package employeedepartmentreactive.model.error;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private String traceId;
    private OffsetDateTime timestamp;
    private HttpStatus status;
    private String message;
    private List<ErrorDetails> errors;
}
