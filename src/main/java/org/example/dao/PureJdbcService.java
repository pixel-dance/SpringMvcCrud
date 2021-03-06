package org.example.dao;

import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class PureJdbcService implements DatabaseService{
    private static int PEOPLE_COUNT;

    private static Connection connection;

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void init(){
        try {
            Class.forName("org.postgresql.Driver");
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    private void destroy(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Person> index() {
        List<Person> people = new ArrayList<>();
        try (Statement statement = connection.createStatement()){
            String SQL = "SELECT * FROM Person";
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()){
                Person person = new Person();

                person.setId(resultSet.getInt("id"));
                person.setAge(resultSet.getInt("age"));
                person.setName(resultSet.getString("name"));
                person.setEmail(resultSet.getString("email"));

                people.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return people;
    }

    @Override
    public Person show(int id) {

        Person person = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PERSON WHERE id = ?")){

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

            person = new Person();

            person.setId(resultSet.getInt("id"));
            person.setName(resultSet.getString("name"));
            person.setAge(resultSet.getInt("age"));
            person.setEmail(resultSet.getString("email"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    @Override
    public void save(Person person) {

        try (PreparedStatement prepareStatement = connection.prepareStatement("INSERT INTO PERSON VALUES (1,?,?,?)")){

            prepareStatement.setString(1, person.getName());
            prepareStatement.setInt(2, person.getAge());
            prepareStatement.setString(3, person.getEmail());

            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(int id, Person updatedPerson) {

        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE PERSON SET  name=?, age=?, email=? WHERE id=?")){
        preparedStatement.setString(1,updatedPerson.getName());
        preparedStatement.setInt(2, updatedPerson.getAge());
        preparedStatement.setString(3, updatedPerson.getEmail());
        preparedStatement.setInt(4, id);

        preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM PERSON WHERE id=?")){

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
