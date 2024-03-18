package be.kdg.programming3.projectwilliamkasasa.presentation.mvc.viewmodels;
import be.kdg.programming3.projectwilliamkasasa.domain.Type;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;

@Component
@SessionScope
public class TechniqueFormViewModel {

    @NotNull
    @NotBlank(message = "Name is mandatory")
    @Size(min = 3, max = 50 , message = "Name must be between 3 and 50 characters")
    private String name;



    private Integer id;

    @NotNull(message = "Type cannot be null")
    private Type type;

    @NotBlank
    @Size(min = 3, max = 255, message = "Description must be between 3 and 255 characters")
    private String description;

    private boolean modificationAllowed;

    private List<StudentFormViewModel> students;

    public TechniqueFormViewModel() {
    }



    public TechniqueFormViewModel(String name, Type type, String description) {
        this.name = name;
        this.type = type;
        this.description = description;
    }

    public TechniqueFormViewModel(Integer id, String name, Type type, String description, List<StudentFormViewModel> students) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.description = description;
        this.students = students;

    }

    public TechniqueFormViewModel(int id, String name, Type type, String description) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;

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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<StudentFormViewModel> getStudents() {
        return students;
    }

    public void setStudents(List<StudentFormViewModel> students) {
        this.students = students;
    }
}
