package org.grow.canteen.services.impl;

import org.grow.canteen.commons.context.ContextHolderService;
import org.grow.canteen.dto.organization.OrganizationAddRequest;
import org.grow.canteen.dto.organization.OrganizationListResponse;
import org.grow.canteen.entities.Organization;
import org.grow.canteen.repository.OrganizationAdd;
import org.grow.canteen.services.AdminService;
import org.grow.canteen.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private ContextHolderService contextHolderService;

   private OrganizationAdd organizationAddrepository;
    private AdminService adminService;


    @Autowired
    public OrganizationServiceImpl(ContextHolderService contextHolderService,

                                   AdminService adminService,
                                   OrganizationAdd organizationAddrepository
                             ) {

        this.contextHolderService = contextHolderService;

        this.adminService=adminService;
        this.organizationAddrepository=organizationAddrepository;
    }
    public OrganizationListResponse saveOrganization(OrganizationAddRequest request) {
        Organization organization = prepareToSaveOrganization(request);
        Organization response = organizationAddrepository.save(organization);
        return OrganizationListResponse.builder()
                .id(response.getId())
                .name(response.getOrganizationName()).contactNumber(response.getMobile_number())
              .build();
    }
    Organization prepareToSaveOrganization(OrganizationAddRequest request) {
        Organization organization = new Organization();
        organization.setActive(true);

        organization.setOrganizationName(request.getName());
        organization.setOrganization_details(request.getAddress());

        organization.setActive(true);
//        organization.setDeleted(false);
//        organization.setModifiedOn(LocalDateTime.now());
//        organization.setCreatedOn(LocalDateTime.now());
        organization.setCreatedBY(contextHolderService.getContext().getUserName());
        return organization;
    }

    @Override
    public OrganizationListResponse addOrganization(OrganizationAddRequest organizationAddRequest) {


        adminService.validateAdmin (contextHolderService.getContext().getUserId());
        Organization organization=new Organization();
        organization.setOrganizationName(organizationAddRequest.getName());
        organization.setAddress(organizationAddRequest.getAddress());
        organization.setEmailID(organizationAddRequest.getEmail_Id());
        Organization organization1=organizationAddrepository.save(organization);
        return OrganizationListResponse.builder().contactNumber(organization1.getMobile_number()).build();

    }
}
