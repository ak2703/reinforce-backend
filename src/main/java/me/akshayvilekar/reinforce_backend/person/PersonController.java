package me.akshayvilekar.reinforce_backend.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping
    public Flux<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Person> getPersonById(@PathVariable String id) {
        return personRepository.findById(id);
    }

    @PostMapping
    public Mono<Person> createPerson(@RequestBody Person person) {
        return personRepository.save(person);
    }

    @PutMapping("/{id}")
    public Mono<Person> updatePerson(@PathVariable String id, @RequestBody Person person) {
        return personRepository.findById(id)
                .flatMap(existingPerson -> {
                    existingPerson.setFirstName(person.getFirstName());
                    existingPerson.setLastName(person.getLastName());
                    return personRepository.save(existingPerson);
                });
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deletePerson(@PathVariable String id) {
        return personRepository.deleteById(id);
    }
}
