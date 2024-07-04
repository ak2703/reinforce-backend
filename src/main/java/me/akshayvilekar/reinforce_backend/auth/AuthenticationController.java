package me.akshayvilekar.reinforce_backend.auth;

import me.akshayvilekar.reinforce_backend.auth.jwt.JwtRequest;
import me.akshayvilekar.reinforce_backend.auth.jwt.JwtResponse;
import me.akshayvilekar.reinforce_backend.auth.jwt.JwtTokenUtil;
import me.akshayvilekar.reinforce_backend.auth.jwt.JwtUserDetailsService;
import me.akshayvilekar.reinforce_backend.user.User;
import me.akshayvilekar.reinforce_backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Mono;

@RestController
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private ReactiveAuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/authenticate")
    public Mono<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {
        return userDetailsService.findByUsername(authenticationRequest.getUsername())
                .flatMap(userDetails -> authenticate(userDetails, authenticationRequest.getPassword()))
                .flatMap(userDetails -> {
                    final String token = jwtTokenUtil.generateToken(userDetails);
                    return Mono.just(new JwtResponse(token));
                });
    }

    private Mono<UserDetails> authenticate(UserDetails userDetails, String password) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails.getUsername(), password);
        return authenticationManager.authenticate(authenticationToken)
                .map(auth -> userDetails);
    }

    @PostMapping("/register")
    public Mono<User> register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @PostMapping("/logout")
    public Mono<Void> logout() {
        // Implement logout logic if required (e.g., invalidate JWT token on the client side)
        return Mono.empty();
    }

    @GetMapping("/current-user")
    public Mono<UserDetails> getCurrentUser(@AuthenticationPrincipal Mono<UserDetails> userDetails) {
        return userDetails;
    }
}
