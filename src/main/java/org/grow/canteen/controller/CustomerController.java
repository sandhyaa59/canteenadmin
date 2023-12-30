package org.grow.canteen.controller;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.grow.canteen.constants.Route;
import org.grow.canteen.dto.customer.*;
import org.grow.canteen.dto.Response;
import org.grow.canteen.dto.customer.CustomerListResponse;
import org.grow.canteen.dto.customer.CustomerRequest;
import org.grow.canteen.dto.customer.CustomerUpdateRequest;
import org.grow.canteen.dto.pageableDto.PageableData;
import org.grow.canteen.dto.pageableDto.Pagination;
import org.grow.canteen.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class CustomerController {
    
    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(Route.SAVE_CUSTOMER)
    private Response saveCustomer(@Valid @RequestBody CustomerRequest request){
        log.info("Saving Customer::{{}}",request);
        return customerService.saveCustomer(request);
    }



    @GetMapping(Route.LIST_CUSTOMER)
    private PageableData<List<CustomerListResponse>> getAllCustomer(@Valid Pagination pagination){
        log.info("fetching all Customer::{{}}",pagination);
        return customerService.getAllCustomer(pagination);
    }

    @GetMapping(Route.ACTIVATE_CUSTOMER)
    private Response activateCustomer(@PathVariable("id") Long id){
        log.info("activate  Customer::{}",id);
        return customerService.activateCustomer(id);
    }

    @GetMapping(Route.DEACTIVATE_CUSTOMER)
    private Response deActivateCustomer(@PathVariable("id") Long id){
        log.info("deactivate  Customer::{}",id);
        return customerService.deActivateCustomer(id);
    }

    @PostMapping(Route.UPDATE_CUSTOMER)
    private Response updateCustomerDetails(@Valid @RequestBody CustomerUpdateRequest request){
        log.info("update  Customer::{{}}",request);
        return customerService.updateCustomer(request);
    }



    @GetMapping(Route.DELETE_CUSTOMER)
    private Response deleteCustomer(@PathVariable("id") Long id){
        log.info("delete  Customer::{}",id);
        return customerService.deleteCustomer(id);
    }

    @GetMapping(Route.SEARCH_CUSTOMER)
    private PageableData<List<CustomerListResponse>> searchKeyword(@PathVariable("keyword") String keyword,@Valid Pagination pagination){
        log.info("deactivate  Customer::{}",keyword);
        return customerService.searchCustomer(pagination, keyword);
    }
}
