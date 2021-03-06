package ar.edu.unq.tip.backendcooperar.model;

import ar.edu.unq.tip.backendcooperar.model.builder.ProjectBuilder;
import ar.edu.unq.tip.backendcooperar.model.exceptions.InvalidProjectException;
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
@NamedEntityGraph(name = "User.projects", attributeNodes = @NamedAttributeNode("projects"))
@Getter @Setter
public class User {

    @Id private String nickname;
    @Column private String firstname;
    @Column private String lastname;
    @Column private String password;
    @Column private String email;
    @Column private LocalDate birthday;
    @Column private String province;
    @Column private BigDecimal money;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "owner")
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

    public Project createProject(String name, BigDecimal budget, String description, String category) throws InvalidProjectException {
        if(money.subtract(budget).compareTo(BigDecimal.valueOf(0)) < 0) {
            throw new InvalidProjectException("EL USUARIO NO TIENE SUFICIENTE DINERO");
        }
        this.money = this.money.subtract(budget);
        Project newProject = ProjectBuilder.aProject()
                .withName(name)
                .withBudget(budget)
                .withDescription(description)
                .withOwner(this.nickname)
                .withCreationDate(LocalDate.now())
                .withFinishDate(null)
                .withCategory(category)
                .build();
        this.projects.add(newProject);
        return newProject;
    }

    public void receiveMoney(BigDecimal reward) {
        this.money = this.money.add(reward);
    }
}
