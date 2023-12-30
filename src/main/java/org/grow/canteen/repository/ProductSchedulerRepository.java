package org.grow.canteen.repository;

import org.grow.canteen.constants.Days;
import org.grow.canteen.entities.ProductScheduler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductSchedulerRepository extends JpaRepository<ProductScheduler,Long> {


    @Query(value = "select p from ProductScheduler p where p.id=?1 and p.isDeleted=false ")
    Optional<ProductScheduler> validateByIdForActive(Long id);

    @Query("select ps from ProductScheduler ps where ps.isDeleted=false and ps.isActive=true ")
    List<ProductScheduler> getAllActiveProduct();

    @Query("select ps from ProductScheduler ps where ps.isDeleted=false  ")
    List<ProductScheduler> getAllScheduledProduct();

    @Query("select ps from ProductScheduler ps where ps.isDeleted=false and ps.isActive=true and ps.days=?1")
    List<ProductScheduler> getScheduledProductByDays(Days days);
}
