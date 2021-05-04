package ar.edu.unq.tip.backendcooperar.model.builder;

import ar.edu.unq.tip.backendcooperar.model.Project;
import ar.edu.unq.tip.backendcooperar.model.ProjectCategory;
import ar.edu.unq.tip.backendcooperar.model.Task;
import ar.edu.unq.tip.backendcooperar.model.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectBuilder {

    private String project_name = "default_name";
    private BigDecimal project_budget = BigDecimal.valueOf(0);
    private String project_description = "default_description";
    private String project_owner = "default_owner";
    private LocalDate project_creationDate = LocalDate.now();
    private LocalDate project_finishDate = null;
    private String project_category = ProjectCategory.ENTRETENIMIENTO.toString();
    private List<Task> project_tasks = new ArrayList<Task>();

    public static ProjectBuilder aProject() {
        return new ProjectBuilder();
    }

    public Project build() {
        return new Project(
                project_name,
                project_budget,
                project_description,
                project_owner,
                project_creationDate,
                project_finishDate,
                project_category,
                project_tasks);
    }

    public ProjectBuilder withName(String name) {
        this.project_name = name;
        return this;
    }

    public ProjectBuilder withBudget(BigDecimal budget) {
        this.project_budget = budget;
        return this;
    }

    public ProjectBuilder withDescription(String description) {
        this.project_description = description;
        return this;
    }

    public ProjectBuilder withOwner(String owner) {
        this.project_owner = owner;
        return this;
    }

    public ProjectBuilder withCreationDate(LocalDate creationDate) {
        this.project_creationDate = creationDate;
        return this;
    }

    public ProjectBuilder withFinishDate(LocalDate finishDate) {
        this.project_finishDate = finishDate;
        return this;
    }

    public ProjectBuilder withCategory(String category) {
        this.project_category = category;
        return this;
    }

    public ProjectBuilder withTasks(List<Task> tasks) {
        this.project_tasks = tasks;
        return this;
    }
}
