package ik.koresh.useraccountingapp.services;

import ik.koresh.useraccountingapp.security.AppUserDetailsImpl;
import ik.koresh.useraccountingapp.model.AppUser;
import ik.koresh.useraccountingapp.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class AppUserDetailsService implements UserDetailsService {


    private final AppUserRepository appUserRepository;

    @Autowired
    public AppUserDetailsService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> appUser = appUserRepository.findByUsername(username);
        return appUser.map(AppUserDetailsImpl::new)
                .orElseThrow(()->new UsernameNotFoundException("User not found"));
    }
}
