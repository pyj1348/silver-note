package silver.silvernote.responsemessage;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ApiControllerAdvice {
    HttpHeaders headers = new HttpHeaders();

    public ApiControllerAdvice() {
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Message> handleExceptions(Exception e) {
        Map<String, String> error = new HashMap<>();
        error.put("errorClass", e.getClass().getName());
        error.put("errorMessage", e.getMessage());
        Message message = new Message(HttpStatusEnum.BAD_REQUEST, "요청을 수행할 수 없습니다", error);
        return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Message> handleValidationExceptions(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors()
                .forEach(c -> errors.put(((FieldError) c).getField(), c.getDefaultMessage()));

        Message message = new Message(HttpStatusEnum.BAD_REQUEST, "메시지 형식이 올바르지 않습니다", errors);

        return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Message> handleNotReadableExceptions(HttpMessageNotReadableException e){
        Map<String, String> error = new HashMap<>();
        error.put("errorClass", e.getClass().getName());
        error.put("errorMessage", e.getMessage());
        Message message = new Message(HttpStatusEnum.BAD_REQUEST, "메시지 형식이 올바르지 않습니다", error);
        return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Message> handleNoSuchElementExceptions(NoSuchElementException e) {
        Map<String, String> error = new HashMap<>();
        error.put("errorClass", e.getClass().getName());
        error.put("errorMessage", e.getMessage());
        Message message = new Message(HttpStatusEnum.NOT_FOUND, "조회된 요소가 없습니다", error);
        return new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
    }


}