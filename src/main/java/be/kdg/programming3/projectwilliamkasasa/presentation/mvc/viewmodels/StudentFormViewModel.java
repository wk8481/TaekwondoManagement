package be.kdg.programming3.projectwilliamkasasa.presentation.mvc.viewmodels;

import be.kdg.programming3.projectwilliamkasasa.domain.Technique;
import jakarta.validation.constraints.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.time.LocalDate;
import java.util.List;

@Component
@SessionScope

public class StudentFormViewModel {

    private Integer id;

    @NotNull
    @NotBlank(message = "Name is mandatory")
    @Size(min = 3, max = 15 , message = "Name must be between 3 and 15 characters")
    private String name;


//    @ValidId




    @NotNull(message = "Start date is mandatory")
    @PastOrPresent(message = "Start date must be in the past or present")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    private boolean modificationAllowed;

    private List<Technique> techniques;


    public StudentFormViewModel() {
    }

    public StudentFormViewModel(List<Technique> techniques) {
        this.techniques = techniques;
    }

    public StudentFormViewModel(Integer id, String name, LocalDate startDate) {
        this.name = name;
        this.id = id;
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

    public void setTechniques(List<Technique> techniques) {
        this.techniques = techniques;
    }

    public StudentFormViewModel(int id, String name, LocalDate startDate, List<TechniqueFormViewModel> list) {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
