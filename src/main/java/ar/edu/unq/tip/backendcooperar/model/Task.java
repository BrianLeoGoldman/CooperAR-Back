package ar.edu.unq.tip.backendcooperar.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Task {

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY) private Integer id;
    @Column private String name;
    @Column private String description;
    @Column private BigDecimal reward;
    @Column private Integer projectId;
    @Column private LocalDate creationDate;
    @Column private LocalDate finishDate;
    @Column private String difficulty;
    @Column private String owner;
    @Column private String worker;
    @Column private String state;

    @Transient
    private List<String> files;

    public Task() {}

    public Task(String name,
                String description,
                BigDecimal reward,
                Integer projectId,
                LocalDate creationDate,
                LocalDate finishDate,
                String difficulty,
                String owner,
                String worker,
                String state) {
        this.name = name;
        this.description = description;
        this.reward = reward;
        this.projectId = projectId;
        this.creationDate = creationDate;
        this.finishDate = finishDate;
        this.difficulty = difficulty;
        this.owner = owner;
        this.worker = worker;
        this.state = state;
        this.files = new ArrayList<>();
    }
}
