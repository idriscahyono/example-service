package idriscahyono.exampleservice.application.validations;

import idriscahyono.exampleservice.application.annotations.PhoneExist;
import idriscahyono.exampleservice.service.AppService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneExistValidation extends AppService implements ConstraintValidator<PhoneExist, String> {
    @Override
    public void initialize(PhoneExist constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return userRepository.findFirstByPhone(s).isEmpty();
    }
}
