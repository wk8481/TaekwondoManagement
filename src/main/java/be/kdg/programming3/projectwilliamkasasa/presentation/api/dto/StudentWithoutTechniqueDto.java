package be.kdg.programming3.projectwilliamkasasa.presentation.api.dto;

import be.kdg.programming3.projectwilliamkasasa.domain.Instructor;

import java.time.LocalDate;

public class StudentWithoutTechniqueDto {
    private int id;
    private String name;
    private LocalDate startDate;
    private Instructor instructor;

    // Constructors, getters, and setters

    public StudentWithoutTechniqueDto() {
    }

    public StudentWithoutTechniqueDto(int id, String name, LocalDate startDate, Instructor instructor) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.instructor = instructor;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }
}
