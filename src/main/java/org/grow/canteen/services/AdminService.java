package org.grow.canteen.services;

import org.grow.canteen.dto.Admin.*;
import org.grow.canteen.dto.pageableDto.PageableData;
import org.grow.canteen.dto.pageableDto.Pagination;
import org.grow.canteen.dto.Response;

import java.util.List;

public interface AdminService {

    Response saveAdmin(AdminAddRequest request);
    LoginResponse login(LoginRequest request);

    PageableData<List<AdminListResponse>> getAllAdmin(Pagination pagination);
    Response activateAdmin(Long id);
   Response deActivateAdmin(Long id);

   Response updateAdmin(UpdateAdminRequest request);

   void validateAdmin(Long id);

   AdminDetailResponse getDetailById(Long id);

   Response deleteAdmin(Long id);

    PageableData<List<AdminListResponse>> searchAdmin(Pagination pagination,String keyword);
}
