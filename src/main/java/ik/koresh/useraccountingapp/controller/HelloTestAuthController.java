package ik.koresh.useraccountingapp.controller;

import ik.koresh.useraccountingapp.model.AppUser;
import ik.koresh.useraccountingapp.services.AdminService;
import ik.koresh.useraccountingapp.services.AppUserService;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@PropertySource("")
@Controller
public class HelloTestAuthController {
    private final AdminService adminService;
    private final AppUserService appUserService;

    public HelloTestAuthController(AdminService adminService, AppUserService appUserService) {
        this.adminService = adminService;
        this.appUserService = appUserService;
    }

    @GetMapping("hello")
    public String sayHello(){
        return "user";
    }

    @GetMapping("admin")
    public String adminPage(){
        adminService.doAdminStuff();
        return "doAdminStuff";
    }

    @GetMapping("userPage")
    public String getUserPage(){
        return "user";
    }

    @GetMapping("adminPage")
    public String getAdminPage(){
        return "admin";
    }

    @GetMapping("user/delete/{id}")
    @PreAuthorize("hasAuthority('user:delete')")
    public String deleteShow(@PathVariable Long id, Model model){
        model.addAttribute("appUser", appUserService.findById(id));
        return "admin";

    }

    @DeleteMapping("user/delete/{id}")
    public String deleteUser(@PathVariable Long id){
        AppUser appUser = appUserService.findById(id);
        if (appUser != null){
            appUserService.delete(id);
            return "User was delete" + appUser;
        }
        return "Error User with id not exist";
    }


}
