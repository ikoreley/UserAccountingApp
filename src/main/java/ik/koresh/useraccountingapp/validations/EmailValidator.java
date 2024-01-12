package ik.koresh.useraccountingapp.validations;

import ik.koresh.useraccountingapp.model.AppUser;
import ik.koresh.useraccountingapp.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class EmailValidator implements Validator {

    private final AppUserService appUserService;

    @Autowired
    public EmailValidator(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return AppUser.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AppUser appUser = (AppUser) target;
        if (appUserService.findByEmail(appUser.getEmail()) != null)
            errors.rejectValue("email", "", "A email already exists");
    }
}
