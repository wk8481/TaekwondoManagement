package be.kdg.programming3.projectwilliamkasasa.presentation.api.dto;

public class InstructorDto {
    private int id;
    private String name;

    private String title;
    // other fields...

    public InstructorDto() {
    }

    public InstructorDto(int id, String name, String title) {
        this.id = id;
        this.name = name;
        this.title = title;
    }


    // getters and setters...

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
