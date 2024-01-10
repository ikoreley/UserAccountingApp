package ik.koresh.useraccountingapp.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Permission {
    USER_READ("user:read"),
    USER_WRITE("user:write"),
    USER_SAVE("user:save");

    private final String permission;
}
