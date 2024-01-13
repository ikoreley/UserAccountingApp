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
        if(appUserService.findById(id) == null){
            return "forward:/users/all";
        }
        model.addAttribute("appUser", appUserService.findById(id));
        return "delete";

    }

    @DeleteMapping("user/delete/{id}")
    public String deleteUser(@PathVariable Long id){
        appUserService.delete(id);
        return "redirect:http://localhost:8080/users/all";
    }


}
