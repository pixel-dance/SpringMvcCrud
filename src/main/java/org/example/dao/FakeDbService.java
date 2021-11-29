package org.example.dao;

import org.example.models.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeDbService implements DatabaseService{
    private static int PEOPLE_COUNT;
    private List<Person> people;

    {
      people = new ArrayList<>();

      people.add(new Person(++PEOPLE_COUNT, "Adam", 23, "adam@mail.ru"));
      people.add(new Person(++PEOPLE_COUNT, "Sam", 35,"sam@mail.ru"));
      people.add(new Person(++PEOPLE_COUNT, "Garry",28,"garry@mail.ru"));
    }

    @Override
    public List<Person> index(){
        return people;
    }

    @Override
    public Person show(int id){
        return people.stream().filter(a -> a.getId() == id).findAny().orElse(null);
    }

    @Override
    public void save(Person person){
        person.setId(++PEOPLE_COUNT);
        people.add(person);
    }

    @Override
    public void update(int id, Person updatedPerson){
        Person personToBeUpdated = show(id);
        personToBeUpdated.setName(updatedPerson.getName());
        personToBeUpdated.setAge(updatedPerson.getAge());
        personToBeUpdated.setEmail(updatedPerson.getEmail());
    }

    @Override
    public void delete(int id){
        people.removeIf(p -> p.getId() == id);
    }
}
