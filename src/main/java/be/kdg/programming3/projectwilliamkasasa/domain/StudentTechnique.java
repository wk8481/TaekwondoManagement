package be.kdg.programming3.projectwilliamkasasa.domain;

import jakarta.persistence.*;

@Entity
public class StudentTechnique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "technique_id")
    private Technique technique;

    @Enumerated(EnumType.STRING)
    @Column
    private BeltLevel level; // Level of the proficiency of the student in the technique

    public StudentTechnique() {
    }

    public StudentTechnique(int id, Student student, Technique technique, BeltLevel level) {
        this.id = id;
        this.student = student;
        this.technique = technique;
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Technique getTechnique() {
        return technique;
    }

    public void setTechnique(Technique technique) {
        this.technique = technique;
    }

    public BeltLevel getLevel() {
        return level;
    }

    public void setLevel(BeltLevel level) {
        this.level = level;
    }

    @Override
public String toString() {
        return "StudentTechnique{" +
                "id=" + id +
                ", student=" + student +
                ", technique=" + technique +
                ", level=" + level +
                '}';
    }

}

