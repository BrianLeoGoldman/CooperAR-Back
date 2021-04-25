package ar.edu.unq.tip.backendcooperar.model;

import ar.edu.unq.tip.backendcooperar.model.builder.TaskBuilder;
import ar.edu.unq.tip.backendcooperar.model.exceptions.InvalidTaskException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Project {

    @Getter @Setter @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Getter @Setter @Column(length = 150)
    private String name;

    @Getter @Setter @Column
    private BigDecimal budget;

    @Getter @Setter @Column(length = 255)
    private String description;

    @Getter @Setter @Column
    private String owner;

    @Getter @Setter @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Task> tasks;

    public Project() {}

    public Project(String name, BigDecimal budget, String description, String owner, List<Task> tasks) {
        this.name = name;
        this.budget = budget;
        this.description = description;
        this.owner = owner;
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
