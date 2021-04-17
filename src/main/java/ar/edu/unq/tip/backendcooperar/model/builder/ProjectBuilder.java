package ar.edu.unq.tip.backendcooperar.model.builder;

import ar.edu.unq.tip.backendcooperar.model.Project;
import ar.edu.unq.tip.backendcooperar.model.Task;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProjectBuilder {

    private String project_name = "default_name";
    private BigDecimal project_budget = BigDecimal.valueOf(0);
    private String project_description = "default_description";
    private String project_userName = "default_userName";
    private List<Task> project_tasks = new ArrayList<Task>();

    public static ProjectBuilder aProject() {
        return new ProjectBuilder();
    }

    public Project build() {
        Project newProject = new Project(project_name, project_budget, project_description, project_userName, project_tasks);
        return newProject;
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

    public ProjectBuilder withUserName(String userName) {
        this.project_userName = userName;
        return this;
    }

    public ProjectBuilder withTasks(List<Task> tasks) {
        this.project_tasks = tasks;
        return this;
    }
}
