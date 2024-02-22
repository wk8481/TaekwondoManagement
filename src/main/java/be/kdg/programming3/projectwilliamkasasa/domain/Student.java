package be.kdg.programming3.projectwilliamkasasa.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
public class Student  {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)

    private LocalDate start;

    @OneToMany(mappedBy = "student")
    private List<StudentTechnique> techniques;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;


    // Constructors, getters, and setters

    public Student() {
        // Default constructor
        this.start = LocalDate.now();
    }

    public Student(int id, String name, LocalDate start) {
        this.id = id;
        this.name = name;
        this.start = start;
        this.techniques = new ArrayList<>();

    }

    public Student(int studentId, String name, LocalDate startDate, int instructorId) {
        this.id = studentId;
        this.name = name;
        this.start = startDate;
        this.instructor = new Instructor(instructorId, "unknown", "unknown");
        this.techniques = new ArrayList<>();
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }



    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Instructor getInstructor() {
        return instructor;
    }


    public List<StudentTechnique> getTechniques() {
        return techniques;
    }

    public void setTechniques(List<StudentTechnique> techniques) {
        this.techniques = techniques;
    }




    public void setStart(LocalDate start) {
        this.start = start;
    }
    public LocalDate getStart() {
        return start;
    }

    @Override
    public String toString(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE_TIME;  creates hourly time, but we dont want that
        String formattedDate = dtf.format(start);
        return String.format("%d %s %s", id, name, formattedDate);

    }



}





//DateTimeFormatter dtf = DateTimeFormater.ISO.LOCAL_DATE_TIME;