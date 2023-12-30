package org.grow.canteen.repository;

import org.grow.canteen.entities.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long > {

    @Query("SELECT a FROM Admin  a WHERE a.emailId=?1 AND a.isActive=true and a.isDeleted=false")
    Optional<Admin> findByUserName(String userName);

    @Query("select a from Admin a where a.emailId=?1 and a.password=?2 and a.isActive=true and a.isDeleted=false")
    Optional<Admin> findUserWithUserNameAndPassword(String userName,String password);

    @Query("SELECT a FROM Admin  a WHERE a.id=?1 AND a.isActive=true and a.isDeleted=false ")
    Optional<Admin> findById(Long id);

    @Query("SELECT a FROM Admin  a WHERE a.id=?1  and a.isDeleted=false ")
    Optional<Admin> findByIdForActivate(Long id);


    @Query("SELECT a from Admin a where a.isDeleted=false ")
    Page<Admin> getAllAdmin(Pageable pageable);

    @Query("SELECT a from Admin a where a.isDeleted=false and (a.fullName LIKE %:keyword% or a.emailId LIKE %:keyword% or a.mobileNumber LIKE %:keyword%)")
    Page<Admin> searchAdmin(Pageable pageable,String keyword);

}
