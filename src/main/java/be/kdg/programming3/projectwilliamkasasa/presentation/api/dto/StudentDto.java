package be.kdg.programming3.projectwilliamkasasa.presentation.api.dto;

import be.kdg.programming3.projectwilliamkasasa.domain.Instructor;
import be.kdg.programming3.projectwilliamkasasa.domain.Technique;

import java.time.LocalDate;
import java.util.List;

public class StudentDto {
    private int id;
    private String name;
    private LocalDate startDate;
//    private Instructor instructor;
//    private List<Technique> techniques;

    // Constructors, getters, and setters

    public StudentDto() {
    }

    public StudentDto(int id, String name, LocalDate startDate) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;

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




}
