package org.grow.canteen.repository;

import org.grow.canteen.entities.Admin;
import org.grow.canteen.entities.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationAdd extends JpaRepository<Organization,Long > {
    @Query("SELECT o FROM Organization  o WHERE o.isActive=?1 AND o.id=1 ")
    Optional<Organization> findOrganizationBy(Long id);

}
