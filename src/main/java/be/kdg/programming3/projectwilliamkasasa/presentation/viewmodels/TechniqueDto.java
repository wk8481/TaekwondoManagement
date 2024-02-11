

//jdbc
package be.kdg.programming3.projectwilliamkasasa.presentation.viewmodels;

import be.kdg.programming3.projectwilliamkasasa.domain.Instructor;
import be.kdg.programming3.projectwilliamkasasa.domain.Type;

public class TechniqueDto {
    private int id;
    private String name;
    private Type type;
    private String description;

    private Instructor instructor;



    public TechniqueDto() {
    }

    public TechniqueDto(int id, String name, Type type, String description, Instructor instructor) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.instructor = instructor;

    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor= instructor;
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
                ", instructor=" + instructor +
                '}';
    }


}
//
////jpa
//package be.kdg.programming3.taekwondospring.presentation.viewmodels;
//
//        import be.kdg.programming3.taekwondospring.domain.Instructor;
//        import be.kdg.programming3.taekwondospring.domain.Technique;
//        import be.kdg.programming3.taekwondospring.domain.Type;
//
//public class TechniqueDto {
//    private int id;
//    private String name;
//    private Type type;
//    private String description;
//
//    private InstructorDto instructor;
//
//
//    public TechniqueDto() {
//    }
//
//    public static TechniqueDto fromEntity(Technique technique, InstructorDto instructorDto) {
//        TechniqueDto techniqueDto = new TechniqueDto();
//        techniqueDto.setId(technique.getId());
//        techniqueDto.setName(technique.getName());
//        techniqueDto.setType(technique.getType());
//        techniqueDto.setDescription(technique.getDescription());
//        techniqueDto.setInstructor(instructorDto);
//
//        return techniqueDto;
//    }
//
//    public InstructorDto getInstructor() {
//        return instructor;
//    }
//
//    public void setInstructor(InstructorDto instructor) {
//        this.instructor = instructor;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public Type getType() {
//        return type;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setType(Type type) {
//        this.type = type;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    @Override
//    public String toString() {
//        return "TechniqueDto{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", type=" + type +
//                ", description='" + description + '\'' +
//                ", instructor=" + instructor +
//                '}';
//    }
//}
//
//
