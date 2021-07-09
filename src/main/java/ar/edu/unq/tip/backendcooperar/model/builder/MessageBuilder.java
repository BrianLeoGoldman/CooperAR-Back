package ar.edu.unq.tip.backendcooperar.model.builder;

import ar.edu.unq.tip.backendcooperar.model.Message;

import java.time.LocalDateTime;

public class MessageBuilder {

    private String message_publisher = "default_name";
    private String message_date = "";
    private String message_text = "default_text";
    private Integer message_taskId = 1;

    public static MessageBuilder aMessage() {
        return new MessageBuilder();
    }

    public Message build() {
        return new Message(
                message_publisher,
                message_date,
                message_text,
                message_taskId);
    }

    public MessageBuilder withPublisher(String publisher) {
        this.message_publisher = publisher;
        return this;
    }

    public MessageBuilder withDate(String date) {
        this.message_date = date;
        return this;
    }

    public MessageBuilder withText(String text) {
        this.message_text = text;
        return this;
    }

    public MessageBuilder withTaskId(Integer taskId) {
        this.message_taskId = taskId;
        return this;
    }
}
