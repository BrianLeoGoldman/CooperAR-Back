package ar.edu.unq.tip.backendcooperar.model;

import ar.edu.unq.tip.backendcooperar.model.enums.RequestState;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter @Setter
public class MoneyRequest {

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY) private Integer id;
    @Column private String requester;
    @Column private BigDecimal moneyRequested;
    @Column private LocalDate creationDate;
    @Column private String state;

    public MoneyRequest() { }

    public MoneyRequest(String requester, BigDecimal moneyRequested) {
        this.requester = requester;
        this.moneyRequested = moneyRequested;
        this.creationDate = LocalDate.now();
        this.state = RequestState.ABIERTO.name();
    }
}
