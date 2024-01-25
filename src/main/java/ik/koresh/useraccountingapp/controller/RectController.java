package ik.koresh.useraccountingapp.controller;


import ik.koresh.useraccountingapp.model.AppUser;
import ik.koresh.useraccountingapp.repositories.AppUserRepository;
import ik.koresh.useraccountingapp.repositories.EventRepository;
import ik.koresh.useraccountingapp.services.AppUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController // @Controller + @ResponseBody над каждым методом
@RequestMapping
public class RectController {
    private final AppUserRepository appUserRepository;
    private final AppUserService appUserService;
    private final EventRepository eventRepository;
    private final PasswordEncoder passwordEncoder;

    public RectController(AppUserRepository appUserRepository, AppUserService appUserService, EventRepository eventRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.appUserService = appUserService;
        this.eventRepository = eventRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String home(){
        return "This is publicly accessible withing needing authentication";
    }

    @PostMapping("/api/user/save")
    @PreAuthorize("hasAuthority('user:save')")
    public ResponseEntity<Object> saveUser(@RequestBody AppUser appUser){
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        AppUser result = appUserRepository.save(appUser);
        if (result.getId() > 0 ){
            return ResponseEntity.ok("User was saved");
        }
        return ResponseEntity.status(404).body("Error User with name not saved");
    }


    @GetMapping("/api/event/all")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<Object> getAllEvents(){
        return ResponseEntity.ok(eventRepository.findAll());

    }

//    @Cacheable("users_all")
    @GetMapping("/api/users/all")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<Object> getAllAppUser(){
        return ResponseEntity.ok(appUserRepository.findAll());

    }

    @GetMapping("/api/user/single")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<Object> getCurrentAppUser(){
        return ResponseEntity.ok(appUserRepository
                .findByUsername(getLoggedInAppUserDetails().getUsername()));
    }

    public UserDetails getLoggedInAppUserDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication !=null && authentication.getPrincipal() instanceof UserDetails){
            return (UserDetails) authentication.getPrincipal();
        }
        return null;
    }
    @GetMapping("/api/user/single1")
    public ResponseEntity<Object> getCurrentAppUser1(){
        return ResponseEntity.ok(
                appUserRepository.findByUsername(getLoggedInAppUserDetails_2().getUsername()));
    }

    public UserDetails getLoggedInAppUserDetails_2(){
        return (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
    }

    @GetMapping("/api/user/role")
    public ResponseEntity<?> getCurrentAppUserAuthorities(){
        return ResponseEntity.ok(
                getLoggedInAppUserDetails().getAuthorities());
    }

    @GetMapping("/api/user/role2")
    public ResponseEntity<?> getCurrentAppUserRole(){
        return ResponseEntity.ok(
                getLoggedInAppUserDetails_2().getAuthorities());
    }

}
