package ik.koresh.useraccountingapp.services;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @PreAuthorize("hasAuthority('user:save')")
    public void doAdminStuff(){
        System.out.println("Only admin here");
    }
}
