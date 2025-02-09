package pl.edu.agh.mwo.hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "schoolClasses")
public class SchoolClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private int startYear;
    @Column
    private int currentYear;
    @Column
    private String profile;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "class_id")

    private Set<Student> students = new HashSet<>();

    @ManyToMany(mappedBy = "classes", cascade = {CascadeType.PERSIST,
            CascadeType.MERGE})
    private Set<Teacher> teachers = new HashSet<>();

    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public int getStartYear() {
        return startYear;
    }
    
    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }
    
    public int getCurrentYear() {
        return currentYear;
    }
    
    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }
    
    public String getProfile() {
        return profile;
    }
    
    public void setProfile(String profile) {
        this.profile = profile;
    }

    public Set<Student> getStudents(){
        return this.students;
    }

    public void addStudent(Student student){
        this.students.add(student);
    }

    public Set<Teacher> getTeachers(){
        return this.teachers;
    }

    public void addTeacher(Teacher teacher){
        this.teachers.add(teacher);
    }

    public void removeTeacher(Teacher teacher){
        this.teachers.remove(teacher);
    }

    @Override
    public String toString() {
        return "        SchoolClass: " + profile + " (start year: " + startYear + ", current year: " + currentYear + ")";
    }
}
