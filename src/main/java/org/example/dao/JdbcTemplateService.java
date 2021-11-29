package org.example.dao;

import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class JdbcTemplateService implements DatabaseService{
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplateService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM PERSON", new BeanPropertyRowMapper<>(Person.class));
    }

    @Override
    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM PERSON WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    @Override
    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO PERSON VALUES(1,?,?,?)", person.getName(), person.getAge(), person.getEmail());
    }

    @Override
    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE PERSON SET  name=?, age=?, email=? WHERE id=?", updatedPerson.getName(), updatedPerson.getAge(), updatedPerson.getEmail(), id);
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM PERSON WHERE id=?", id);
    }
}
