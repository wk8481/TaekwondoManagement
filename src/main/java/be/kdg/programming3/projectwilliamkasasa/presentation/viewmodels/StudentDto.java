package be.kdg.programming3.projectwilliamkasasa.presentation.viewmodels;

import be.kdg.programming3.projectwilliamkasasa.domain.Instructor;
import be.kdg.programming3.projectwilliamkasasa.domain.Technique;

import java.time.LocalDate;
import java.util.List;

public class StudentDto {
    private int id;
    private String name;
    private LocalDate start;
    private Instructor instructor;
    private List<Technique> techniques;

    // Constructors, getters, and setters

    public StudentDto() {
    }

    public StudentDto(int id, String name, LocalDate start, Instructor instructor, List<Technique> techniques) {
        this.id = id;
        this.name = name;
        this.start = start;
        this.instructor = instructor;
        this.techniques = techniques;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public List<Technique> getTechniques() {
        return techniques;
    }

    public void setTechniques(List<Technique> techniques) {
        this.techniques = techniques;
    }

    @Override
    public String toString() {
        return "StudentDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", start=" + start +
                ", instructor=" + instructor +
                ", techniques=" + techniques +
                '}';
    }
}
