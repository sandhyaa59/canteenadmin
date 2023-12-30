package org.grow.canteen.services;

import org.grow.canteen.dto.customer.*;
import org.grow.canteen.dto.Response;
import org.grow.canteen.dto.pageableDto.PageableData;
import org.grow.canteen.dto.pageableDto.Pagination;
import org.grow.canteen.entities.Customer;

import java.util.List;

public interface CustomerService {
    Response saveCustomer(CustomerRequest request);


    PageableData<List<CustomerListResponse>> getAllCustomer(Pagination pagination);
    Response activateCustomer(Long id);
    Response deActivateCustomer(Long id);

    Response updateCustomer(CustomerUpdateRequest request);

    Customer validateCustomer(Long id);

    Response deleteCustomer(Long id);

    PageableData<List<CustomerListResponse>> searchCustomer(Pagination pagination,String keyword);
}
