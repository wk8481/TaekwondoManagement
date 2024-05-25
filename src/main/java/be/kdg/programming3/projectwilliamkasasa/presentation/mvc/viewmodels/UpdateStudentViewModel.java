package be.kdg.programming3.projectwilliamkasasa.presentation.mvc.viewmodels;

import jakarta.validation.constraints.NotBlank;

public class UpdateStudentViewModel {
    private Integer id;
    @NotBlank
    private String name;

    private String startDate;

    public UpdateStudentViewModel() {
    }

    public UpdateStudentViewModel(Integer id, String name, String startDate) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStartDate() {
        return startDate;
    }
}
