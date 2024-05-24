package be.kdg.programming3.projectwilliamkasasa.presentation.api.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class UpdateStudentStartDateDto {

    @NotNull
    private LocalDate startDate;

    public UpdateStudentStartDateDto() {
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}
