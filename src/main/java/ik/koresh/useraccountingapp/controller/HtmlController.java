package ik.koresh.useraccountingapp.controller;

import ik.koresh.useraccountingapp.model.AppUser;
import ik.koresh.useraccountingapp.services.AppUserService;
import ik.koresh.useraccountingapp.validations.AppUserValidator;
import ik.koresh.useraccountingapp.validations.EmailValidator;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;



@Controller
@RequestMapping("/html")
public class HtmlController {
    private final AppUserValidator appUserValidator;
    private final EmailValidator emailValidator;

    private final String REDIRECT_USERS_ALL = "redirect:/html/admin/all";

    private final AppUserService appUserService;

    public HtmlController(AppUserValidator appUserValidator, EmailValidator emailValidator, AppUserService appUserService) {
        this.appUserValidator = appUserValidator;
        this.emailValidator = emailValidator;
        this.appUserService = appUserService;
    }


    @GetMapping("/userPage")
    public String UserPage(Principal principal, Model model){
        var authentication = (Authentication) principal;
        var userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("appUser", userDetails);
        return "html/user";
    }

    @GetMapping("/adminPage")
    public String AdminPage(@AuthenticationPrincipal UserDetails userDetails, Model model){
        model.addAttribute("admin", userDetails);
        return "html/admin";
    }

    @GetMapping("/{id}")
    public String userShow(@PathVariable("id") Long id,  Model model){
        model.addAttribute("user", appUserService.findById(id).get());
        return "html/showUser";
    }
    @GetMapping("/admin/all")
    public String usersAllPage(Model model){
        model.addAttribute("users", appUserService.getAppUserAll());
        return "html/usersAll";
    }

    @GetMapping("/{id}/delete")
    @PreAuthorize("hasAuthority('user:delete')")
    public String deletePage(@PathVariable("id") Long id, Model model){
        final Optional<AppUser> user = appUserService.findById(id);
        if(user.isEmpty()){
            return REDIRECT_USERS_ALL;
        }
        model.addAttribute("appUser", user.get());

        return "html/delete";
    }

    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasAuthority('user:delete')")
    public String deleteUser(@PathVariable("id") Long id){
        appUserService.delete(id);
        return REDIRECT_USERS_ALL;
    }


    @GetMapping("/{id}/update")
    @PreAuthorize("hasAuthority('user:delete')")
    public String updateUserPage(@PathVariable("id") Long id, Model model){
        final Optional<AppUser> user = appUserService.findById(id);
        if(user.isEmpty()){
            return REDIRECT_USERS_ALL;
        }
        model.addAttribute("appUser", user.get());
        return "html/update";

    }

    @PatchMapping("/{id}/update")
    @PreAuthorize("hasAuthority('user:delete')")
    public String updateUserAdmin(@ModelAttribute("appUser") @Validated AppUser appUser,
                                  BindingResult bindingResult, @PathVariable("id") Long id){

        //todo: обойти валидацию проверки нетронутых данных.
        // сейчас выйдет ошибка при изменении уникальных данных на данные других юзеров
//        appUserValidator.validate(appUser, bindingResult);
//        emailValidator.validate(appUser, bindingResult);

        if(bindingResult.hasErrors())
            return "html/update";

        appUserService.updateAdmin(id, appUser);

        return "redirect:/html/" + id;
    }


}
