package ik.koresh.useraccountingapp.security;

import ik.koresh.useraccountingapp.model.AppUser;
import ik.koresh.useraccountingapp.model.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class AppUserDetails implements UserDetails {
    private final String username;
    private final String password;
    private final Role role;

    public AppUserDetails(AppUser appUser){
        this.username = appUser.getUsername();
        this.password = appUser.getPassword();
        this.role = appUser.getRoles();
//        this.roles = Arrays.stream(appUser.getRoles().split(","))
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
