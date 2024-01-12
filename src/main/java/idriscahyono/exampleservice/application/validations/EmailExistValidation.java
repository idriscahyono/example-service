package idriscahyono.exampleservice.application.validations;

import idriscahyono.exampleservice.application.annotations.EmailExist;
import idriscahyono.exampleservice.service.AppService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailExistValidation extends AppService implements ConstraintValidator<EmailExist, String> {
    @Override
    public void initialize(EmailExist constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return userRepository.findFirstByEmail(s).isEmpty();
    }
}
