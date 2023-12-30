package org.grow.canteen.repository;

import org.grow.canteen.entities.Category;
import org.grow.canteen.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryAddRepository extends JpaRepository<Category,Long> {

    @Query(value = "select c from Category c where c.id=?1 and c.isDeleted=false and c.isActive=true ")
    Optional<Category> validateCategoryById(Long id);

    @Query(value = "select c from Category c where c.isDeleted=false ")
    List<Category> getAllCategory();

    @Query(value = "select c from Category c where c.isDeleted=false and c.isActive=true ")
    List<Category> getAllActiveCategory();

    @Query("select p from Category p where p.isDeleted=false and p.id=?1")
    Optional<Category> validateCategoryByIdForActive(Long id);




}
