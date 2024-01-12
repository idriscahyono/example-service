package idriscahyono.exampleservice.application.annotations;
import idriscahyono.exampleservice.application.validations.UsernameExistValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameExistValidation.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameExist {
    String message() default "already exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
