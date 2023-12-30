package org.grow.canteen.repository;

import org.grow.canteen.entities.Admin;
import org.grow.canteen.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("select p from Product p where p.isActive=true and p.isDeleted=false and p.id=?1")
    Optional<Product> validateProductById(Long id);

    @Query("select p from Product p where p.isDeleted=false and p.id=?1")
    Optional<Product> validateProductByIdForActive(Long id);

    @Query("select p from Product p where p.isDeleted=false ")
    Page<Product> getAllProduct(Pageable pageable);

    @Query("SELECT p from Product p where p.isDeleted=false and (p.title LIKE %:keyword% )")
    Page<Product> searchProduct(Pageable pageable,String keyword);

    @Query("select p from Product p where p.isDeleted=false and p.isActive=true ")
    List<Product> getAllActiveProducts();
}
