package ik.koresh.useraccountingapp.controller;

import ik.koresh.useraccountingapp.model.AppUser;
import ik.koresh.useraccountingapp.services.RegistrationService;
import ik.koresh.useraccountingapp.validations.AppUserValidator;
import ik.koresh.useraccountingapp.validations.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AppUserValidator appUserValidator;
    private final EmailValidator emailValidator;
    private final RegistrationService registrationService;

    @Autowired
    public AuthController(AppUserValidator appUserValidator, EmailValidator emailValidator, RegistrationService registrationService) {
        this.appUserValidator = appUserValidator;
        this.emailValidator = emailValidator;
        this.registrationService = registrationService;
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("appUser") AppUser appUser){
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("appUser") @Validated AppUser appUser,
                                      BindingResult bindingResult){

        appUserValidator.validate(appUser, bindingResult);
        emailValidator.validate(appUser, bindingResult);


        if(bindingResult.hasErrors())
            return "auth/registration";

        registrationService.register(appUser);

        return "redirect:/userPage";
    }

}
