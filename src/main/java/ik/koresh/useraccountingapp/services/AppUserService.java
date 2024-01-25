package ik.koresh.useraccountingapp.services;

import ik.koresh.useraccountingapp.dto.AppUserDTO;
import ik.koresh.useraccountingapp.model.AppUser;
import ik.koresh.useraccountingapp.repositories.AppUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<AppUser> findByUsername(String username){
        return appUserRepository.findByUsername(username);
    }

    public Optional<AppUser> findByEmail(String email){
        return appUserRepository.findByEmail(email);
    }


    public Optional<AppUser> findById(Long id){
        return appUserRepository.findById(id);
    }


    public List<AppUser> getAppUserAll(){
        return appUserRepository.findAll();
    }


    @Transactional
    public void delete(long id){
        appUserRepository.deleteById(id);
    }

    @Transactional
    public void updateAdmin(Long id, AppUser updateAppUser){
        updateAppUser.setId(id);
        AppUser appUser = findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with ID " + id));
        appUser.setId(updateAppUser.getId());
        appUser.setUsername(updateAppUser.getUsername());
        appUser.setName(updateAppUser.getName());
        appUser.setSurname(updateAppUser.getSurname());
        appUser.setDescription(updateAppUser.getDescription());
        if(updateAppUser.getPassword().isEmpty()){
            appUser.setPassword(appUser.getPassword());
        }else {
            appUser.setPassword(passwordEncoder.encode(updateAppUser.getPassword()));
        }
        appUserRepository.save(appUser);
    }

    @Transactional
    public void updateUser(AppUserDTO appUserDTO, Principal principal){
        AppUser appUser = getCurrentAppUser(principal);
        appUser.setName(appUserDTO.getName());
        appUser.setSurname(appUserDTO.getSurname());
        appUser.setPassword(passwordEncoder.encode(appUserDTO.getPassword()));
        appUserRepository.save(appUser);
    }

    private AppUser getCurrentAppUser(Principal principal){
        String username = principal.getName();
        return findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));
    }



}
