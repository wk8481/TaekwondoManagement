package be.kdg.programming3.projectwilliamkasasa.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "instructors")
public class Instructor  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    @Column
    private String title;
    @Column(nullable = false)
    private String name;

   @OneToMany(mappedBy = "instructor", cascade ={/*CascadeType.DETACH,*/ CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    private  List<Student> students = new ArrayList<>();

    @OneToMany(mappedBy = "instructor", cascade ={/*CascadeType.DETACH,*/ CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    private  List<Technique> techniques = new ArrayList<>();




    // Constructors, getters, and setters


    public Instructor(int id, String title, String name) {
        this.id = id;
        this.title = title;
        this.name = name;
    }

    public Instructor() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Technique> getTechniques() {
        return techniques;
    }

    public void setTechniques(List<Technique> techniques) {
        this.techniques = techniques;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void addStudent(Student student) {
        students.add(student);
        student.setInstructor(this);
    }

    public void addTechnique(Technique technique) {
        techniques.add(technique);
        technique.setInstructor(this);
    }

    @Override
    public String toString() {
        return "Instructor{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", students=" + students +
                '}';
    }
}
