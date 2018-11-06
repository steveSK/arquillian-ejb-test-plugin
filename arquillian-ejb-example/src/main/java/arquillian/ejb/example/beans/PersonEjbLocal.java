package arquillian.ejb.example.beans;

import arquillian.ejb.example.entities.Person;

import javax.ejb.Local;
import java.time.LocalDate;
import java.util.List;

@Local
public interface PersonEjbLocal {

    Person getPersonByName(String fullName);
    List<Person> getAllPersons();
    List<Person> getMarriedPersons();
    List<Person> getAllPersonOlderThan(LocalDate localDate);


}
