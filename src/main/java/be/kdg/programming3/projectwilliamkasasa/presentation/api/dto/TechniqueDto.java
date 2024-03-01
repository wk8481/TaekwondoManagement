

//jdbc
package be.kdg.programming3.projectwilliamkasasa.presentation.api.dto;

import be.kdg.programming3.projectwilliamkasasa.domain.Instructor;
import be.kdg.programming3.projectwilliamkasasa.domain.Type;

public class TechniqueDto {
    private int id;
    private String name;
    private Type type;
    private String description;




    public TechniqueDto() {
    }

    public TechniqueDto(int id, String name, Type type, String description) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;


    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "TechniqueDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", description='" + description + '\'' +
                '}';
    }


}

