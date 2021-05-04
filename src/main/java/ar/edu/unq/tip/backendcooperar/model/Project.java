package ar.edu.unq.tip.backendcooperar.model;

import ar.edu.unq.tip.backendcooperar.model.builder.TaskBuilder;
import ar.edu.unq.tip.backendcooperar.model.exceptions.InvalidTaskException;
import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.jni.Local;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter @Setter
public class Project {

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY) private Integer id;
    @Column private String name;
    @Column private BigDecimal budget;
    @Column private String description;
    @Column private String owner;
    @Column private LocalDate creationDate;
    @Column private LocalDate finishDate;
    @Column private String category;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Task> tasks;

    public Project() {}

    public Project(String name,
                   BigDecimal budget,
                   String description,
                   String owner,
                   LocalDate creationDate,
                   LocalDate finishDate,
                   String category,
                   List<Task> tasks) {
        this.name = name;
        this.budget = budget;
        this.description = description;
        this.owner = owner;
        this.creationDate = creationDate;
        this.finishDate = finishDate;
        this.category = category;
        this.tasks = tasks;
    }

    public Task createTask(String name, String description, BigDecimal reward) throws InvalidTaskException {
        if(budget.subtract(reward).compareTo(BigDecimal.valueOf(0)) < 0) {
            throw  new InvalidTaskException("The project does not have enough budget");
        }
        this.budget = budget.subtract(reward);
        Task newTask = TaskBuilder.aTask()
                .withName(name)
                .withDescription(description)
                .withReward(reward)
                .build();
        this.tasks.add(newTask);
        return newTask;
    }
}
