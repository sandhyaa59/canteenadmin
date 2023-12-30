package org.grow.canteen.startup;

import jakarta.annotation.PostConstruct;
import org.grow.canteen.entities.Admin;
import org.grow.canteen.repository.AdminRepository;
import org.grow.canteen.services.AdminService;
import org.grow.canteen.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class AdminInit {

    @Autowired
    private AdminRepository adminRepository;

    @PostConstruct
    private void saveAdmin(){
        List<Admin> adminList=adminRepository.findAll();
        if (adminList.isEmpty()){
            Admin admin=new Admin();
            admin.setActive(true);
            admin.setDeleted(false);
            admin.setEmailId("superadmin@gmail.com");
            admin.setCreatedOn(LocalDateTime.now());
            admin.setFullName("Super Admin");
            admin.setPassword(SecurityUtil.encode("Admin@12"));
            admin.setMobileNumber("98121212112");
            adminRepository.save(admin);
        }
    }

}
