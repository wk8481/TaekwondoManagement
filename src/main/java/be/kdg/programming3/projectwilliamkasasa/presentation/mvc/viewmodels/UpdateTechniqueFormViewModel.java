package be.kdg.programming3.projectwilliamkasasa.presentation.mvc.viewmodels;

import jakarta.validation.constraints.NotNull;
import be.kdg.programming3.projectwilliamkasasa.domain.Type;

public class UpdateTechniqueFormViewModel {
    private Integer id;

    @NotNull
    private Type type;

    public UpdateTechniqueFormViewModel() {
    }

    public UpdateTechniqueFormViewModel(Integer id, Type type) {
        this.id = id;
        this.type = type;
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
}
