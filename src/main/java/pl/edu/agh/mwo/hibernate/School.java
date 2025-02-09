package pl.edu.agh.mwo.hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "schools")
public class School {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column
    private String name;
    
    @Column
    private String address;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "school_id")
    private Set<SchoolClass> schoolClasses = new HashSet<SchoolClass>();
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }

    public Set<SchoolClass> getSchoolClass(){
        return this.schoolClasses;
    }

    public void addSchoolClass(SchoolClass schoolClass){
        schoolClasses.add(schoolClass);
    }

    public void removeSchoolClass(SchoolClass schoolClass){
        schoolClasses.remove(schoolClass);
    }


    
    @Override
    public String toString() {
        return "School: " + name + " (" + address + ")";
    }
}
