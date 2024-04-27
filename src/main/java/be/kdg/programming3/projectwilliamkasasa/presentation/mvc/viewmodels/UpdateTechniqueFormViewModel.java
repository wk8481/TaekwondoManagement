package be.kdg.programming3.projectwilliamkasasa.presentation.mvc.viewmodels;

import jakarta.validation.constraints.NotNull;
import be.kdg.programming3.projectwilliamkasasa.domain.Type;

public class UpdateTechniqueFormViewModel {
    private Integer id;

    @NotNull
    private String description;

    public UpdateTechniqueFormViewModel() {
    }

    public UpdateTechniqueFormViewModel(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
