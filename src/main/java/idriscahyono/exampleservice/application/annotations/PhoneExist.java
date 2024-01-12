package idriscahyono.exampleservice.application.annotations;

import idriscahyono.exampleservice.application.validations.PhoneExistValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneExistValidation.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneExist {
    String message() default "already exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
