package org.grow.canteen.controller;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.grow.canteen.constants.Route;
import org.grow.canteen.dto.Admin.*;
import org.grow.canteen.dto.pageableDto.PageableData;
import org.grow.canteen.dto.pageableDto.Pagination;
import org.grow.canteen.dto.Response;
import org.grow.canteen.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class AdminController {

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping(Route.SAVE_ADMIN)
    private Response saveAdmin(@Valid @RequestBody AdminAddRequest request){
        log.info("Saving admin::{{}}",request);
        return adminService.saveAdmin(request);
    }

    @PostMapping(Route.ADMIN_LOGIN)
    private LoginResponse loginAdmin(@Valid @RequestBody LoginRequest request){
        log.info("Login admin::{{}}",request);
        return adminService.login(request);
    }

    @GetMapping(Route.LIST_ADMIN)
    private PageableData<List<AdminListResponse>> getAllAdmin(@Valid Pagination pagination){
        log.info("fetching all admin::{{}}",pagination);
        return adminService.getAllAdmin(pagination);
    }

    @GetMapping(Route.ACTIVATE_ADMIN)
    private Response activateAdmin(@PathVariable("id") Long id){
        log.info("activate  admin::{}",id);
        return adminService.activateAdmin(id);
    }

    @GetMapping(Route.DEACTIVATE_ADMIN)
    private Response deActivateAdmin(@PathVariable("id") Long id){
        log.info("deactivate  admin::{}",id);
        return adminService.deActivateAdmin(id);
    }

    @PostMapping(Route.UPDATE_ADMIN)
    private Response updateAdminDetails(@Valid @RequestBody UpdateAdminRequest request){
        log.info("update  admin::{{}}",request);
        return adminService.updateAdmin(request);
    }

    @GetMapping(Route.ADMIN_DETAIL)
    private AdminDetailResponse updateAdminDetails(@PathVariable("id") Long id){
        log.info("Admin  detail::{}",id);
        return adminService.getDetailById(id);
    }

    @GetMapping(Route.DELETE_ADMIN)
    private Response deleteAdmin(@PathVariable("id") Long id){
        log.info("delete  admin::{}",id);
        return adminService.deleteAdmin(id);
    }

    @GetMapping(Route.SEARCH_ADMIN)
    private PageableData<List<AdminListResponse>> searchKeyword(@PathVariable("keyword") String keyword,@Valid Pagination pagination){
        log.info("deactivate  admin::{}",keyword);
        return adminService.searchAdmin(pagination, keyword);
    }
}
