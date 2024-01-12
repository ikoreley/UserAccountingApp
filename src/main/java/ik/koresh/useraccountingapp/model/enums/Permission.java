package ik.koresh.useraccountingapp.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Permission {
    USER_READ("user:read"),
    USER_WRITE("user:write"),
    USER_SAVE("user:save"),
    USER_DELETE("user:delete");

    private final String permission;
}
