package be.kdg.programming3.projectwilliamkasasa.presentation.mvc.viewmodels;

import be.kdg.programming3.projectwilliamkasasa.domain.Technique;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public class StudentFormViewModel {

    private Integer id;

    @NotNull
    @NotBlank(message = "Name is mandatory")
    @Size(min = 3, max = 15, message = "Name must be between 3 and 15 characters")
    private String name;

    @NotNull(message = "Start date is mandatory")
    @PastOrPresent(message = "Start date must be in the past or present")
    private LocalDate startDate;

    private boolean modificationAllowed;

    private List<Technique> techniques;

    public StudentFormViewModel() {
    }

    public StudentFormViewModel(Integer id, String name, LocalDate startDate) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
    }

    public StudentFormViewModel(Integer id, String name, LocalDate startDate, boolean modificationAllowed) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.modificationAllowed = modificationAllowed;
    }

    public boolean isModificationAllowed() {
        return modificationAllowed;
    }

    public void setModificationAllowed(boolean modificationAllowed) {
        this.modificationAllowed = modificationAllowed;
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

    public List<Technique> getTechniques() {
        return techniques;
    }

    public void setTechniques(List<Technique> techniques) {
        this.techniques = techniques;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
