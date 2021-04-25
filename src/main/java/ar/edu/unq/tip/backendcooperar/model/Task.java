package ar.edu.unq.tip.backendcooperar.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Task {

    @Getter @Setter @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Getter @Setter @Column(length = 150)
    private String name;

    @Getter @Setter @Column(length = 255)
    private String description;

    @Getter @Setter @Column
    private BigDecimal reward;

    /*@Getter @Setter @Column
    private Integer projectId;*/

    public Task() {}

    public Task(String name, String description, BigDecimal reward) {
        this.name = name;
        this.description = description;
        this.reward = reward;
        //this.projectId = projectId;
    }
}
