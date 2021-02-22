package silver.silvernote.responsemessage;

public enum HttpStatusEnum {

    OK(200, "OK"),
    CREATED(201, "CREATED"),
    NO_CONTENT(204, "NO_CONTENT"),
    BAD_REQUEST(400, "BAD_REQUEST"),
    UNAUTHORIZED(401, "UNAUTHORIZED"),
    FORBIDDEN(403, "FORBIDDEN"),
    NOT_FOUND(404, "NOT_FOUND"),
    INTERNAL_SERER_ERROR(500, "INTERNAL_SERVER_ERROR");

    int statusCode;
    String code;

    HttpStatusEnum(int statusCode, String code) {
        this.statusCode = statusCode;
        this.code = code;
    }
}
