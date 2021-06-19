package ar.edu.unq.tip.backendcooperar.model;

import ar.edu.unq.tip.backendcooperar.model.enums.RequestState;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

@Entity
@Getter @Setter
public class MoneyRequest {

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY) private Integer id;
    @Column private String requester;
    @Column private BigDecimal moneyRequested;
    @Column private LocalDate creationDate;
    @Column private String state;
    @Transient private String accountStatus;
    @Transient private String depositReceipt;

    public MoneyRequest() { }

    public MoneyRequest(String requester, BigDecimal moneyRequested) {
        this.requester = requester;
        this.moneyRequested = moneyRequested;
        this.creationDate = LocalDate.now();
        this.state = RequestState.ABIERTO.name();
        this.accountStatus = "";
        this.depositReceipt = "";
    }

    public void loadFiles() {
        String directoryAS = "src/main/resources/request/" + this.getId() + "/AS";
        File folderAS = new File(directoryAS);
        File[] listOfFilesAS = folderAS.listFiles();
        if(listOfFilesAS != null){
            this.accountStatus = Arrays.stream(listOfFilesAS).findFirst().map(File::getName).get();
        }

        String directoryDR = "src/main/resources/request/" + this.getId() + "/DR";
        File folderDR = new File(directoryDR);
        File[] listOfFilesDR = folderDR.listFiles();
        if(listOfFilesDR != null){
            this.depositReceipt = Arrays.stream(listOfFilesDR).findFirst().map(File::getName).get();
        }
    }
}
