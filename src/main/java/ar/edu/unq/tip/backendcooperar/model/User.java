package ar.edu.unq.tip.backendcooperar.model;

import ar.edu.unq.tip.backendcooperar.model.builder.ProjectBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@NamedEntityGraph(name = "User.projects",attributeNodes = @NamedAttributeNode("projects"))
public class User {

    @Id
    private String nickname;

    @Column
    private String email;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<Project> projects;

    public User() {}

    public User(String nickname, String email, Set<Project> projects) {
        this.nickname = nickname;
        this.email = email;
        this.projects = projects;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public Project createProject(String name, BigDecimal budget, String description){
        Project newProject = ProjectBuilder.aProject()
                .withName(name)
                .withBudget(budget)
                .withDescription(description)
                .withOwner(this.nickname)
                .build();
        this.projects.add(newProject);
        return newProject;
    }


}
