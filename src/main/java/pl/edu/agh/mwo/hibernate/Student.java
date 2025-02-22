package pl.edu.agh.mwo.hibernate;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private BigInteger class_id;

    @Column
    private String pesel;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurame(String surname) {
        this.surname = surname;
    }

    public String getPesel() {
        return this.pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public BigInteger getClassId() {
        return this.class_id;
    }

    public void setClassId(String name) {
        this.class_id = class_id;
    }

    @Override
    public String toString() {
        return "            Student: " + name + " " + surname + " (" + pesel + ")";
    }
}
