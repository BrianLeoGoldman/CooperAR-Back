package ar.edu.unq.tip.backendcooperar.model;

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

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column
    private String name;

    @Column
    private BigDecimal budget;

    @Column
    private String userName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "projectName")
    private List<Task> tasks;

    public Project() {}

    public Project(String name, BigDecimal budget, String userName, List<Task> tasks) {
        this.name = name;
        this.budget = budget;
        this.userName = userName;
        this.tasks = tasks;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) {
        this.id = id;
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

    public List<Task> getTasks() {
        return tasks;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        task.setProjectName(this.name);
        this.tasks.add(task);
    }

}
