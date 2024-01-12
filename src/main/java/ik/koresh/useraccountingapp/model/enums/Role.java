package ik.koresh.useraccountingapp.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum Role {
    ROLE_USER(Set.of(Permission.USER_READ)),
    ROLE_ADMIN(Set.of(Permission.USER_READ, Permission.USER_WRITE, Permission.USER_SAVE, Permission.USER_DELETE));

    private final Set<Permission> permissions;


    public Set<SimpleGrantedAuthority> getAuthorities(){
        return getPermissions().stream()
                .map(p -> new SimpleGrantedAuthority(p.getPermission()))
                .collect(Collectors.toSet());
    }
}
