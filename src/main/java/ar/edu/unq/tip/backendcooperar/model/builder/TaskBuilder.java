package ar.edu.unq.tip.backendcooperar.model.builder;

import ar.edu.unq.tip.backendcooperar.model.Task;
import ar.edu.unq.tip.backendcooperar.model.enums.TaskDifficulty;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TaskBuilder {

    private String task_name = "default_name";
    private String task_description = "default_description";
    private BigDecimal task_reward = new BigDecimal(0);
    private Integer task_projectId = 1;
    private LocalDate task_creationDate = LocalDate.now();
    private LocalDate task_finishDate = null;
    private String task_difficulty = TaskDifficulty.REGULAR.toString();

    public static TaskBuilder aTask() {
        return new TaskBuilder();
    }

    public Task build() {
        return new Task(
                task_name,
                task_description,
                task_reward,
                task_projectId,
                task_creationDate,
                task_finishDate,
                task_difficulty);
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

    public TaskBuilder withProjectId(Integer project_id) {
        this.task_projectId = project_id;
        return this;
    }

    public TaskBuilder withCreationDate(LocalDate creationDate) {
        this.task_creationDate = creationDate;
        return this;
    }

    public TaskBuilder withFinishDate(LocalDate finishDate) {
        this.task_finishDate = finishDate;
        return this;
    }

    public TaskBuilder withDifficulty(String difficulty) {
        this.task_difficulty = difficulty;
        return this;
    }
}
