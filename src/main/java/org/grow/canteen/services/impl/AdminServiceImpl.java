package org.grow.canteen.services.impl;

import org.grow.canteen.commons.context.ContextHolderService;
import org.grow.canteen.commons.exceptions.RestException;
import org.grow.canteen.constants.UserType;
import org.grow.canteen.dto.Admin.*;
import org.grow.canteen.dto.pageableDto.PageableData;
import org.grow.canteen.dto.pageableDto.Pagination;
import org.grow.canteen.dto.Response;
import org.grow.canteen.entities.Admin;
import org.grow.canteen.repository.AdminRepository;
import org.grow.canteen.services.AdminService;
import org.grow.canteen.utils.JwtTokenUtil;
import org.grow.canteen.utils.PaginationUtil;
import org.grow.canteen.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;


@Service
public class AdminServiceImpl implements AdminService {

    private AdminRepository adminRepository;
    private ContextHolderService contextHolderService;
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository,JwtTokenUtil jwtTokenUtil,
                            ContextHolderService contextHolderService) {
        this.adminRepository = adminRepository;
        this.contextHolderService = contextHolderService;
        this.jwtTokenUtil=jwtTokenUtil;
    }

    @Override
    public Response saveAdmin(AdminAddRequest request) {
        validateAdminByEmail(request.getEmail());
        Admin admin=prepareToSaveAdmin(request);
        Admin response=adminRepository.save(admin);

        return Response.builder().id(response.getId()).build();
    }

    void validateAdminByEmail(String email){
        Optional<Admin> adminOptional=adminRepository.findByUserName(email);
        if(adminOptional.isPresent()){
           throw  new RestException("A002", "Admin already exist");
        }
    }

    @Override
    public LoginResponse login(LoginRequest request) {
      Optional<Admin> adminOptional= adminRepository.findUserWithUserNameAndPassword(request.getUserName(),SecurityUtil.encode(request.getPassword()) );
   if(adminOptional.isPresent()){
       final String token= jwtTokenUtil.generateToken(prepareClaims(request.getUserName(),adminOptional.get().getId(), UserType.ADMIN.name()));
       return  LoginResponse.builder().id(adminOptional.get().getId()).token(token).build();
   }else {
       throw new RestException("L001","Invalid UserName and Password");
   }

    }

    @Override
    public PageableData<List<AdminListResponse>> getAllAdmin(Pagination pagination) {
        validateAdminById(contextHolderService.getContext().getUserId());
        return getAdminListResponses(pagination);
    }

    @Override
    public Response activateAdmin(Long id) {
        validateAdminById(contextHolderService.getContext().getUserId());
        Admin activateAdmin=validateAdminByIdForActive(id);
        activateAdmin.setActive(true);
        activateAdmin.setModifiedOn(LocalDateTime.now());
        adminRepository.save(activateAdmin);
        return Response.builder().id(activateAdmin.getId()).build();
    }

 private   Admin validateAdminById(Long id){
        Optional<Admin> adminOptional=adminRepository.findById(id);
        if (adminOptional.isEmpty()){
            throw new RestException("A003","Admin doesnot exists");
        }else {
            return adminOptional.get();
        }
    }
    private   Admin validateAdminByIdForActive(Long id){
        Optional<Admin> adminOptional=adminRepository.findByIdForActivate(id);
        if (adminOptional.isEmpty()){
            throw new RestException("A003","Admin doesnot exists");
        }else {
            return adminOptional.get();
        }
    }


    private PageableData<List<AdminListResponse>> getAdminListResponses(Pagination pagination) {
        List<AdminListResponse> adminListResponseList=new ArrayList<>();
        Pageable pageable = PaginationUtil.performPagination(pagination);
        Page<Admin> adminList=adminRepository.getAllAdmin(pageable);
        adminList.forEach(admin -> {
            AdminListResponse adminListResponse=prepareToSendAdminListResponse(admin);
            adminListResponseList.add(adminListResponse);
        });
        return new PageableData<>(adminListResponseList, adminList.getTotalPages(), adminList.getTotalElements(), pagination.getPage());
    }

    @Override
    public Response deActivateAdmin(Long id) {
        validateAdminById(contextHolderService.getContext().getUserId());
        Admin activateAdmin=validateAdminByIdForActive(id);
        activateAdmin.setActive(false);
        activateAdmin.setModifiedOn(LocalDateTime.now());
        adminRepository.save(activateAdmin);
        return Response.builder().id(activateAdmin.getId()).build();
    }

    @Override
    public Response updateAdmin(UpdateAdminRequest request) {
        validateAdminById(contextHolderService.getContext().getUserId());
       Admin admin= validateAdminByIdForActive(request.getId());
       admin=prepareToUpdate(request,admin);
       adminRepository.save(admin);
        return Response.builder().id(admin.getId()).build();
    }

    @Override
    public void validateAdmin(Long id) {
        validateAdminById(id);
    }

    @Override
    public AdminDetailResponse getDetailById(Long id) {
        validateAdmin(contextHolderService.getContext().getUserId());
       Optional<Admin> adminOptional= adminRepository.findById(id);
      Admin admin= adminOptional.orElseThrow(() -> new RestException("A001","Admin not found"));
      return AdminDetailResponse.builder().checker(admin.getChecker()).isActive(admin.isActive())
              .maker(admin.getMaker()).createdOn(admin.getCreatedOn()).emailId(admin.getEmailId())
              .fullName(admin.getFullName()).mobileNumber(admin.getMobileNumber()).id(admin.getId()).build();
    }

    @Override
    public Response deleteAdmin(Long id) {
        validateAdmin(contextHolderService.getContext().getUserId());
        Admin admin=validateAdminByIdForActive(id);
        admin.setDeleted(true);
        admin.setDeletedBy(contextHolderService.getContext().getUserName());
        admin.setModifiedOn(LocalDateTime.now());
        adminRepository.save(admin);
        return  Response.builder().id(admin.getId()).build();
    }

    @Override
    public PageableData<List<AdminListResponse>> searchAdmin(Pagination pagination,String keyword) {
        validateAdmin(contextHolderService.getContext().getUserId());
        List<AdminListResponse> adminListResponseList=new ArrayList<>();
        Pageable pageable = PaginationUtil.performPagination(pagination);
        Page<Admin> adminList=adminRepository.searchAdmin(pageable,keyword);
        adminList.forEach(admin -> {
            AdminListResponse adminListResponse=prepareToSendAdminListResponse(admin);
            adminListResponseList.add(adminListResponse);
        });
        return new PageableData<>(adminListResponseList, adminList.getTotalPages(), adminList.getTotalElements(), pagination.getPage());
    }

   private Admin prepareToUpdate(UpdateAdminRequest request,Admin admin){
    admin.setFullName(request.getFullName());
    if(request.getPassword()!=null){
    admin.setPassword(SecurityUtil.encode(request.getPassword()));}
    admin.setMobileNumber(request.getMobileNumber());
    return admin;
}

  private   AdminListResponse prepareToSendAdminListResponse(Admin admin){
        AdminListResponse response=new AdminListResponse();
        response.setId(admin.getId());
        response.setMobileNumber(admin.getMobileNumber());
        response.setFullName(admin.getFullName());
        response.setEmail(admin.getEmailId());
        response.setCreatedAt(admin.getCreatedOn());
        response.setActive(admin.isActive());
        return response;
    }

    private Map<String, Object> prepareClaims(String email, Long id, String type){
        Map<String,Object> claims=new HashMap<>();
        claims.put("id",id);
        claims.put("userName",email);
        claims.put("userType",type);
        return claims;
    }

  private   Admin prepareToSaveAdmin(AdminAddRequest request){
        Admin admin=new Admin();
        admin.setActive(true);
        admin.setDeleted(false);
        admin.setEmailId(request.getEmail());
        admin.setCreatedOn(LocalDateTime.now());
        admin.setFullName(request.getFullName());
        admin.setPassword(SecurityUtil.encode(request.getPassword()));
        admin.setMobileNumber(request.getMobileNumber());
        return admin;
    }
}
