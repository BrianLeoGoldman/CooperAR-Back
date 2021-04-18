package ar.edu.unq.tip.backendcooperar.model;

import ar.edu.unq.tip.backendcooperar.model.builder.TaskBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Project {

    @Id
    private String name;

    @Column
    private BigDecimal budget;

    @Column
    private String description;

    @Column
    private String userNickname;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "projectName")
    private List<Task> tasks;

    public Project() {}

    public Project(String name, BigDecimal budget, String description, String userNickname, List<Task> tasks) {
        this.name = name;
        this.budget = budget;
        this.description = description;
        this.userNickname = userNickname;
        this.tasks = tasks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal money) {
        this.budget = money;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Task createTask(String name, String description, BigDecimal reward) {
        Task newTask = TaskBuilder.aTask()
                .withName(name)
                .withDescription(description)
                .withReward(reward)
                .withProjectName(this.name)
                .build();
        this.tasks.add(newTask);
        return newTask;
    }
}
