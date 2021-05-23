package ar.edu.unq.tip.backendcooperar.model.DTO;

import ar.edu.unq.tip.backendcooperar.model.Project;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ProjectDTO {

    private Integer id;
    private String name;
    private BigDecimal budget;
    private String description;
    private String owner;
    private LocalDate creationDate;
    private LocalDate finishDate;
    private String category;

    public ProjectDTO(){

    }

    public ProjectDTO(Project project){
        this.id = project.getId();
        this.name = project.getName();
        this.budget = project.getBudget();
        this.description = project.getDescription();
        this.owner = project.getOwner();
        this.creationDate = project.getCreationDate();
        this.finishDate = project.getFinishDate();
        this.category = project.getCategory();
    }

    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public BigDecimal getBudget() {
        return budget;
    }
    public String getDescription() {
        return description;
    }
    public String getOwner() {
        return owner;
    }
    public LocalDate getCreationDate() {
        return creationDate;
    }
    public LocalDate getFinishDate() {
        return finishDate;
    }
    public String getCategory() {
        return category;
    }
}
