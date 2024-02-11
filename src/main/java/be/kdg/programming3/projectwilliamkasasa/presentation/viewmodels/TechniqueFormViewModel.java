package be.kdg.programming3.projectwilliamkasasa.presentation.viewmodels;
import be.kdg.programming3.projectwilliamkasasa.domain.Type;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

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

    public TechniqueFormViewModel() {
    }

    public TechniqueFormViewModel(String name, Integer id, Type type, String description) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.description = description;
    }

    public TechniqueFormViewModel(String name, Type type, String description) {
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

    @Override
    public String toString() {
        return "TechniqueViewModelForm{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", type=" + type +
                ", description='" + description + '\'' +
                '}';
    }
}
