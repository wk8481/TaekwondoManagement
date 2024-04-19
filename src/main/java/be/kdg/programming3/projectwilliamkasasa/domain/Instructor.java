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

    @Column(nullable = false)
    private String username;

    @Column
    private String password;
@Column
    private Role role;

   @OneToMany(mappedBy = "instructor", cascade ={/*CascadeType.DETACH,*/ CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    private  List<Student> students = new ArrayList<>();

//    @OneToMany(mappedBy = "instructor", cascade ={/*CascadeType.DETACH,*/ CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
//    private  List<Technique> techniques = new ArrayList<>();




    // Constructors, getters, and setters


    public Instructor(int id, String title, String name, String username, String password, Role role) {
        this.id = id;
        this.title = title;
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = role;
    }


    public Instructor() {

    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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




}
