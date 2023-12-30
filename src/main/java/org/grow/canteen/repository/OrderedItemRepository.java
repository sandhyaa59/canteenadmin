package org.grow.canteen.repository;

import org.grow.canteen.entities.Order;
import org.grow.canteen.entities.OrderedItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderedItemRepository extends JpaRepository<OrderedItem,Long> {

    @Query(value = "select oi from OrderedItem  oi where oi.orderId=?1")
    List<OrderedItem> getOrderedItemById(Order order);
}
