package org.grow.canteen.services.impl;

import org.grow.canteen.commons.context.ContextHolderService;
import org.grow.canteen.commons.exceptions.RestException;
import org.grow.canteen.dto.Admin.AdminAddRequest;
import org.grow.canteen.dto.Admin.AdminListResponse;
import org.grow.canteen.dto.Admin.UpdateAdminRequest;
import org.grow.canteen.dto.Response;
import org.grow.canteen.dto.customer.CustomerListResponse;
import org.grow.canteen.dto.customer.CustomerRequest;
import org.grow.canteen.dto.customer.CustomerUpdateRequest;
import org.grow.canteen.dto.pageableDto.PageableData;
import org.grow.canteen.dto.pageableDto.Pagination;
import org.grow.canteen.dto.product.ProductListResponse;
import org.grow.canteen.entities.Admin;
import org.grow.canteen.entities.Customer;
import org.grow.canteen.entities.Product;
import org.grow.canteen.repository.CustomerRepository;
import org.grow.canteen.services.AdminService;
import org.grow.canteen.services.CustomerService;
import org.grow.canteen.utils.PaginationUtil;
import org.grow.canteen.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private AdminService adminService;
    private ContextHolderService contextHolderService;
    private CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(AdminService adminService, ContextHolderService contextHolderService, CustomerRepository customerRepository) {
        this.adminService = adminService;
        this.contextHolderService = contextHolderService;
        this.customerRepository = customerRepository;
    }

    @Override
    public Response saveCustomer(CustomerRequest request) {
        adminService.validateAdmin(contextHolderService.getContext().getUserId());
        Customer customer=prepareToSaveCustomer(request);
        Customer response=customerRepository.save(customer);

        return Response.builder().id(response.getId()).build();
    }

   private Customer prepareToSaveCustomer(CustomerRequest request){
        Customer customer=new Customer();
        customer.setActive(true);
        customer.setDeleted(false);
        customer.setAddress(request.getAddress());
        customer.setEmailId(request.getEmail());
        customer.setCreatedOn(LocalDateTime.now());
        customer.setCustomerName(request.getName());

        customer.setMobileNumber(request.getMobileNumber());
        return customer;
    }

    @Override
    public PageableData<List<CustomerListResponse>> getAllCustomer(Pagination pagination) {
        adminService.validateAdmin(contextHolderService.getContext().getUserId());

        return getCustomerListResponses(pagination);
    }

    private PageableData<List<CustomerListResponse>> getCustomerListResponses(Pagination pagination) {
        List<CustomerListResponse> CustomerListResponse=new ArrayList<>();
        Pageable pageable = PaginationUtil.performPagination(pagination);
        Page<Customer> customerPage=customerRepository.getAllCustomer(pageable);
        customerPage.forEach(admin -> {
            CustomerListResponse customerListResponses=prepareToSendCustomerListResponse(admin);
            CustomerListResponse.add(customerListResponses);
        });
        return new PageableData<>(CustomerListResponse, customerPage.getTotalPages(), customerPage.getTotalElements(), pagination.getPage());
    }

    private   CustomerListResponse prepareToSendCustomerListResponse(Customer customer){
        CustomerListResponse response=new CustomerListResponse();
        response.setId(customer.getId());
        response.setMobileNumber(customer.getMobileNumber());
        response.setName(customer.getCustomerName());
        response.setEmail(customer.getEmailId());
        response.setCreatedOn(customer.getCreatedOn());
        response.setAddress(customer.getAddress());
        response.setActive(customer.isActive());
        return response;
    }

    @Override
    public Response activateCustomer(Long id) {
     adminService.validateAdmin(contextHolderService.getContext().getUserId());
        Customer activateCustomer=validateCustomerByIdForActive(id);
        activateCustomer.setActive(true);
        activateCustomer.setModifiedOn(LocalDateTime.now());
        customerRepository.save(activateCustomer);
        return Response.builder().id(activateCustomer.getId()).build();
    }


    private   Customer validateCustomerByIdForActive(Long id){
        Optional<Customer> customerOptional=customerRepository.findByIdForActivate(id);
        if (customerOptional.isEmpty()){
            throw new RestException("A003","Customer doesnot exists");
        }else {
            return customerOptional.get();
        }
    }

    @Override
    public Response deActivateCustomer(Long id) {
        adminService.validateAdmin(contextHolderService.getContext().getUserId());
        Customer activateCustomer=validateCustomerByIdForActive(id);
        activateCustomer.setActive(false);
        activateCustomer.setModifiedOn(LocalDateTime.now());
        customerRepository.save(activateCustomer);
        return Response.builder().id(activateCustomer.getId()).build();
    }

    @Override
    public Response updateCustomer(CustomerUpdateRequest request) {
       adminService.validateAdmin (contextHolderService.getContext().getUserId());
        Customer customer= validateCustomerByIdForActive(request.getId());
        customer=prepareToUpdate(request,customer);
        customerRepository.save(customer);
        return Response.builder().id(customer.getId()).build();
    }
    private Customer prepareToUpdate(CustomerUpdateRequest request, Customer customer){
        customer.setCustomerName(request.getName());
        customer.setEmailId(request.getEmail());
        customer.setAddress(request.getAddress());
        customer.setModifiedOn(LocalDateTime.now());
        customer.setMobileNumber(request.getMobileNumber());
        return customer;
    }

    @Override
    public Customer validateCustomer(Long id) {
        Optional<Customer> adminOptional=customerRepository.findCustomerById(id);
        if (adminOptional.isEmpty()){
            throw new RestException("A003","Customer doesnot exists");
        }else {
            return adminOptional.get();
        }
    }

    @Override
    public Response deleteCustomer(Long id) {
        adminService.validateAdmin(contextHolderService.getContext().getUserId());
        Customer activateCustomer=validateCustomerByIdForActive(id);
        activateCustomer.setDeleted(true);
        activateCustomer.setModifiedOn(LocalDateTime.now());
        customerRepository.save(activateCustomer);
        return Response.builder().id(activateCustomer.getId()).build();
    }

    @Override
    public PageableData<List<CustomerListResponse>> searchCustomer(Pagination pagination, String keyword) {
        adminService.validateAdmin(contextHolderService.getContext().getUserId());
        List<CustomerListResponse> customerListResponses=new ArrayList<>();
        Pageable pageable = PaginationUtil.performPagination(pagination);
        Page<Customer> customers=customerRepository.searchCustomer(pageable,keyword);
        customers.forEach(customer -> {
            CustomerListResponse toSendCustomerListResponse=prepareToSendCustomerListResponse(customer);
            customerListResponses.add(toSendCustomerListResponse);
        });
        return new PageableData<>(customerListResponses, customers.getTotalPages(), customers.getTotalElements(), pagination.getPage());
    }
}
