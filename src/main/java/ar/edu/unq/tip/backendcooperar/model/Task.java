package ar.edu.unq.tip.backendcooperar.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter @Setter
public class Task {

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY) private Integer id;
    @Column private String name;
    @Column private String description;
    @Column private BigDecimal reward;
    /*@Column private Integer projectId;*/
    @Column private LocalDate creationDate;
    @Column private LocalDate finishDate;
    @Column private String difficulty;

    public Task() {}

    public Task(String name,
                String description,
                BigDecimal reward,
                LocalDate creationDate,
                LocalDate finishDate,
                String difficulty) {
        this.name = name;
        this.description = description;
        this.reward = reward;
        //this.projectId = projectId;
        this.creationDate = creationDate;
        this.finishDate = finishDate;
        this.difficulty = difficulty;
    }
}
