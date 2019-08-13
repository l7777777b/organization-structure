package org.javers.organization.structure.domain;

import org.javers.organization.structure.domain.EmployeeRepository;
import org.javers.organization.structure.domain.Person;
import org.javers.organization.structure.domain.PersonRepository;
import org.javers.spring.annotation.JaversAuditable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toMap;

@Component
public class PersonService {

    private PersonRepository personRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public PersonService(PersonRepository personRepository, EmployeeRepository employeeRepository) {
        this.personRepository = personRepository;
        this.employeeRepository = employeeRepository;
    }

    @JaversAuditable
    public void update(Person person) {
        personRepository.save(person);
    }

    public Person findPerson(int id) {
        return personRepository.findById(id).orElse(null);
    }

    public Map<Integer, String> findAllPersons() {
        return StreamSupport.stream(personRepository.findAll().spliterator(), false)
                .collect(toMap((Function<Person, Integer>) person -> person.getId(),
                        (Function<Person, String>) person -> person.getFirstName() + " " + person.getLastName()));
    }

    public List<String> findAllEmployees() {
        return StreamSupport.stream(employeeRepository.findAll().spliterator(), false)
                .map(e -> e.getDomainName())
                .collect(Collectors.toList());
    }

    @JaversAuditable
    public void save(Person person) {
        personRepository.save(person);
    }
}
