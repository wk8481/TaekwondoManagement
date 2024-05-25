package be.kdg.programming3.projectwilliamkasasa.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "techniques")
public class Technique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column
    private String description;



    @OneToMany(mappedBy = "technique", fetch = FetchType.LAZY)
    private List<StudentTechnique> students;


    public Technique(int id, String name,Type type, String description) {
        this.id = id;
        this.description = description;
        this.type = type;
        this.name = name;
        this.students = new ArrayList<>();
    }

    public Technique() {

    }

    public Technique(int id, String name, Type type, String description, int instructorId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.students = new ArrayList<>();
    }

    public Technique(String name, Type type, String description) {
        this.name = name;
        this.description = description;
        this.type = type;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<StudentTechnique> getStudents() {
        return students;
    }

    public void setStudents(List<StudentTechnique> students) {
        this.students = students;
    }















}
