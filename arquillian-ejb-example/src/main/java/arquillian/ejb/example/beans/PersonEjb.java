package arquillian.ejb.example.beans;

import arquillian.ejb.example.entities.Person;

import javax.ejb.Remote;
import java.time.LocalDate;
import java.util.List;

@Remote
public interface PersonEjb {

    Person getPersonByName(String fullName);
    List<Person> getAllPersons();
    List<Person> getMarriedPersons();
    List<Person> getAllPersonOlderThan(LocalDate localDate);
}
