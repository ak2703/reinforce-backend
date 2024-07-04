package me.akshayvilekar.reinforce_backend.config;

package com.example.demo;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import me.akshayvilekar.reinforce_backend.user.User;
import me.akshayvilekar.reinforce_backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Set;

@Component
public class Seeder {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void seedDatabase() {
        userRepository.deleteAll()
                .thenMany(
                        Flux.just(
                                User.builder().username("superadmin")
                                        .password(passwordEncoder
                                                .encode("password"))
                                        .roles("ROLE_SUPERADMIN").build(),
                                User.builder().username("admin")
                                        .password(passwordEncoder
                                                .encode("password"))
                                        .roles("ROLE_ADMIN").build()
                        )
                )
                .flatMap(userRepository::save)
                .subscribe();
    }
}

