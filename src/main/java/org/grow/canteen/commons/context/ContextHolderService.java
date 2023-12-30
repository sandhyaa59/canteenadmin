package org.grow.canteen.commons.context;


import lombok.NoArgsConstructor;
import org.grow.canteen.constants.UserType;
import org.grow.canteen.entities.Admin;
import org.grow.canteen.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@NoArgsConstructor
public class ContextHolderService {


    private AdminRepository adminRepository;


    @Autowired
    public ContextHolderService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Context getContext(){
        return  ContextHolder.get();
    }

    public void setContext(String userName,String userType){
        if(userType.equalsIgnoreCase(UserType.USER.name())){
            this.setContextForUser(userName);
        } else if (userType.equalsIgnoreCase(UserType.ADMIN.name())) {
            this.setContextForAdmin(userName);
        }
    }
    private  void setContextForAdmin(String userName){
        Optional<Admin> optionalAdmins=adminRepository.findByUserName(userName);
        optionalAdmins.ifPresent(admins -> {
            ContextHolder thread=new ContextHolder(admins.getId(),admins.getEmailId(), UserType.ADMIN.name());
            thread.run();
        });
    }

    private  void setContextForUser(String userName){

    }
}
