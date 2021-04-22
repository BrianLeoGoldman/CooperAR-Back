package ar.edu.unq.tip.backendcooperar.model.builder;

import ar.edu.unq.tip.backendcooperar.model.Task;

import java.math.BigDecimal;

public class TaskBuilder {

    private String task_name = "default_name";
    private String task_description = "default_description";
    private BigDecimal task_reward = new BigDecimal(0);
    //private Integer project_id = 1;

    public static TaskBuilder aTask() {
        return new TaskBuilder();
    }

    public Task build() {
        Task newTask = new Task(task_name, task_description, task_reward);
        return newTask;
    }

    public TaskBuilder withName(String name) {
        this.task_name = name;
        return this;
    }

    public TaskBuilder withDescription(String description) {
        this.task_description = description;
        return this;
    }

    public TaskBuilder withReward(BigDecimal reward) {
        this.task_reward = reward;
        return this;
    }

    /*public TaskBuilder withProjectId(Integer project_id) {
        this.project_id = project_id;
        return this;
    }*/
}
