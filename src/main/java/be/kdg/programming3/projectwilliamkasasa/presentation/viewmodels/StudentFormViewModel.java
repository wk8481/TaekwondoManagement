package be.kdg.programming3.projectwilliamkasasa.presentation.viewmodels;

import be.kdg.programming3.projectwilliamkasasa.domain.Technique;
import jakarta.validation.constraints.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.time.LocalDate;
import java.util.List;

@Component
@SessionScope

public class StudentFormViewModel {

    @NotNull
    @NotBlank(message = "Name is mandatory")
    @Size(min = 3, max = 15 , message = "Name must be between 3 and 15 characters")
    private String name;


//    @ValidId

    private Integer id;


    @NotNull(message = "Start date is mandatory")
    @PastOrPresent(message = "Start date must be in the past or present")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate start;

    private List<Technique> techniques;


    public StudentFormViewModel() {
    }

    public StudentFormViewModel(List<Technique> techniques) {
        this.techniques = techniques;
    }

    public StudentFormViewModel(String name, Integer id, LocalDate start) {
        this.name = name;
        this.id = id;
        this.start = start;
    }

    public StudentFormViewModel(int id, String name, LocalDate start, List<TechniqueFormViewModel> list) {

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

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    @Override
    public String toString() {
        return "StudentViewModelForm{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", start=" + start +
                '}';
    }

    public List<Technique> getTechniques() {
        return techniques;
    }
}
