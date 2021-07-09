package ar.edu.unq.tip.backendcooperar.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Message {

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY) private Integer id;
    @Column private String publisher;
    @Column private String date;
    @Column private String text;
    @Column private Integer taskId;

    public Message() {}

    public Message(String publisher,
                   String date,
                String text,
                Integer taskId) {
        this.publisher = publisher;
        this.date = date;
        this.text = text;
        this.taskId = taskId;
    }
}
