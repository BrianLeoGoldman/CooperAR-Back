package ar.edu.unq.tip.backendcooperar.model.builder;

import ar.edu.unq.tip.backendcooperar.model.Task;

import java.math.BigDecimal;

public class TaskBuilder {

    private String task_name = "default_name";
    private String task_description = "default_description";
    private BigDecimal task_reward = new BigDecimal(0);
    private String project_name = "default_projectName";

    public static TaskBuilder aTask() {
        return new TaskBuilder();
    }

    public Task build() {
        Task newTask = new Task(task_name, task_description, task_reward, project_name);
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

    public TaskBuilder withProjectName(String project_name) {
        this.project_name = project_name;
        return this;
    }
}
