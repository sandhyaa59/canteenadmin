package org.grow.canteen.controller;

import jakarta.validation.Valid;
import org.grow.canteen.constants.Route;
import org.grow.canteen.dto.organization.OrganizationAddRequest;
import org.grow.canteen.dto.organization.OrganizationListResponse;
import org.grow.canteen.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrganizationController {

    private OrganizationService organizationService;

    @Autowired
    public OrganizationController(OrganizationService organizationService) {

        this.organizationService = organizationService;
    }

    @PostMapping(Route.Organization)
    private OrganizationListResponse addOrganization(@Valid @RequestBody OrganizationAddRequest request){
//        log.info("Saving admin::{}",request);
        return organizationService.addOrganization(request);
    }
}
