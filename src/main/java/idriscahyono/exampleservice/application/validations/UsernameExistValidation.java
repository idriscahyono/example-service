package idriscahyono.exampleservice.application.validations;

import idriscahyono.exampleservice.application.annotations.UsernameExist;
import idriscahyono.exampleservice.service.AppService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameExistValidation extends AppService implements ConstraintValidator<UsernameExist, String> {
    @Override
    public void initialize(UsernameExist constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return userRepository.findFirstByUsername(s).isEmpty();
    }
}
