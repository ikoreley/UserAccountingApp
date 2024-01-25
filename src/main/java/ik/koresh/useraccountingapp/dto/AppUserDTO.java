package ik.koresh.useraccountingapp.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AppUserDTO {
    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;
    @NotEmpty
    private String description;
    @NotEmpty
    private String password;

}
