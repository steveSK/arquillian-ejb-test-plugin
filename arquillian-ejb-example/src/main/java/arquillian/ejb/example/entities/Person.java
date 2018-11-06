package arquillian.ejb.example.entities;

import java.io.Serializable;
import java.time.LocalDate;

public class Person implements Serializable {

    private final String fullName;
    private final MarriageStatus mariageStatus;
    private final LocalDate dateOfBirth;
    private final int numberOfChildren;


    public Person(String fullName, MarriageStatus mariageStatus, LocalDate dateOfBirth, int numberOfChildren) {
        this.fullName = fullName;
        this.mariageStatus = mariageStatus;
        this.dateOfBirth = dateOfBirth;
        this.numberOfChildren = numberOfChildren;

    }

    public String getFullName() {
        return fullName;
    }

    public MarriageStatus getMariageStatus() {
        return mariageStatus;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public int getNumberOfChildren() {
        return numberOfChildren;
    }
}
