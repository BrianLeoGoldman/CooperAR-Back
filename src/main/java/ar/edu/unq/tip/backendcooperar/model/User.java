package ar.edu.unq.tip.backendcooperar.model;

import ar.edu.unq.tip.backendcooperar.model.builder.ProjectBuilder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@NamedEntityGraph(name = "User.projects",attributeNodes = @NamedAttributeNode("projects"))
public class User {

    @Getter @Setter @Id private String nickname;
    @Getter @Setter @Column private String firstname;
    @Getter @Setter @Column private String lastname;
    @Getter @Setter @Column private String password;
    @Getter @Setter @Column private String email;
    @Getter @Setter @Column private LocalDate birthday;
    @Getter @Setter @Column private String province;
    @Getter @Setter @Column private BigDecimal money;
    @Getter @Setter @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<Project> projects;

    public User() {}

    public User(String nickname,
                String firstname,
                String lastname,
                String password,
                String email,
                LocalDate birthday,
                String province,
                BigDecimal money,
                Set<Project> projects) {
        this.nickname = nickname;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
        this.birthday = birthday;
        this.province = province;
        this.money = money;
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
