package ar.edu.unq.tip.backendcooperar.model;

import ar.edu.unq.tip.backendcooperar.model.builder.TaskBuilder;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    public void testTaskName() {
        String name = "Cook fries";
        Task task = TaskBuilder.aTask().withName(name).build();
        assertEquals(name, task.getName());
    }

    @Test
    public void testTaskDescription() {
        String description = "Fix a broken wooden door";
        Task task = TaskBuilder.aTask().withDescription(description).build();
        assertEquals(description, task.getDescription());
    }

    @Test
    public void testTaskReward() {
        BigDecimal reward = BigDecimal.valueOf(123);
        Task task = TaskBuilder.aTask().withReward(reward).build();
        assertEquals(reward, task.getReward());
    }

    @Test
    public void testTaskProjectName() {
        String projectName = "Start a business";
        Task task = TaskBuilder.aTask().withProjectName(projectName).build();
        assertEquals(projectName, task.getProjectName());
    }

}