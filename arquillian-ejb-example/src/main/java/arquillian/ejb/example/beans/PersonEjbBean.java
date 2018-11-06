package arquillian.ejb.example.beans;


import arquillian.ejb.example.entities.MarriageStatus;
import arquillian.ejb.example.entities.Person;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class PersonEjbBean implements PersonEjb,PersonEjbLocal {

    private List<Person> allPersons;


    @PostConstruct
    private void init() {
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("Jakob Adan",MarriageStatus.SINGLE,LocalDate.of(1988,3,1),0));
        personList.add(new Person("John Smith",MarriageStatus.MARRIED,LocalDate.of(1980,6,13),2));
        personList.add(new Person("Katharina Sane",MarriageStatus.MARRIED,LocalDate.of(1984,6,13),1));
        personList.add(new Person("James Tall",MarriageStatus.SINGLE,LocalDate.of(1971,2,25),1));
        personList.add(new Person("Marianna Clock",MarriageStatus.SINGLE,LocalDate.of(1993,12,23),0));
        personList.add(new Person("Suzane Bright",MarriageStatus.SINGLE,LocalDate.of(1990,1,4),1));

        allPersons = personList;
    }




    @Override
    public Person getPersonByName(String fullName) {
        return allPersons.stream().filter(x -> x.getFullName().equals(fullName)).findFirst().orElse(null);
    }

    @Override
    public List<Person> getAllPersons() {
        return allPersons;
    }

    @Override
    public List<Person> getMarriedPersons() {
        return allPersons.stream().filter(x -> x.getMariageStatus().equals(MarriageStatus.MARRIED)).collect(Collectors.toList());
    }

    @Override
    public List<Person> getAllPersonOlderThan(LocalDate localDate) {
        return allPersons.stream().filter(x -> x.getDateOfBirth().isBefore(localDate)).collect(Collectors.toList());
    }
}
