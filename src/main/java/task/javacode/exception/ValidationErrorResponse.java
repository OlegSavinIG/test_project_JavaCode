package task.javacode.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ValidationErrorResponse {
    private String message;
    private Map<String, String> fieldErrors;
}

