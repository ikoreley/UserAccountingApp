package ik.koresh.useraccountingapp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import ik.koresh.useraccountingapp.model.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "users", indexes = @Index(name = "idx_username", columnList="username"))
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Nickname is required")
    @Size(min = 3, max = 100, message = "Name must be between 2 and 100 characters long")
    @Column(unique = true, updatable = false)
    private String username;

    @NotEmpty(message = "Please enter your name")
    @Column(nullable = false)
    private String name;

    @NotEmpty(message = "Please enter your surname")
    @Column(nullable = false)
    private String surname;

    @Column(columnDefinition = "text")
    private String description;

    @NotBlank(message = "User email is required")
    @Email(message = "It should have email format")
    @Column(unique = true)
    private String email;

    @Column(length = 3000)
    @NotEmpty(message = "Password is required")
    @Size(min = 6)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role roles;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column
    private LocalDateTime updateDate;


    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //CascadeType.ALL когда удалим user удалится и его eventList
    //FetchType.LAZY получая данные user мы не получаем все его события автоматом, а только когда захотим
    private List<Event> eventList;


}

