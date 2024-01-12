package idriscahyono.exampleservice.application.validations;

import idriscahyono.exampleservice.application.annotations.UsernameValid;
import idriscahyono.exampleservice.service.AppService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameValidation extends AppService implements ConstraintValidator<UsernameValid, String> {
   @Override
    public void initialize(UsernameValid usernameValid){
       ConstraintValidator.super.initialize(usernameValid);
   }
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return userRepository.findFirstByUsername(s).isPresent();
    }
}
