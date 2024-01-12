package ik.koresh.useraccountingapp.services;

import ik.koresh.useraccountingapp.model.AppUser;
import ik.koresh.useraccountingapp.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AppUserService {
    private final AppUserRepository appUserRepository;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public AppUser findByUsername(String username){
        return appUserRepository.findByUsername(username).orElse(null);
    }

    public AppUser findByEmail(String email){
        return appUserRepository.findByEmail(email).orElse(null);
    }

    public AppUser findById(Long id){
        return appUserRepository.findById(id).orElse(null);
    }


    @Transactional
    public void delete(long id){
        appUserRepository.deleteById(id);
    }




}
