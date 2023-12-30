package org.grow.canteen.services;

import org.grow.canteen.dto.organization.OrganizationAddRequest;
import org.grow.canteen.dto.organization.OrganizationListResponse;


public interface OrganizationService {
    OrganizationListResponse addOrganization(OrganizationAddRequest organizationAddRequest);
}
