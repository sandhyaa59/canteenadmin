package org.grow.canteen.repository;

import org.grow.canteen.entities.Admin;
import org.grow.canteen.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    @Query("SELECT a FROM Customer  a WHERE a.id=?1 AND a.isActive=true and a.isDeleted=false ")
    Optional<Customer> findCustomerById(Long id);

    @Query("SELECT a FROM Customer  a WHERE a.id=?1  and a.isDeleted=false ")
    Optional<Customer> findByIdForActivate(Long id);


    @Query("SELECT a from Customer a where a.isDeleted=false ")
    Page<Customer> getAllCustomer(Pageable pageable);

    @Query("SELECT a from Customer a where a.isDeleted=false and (a.customerName LIKE %:keyword% or a.emailId LIKE %:keyword% or a.mobileNumber LIKE %:keyword%)")
    Page<Customer> searchCustomer(Pageable pageable,String keyword);
}
