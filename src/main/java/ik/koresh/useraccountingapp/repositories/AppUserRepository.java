package ik.koresh.useraccountingapp.repositories;

import ik.koresh.useraccountingapp.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

//    @Query(value = "select * from users where username = ?1", nativeQuery = true)
    Optional<AppUser> findByUsername(String username);

}