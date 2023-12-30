package org.grow.canteen.repository;

import org.grow.canteen.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    @Query(value = "select max(o.token_value) from orders o where DATE(o.created_at)=Date(:date)",nativeQuery = true)
    Optional<Integer> findTokenForToday(@Param("date") LocalDateTime localDateTime);

    @Query(value = "select * from orders o where DATE(o.created_at)=Date(:date) order by o.created_at desc",nativeQuery = true)
    List<Order> getTodayOrder(@Param("date") LocalDateTime localDateTime);

    @Query(value = "select o from Order o order by o.createdAt desc ")
    Page<Order> getAllOrder(Pageable pageable);

    @Query(value = "select  o from Order  o where o.id=?1")
    Optional<Order> getOrderByIdIs(Long id);

    @Query(value = "SELECT COUNT(o) AS totalOrders, COALESCE(SUM(o.total), 0) AS totalSaleAmount,COALESCE(SUM(o.subtotal), 0) AS totalSubTotal, COALESCE(SUM(o.discont_amount), 0) AS totalDiscountAmount " +
            "FROM orders o " +
            "WHERE DATE(o.created_at) BETWEEN DATE(:startDate) AND DATE(:endDate)",nativeQuery = true)
    Map<String,Object> getOrderStatistics(@Param("startDate") LocalDateTime startDate,
                                          @Param("endDate") LocalDateTime endDate);
}
