package silver.silvernote.responsemessage;

import lombok.Data;

@Data
public class Message {
    private HttpStatusEnum status;
    private String message;
    private Object data;

    public Message() {
        this.status = HttpStatusEnum.BAD_REQUEST;
        this.data = null;
        this.message = null;
    }

    public Message(HttpStatusEnum status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Message(HttpStatusEnum status, String message) {
        this.status = status;
        this.message = message;
    }
}
