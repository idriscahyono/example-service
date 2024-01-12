package idriscahyono.exampleservice.helper;

import idriscahyono.exampleservice.base.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<BaseResponse> handleRuntimeExceptions(RuntimeException r){
        List<String> errors = Collections.singletonList(r.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                BaseResponse.builder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                        .errorValidation(errors)
                        .build()
        );
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<BaseResponse> handleGeneralExceptions(Exception e){
       List<String> errors = Collections.singletonList(e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                BaseResponse.builder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                        .errorValidation(errors)
                        .build()
        );
    }

    @ExceptionHandler(UserPrincipalNotFoundException.class)
    public ResponseEntity<BaseResponse> handleNotFoundException(UserPrincipalNotFoundException u){
        List<String> errors = Collections.singletonList(u.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                BaseResponse.builder()
                        .code(HttpStatus.NOT_FOUND.value())
                        .message(HttpStatus.NOT_FOUND.getReasonPhrase())
                        .errorValidation(errors)
                        .build()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse> handleValidationErrors(MethodArgumentNotValidException m){
        List<String> errors = new ArrayList<>();
        m.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.add(fieldName + ":" + " " + errorMessage);
        });

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                BaseResponse.builder()
                        .code(HttpStatus.UNPROCESSABLE_ENTITY.value())
                        .message(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase())
                        .errorValidation(errors)
                        .build()
        );
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors){
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);

        return errorResponse;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<BaseResponse> valueUnprocessablePathParameter(ConstraintViolationException e){
        List<String> errors = new ArrayList<>();
        errors.add(e.getMessage().split(":")[1].trim());
        BaseResponse baseResponse = BaseResponse.builder()
                .message(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase())
                .errorValidation(errors)
                .code(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .build();

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(baseResponse);
    }
}
