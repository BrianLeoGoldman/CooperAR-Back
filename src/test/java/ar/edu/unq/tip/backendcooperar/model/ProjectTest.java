package ar.edu.unq.tip.backendcooperar.model;

import ar.edu.unq.tip.backendcooperar.model.builder.ProjectBuilder;
import ar.edu.unq.tip.backendcooperar.model.builder.TaskBuilder;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {

    @Test
    public void testProjectName() {
        String name = "Start a business";
        Project project = ProjectBuilder.aProject().withName(name).build();
        assertEquals(name, project.getName());
    }

    @Test
    public void testProjectBudget() {
        BigDecimal budget = BigDecimal.valueOf(7000);
        Project project = ProjectBuilder.aProject().withBudget(budget).build();
        assertEquals(budget, project.getBudget());
    }

    @Test
    public void testProjectDescription() {
        String description = "I want to start a restaurant in my city";
        Project project = ProjectBuilder.aProject().withDescription(description).build();
        assertEquals(description, project.getDescription());
    }

    @Test
    public void testProjectUserName() {
        String userName = "fabio789";
        Project project = ProjectBuilder.aProject().withUserNickname(userName).build();
        assertEquals(userName, project.getUserNickname());
    }

    @Test
    public void testProjectTasks() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(TaskBuilder.aTask().build());
        tasks.add(TaskBuilder.aTask().build());
        Project project = ProjectBuilder.aProject().withTasks(tasks).build();
        assertEquals(2, project.getTasks().size());
    }

}