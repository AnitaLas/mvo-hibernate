package pl.edu.agh.mwo.hibernate;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "teachers")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String pesel;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
   // @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "schoolClasses_teachers",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "schoolClass_id"))

    private Set<SchoolClass> classes = new HashSet<>();

    public long getId() {
        return id;
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

    public void setSurame(String surname) {
        this.surname = surname;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getPesel() {
        return this.pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public void addSchoolClass(SchoolClass schoolClass){
        this.classes.add(schoolClass);
    }

    public void removeSchoolClass(SchoolClass schoolClass){
        this.classes.remove(schoolClass);
    }

    public  Set<SchoolClass> getSchoolClass(){
        return classes;
    }

    @Override
    public String toString() {
        return "            Teacher name: " + name + " (surname: " + surname + ", pesel " + pesel + ")";
    }
}
