package org.grow.canteen.services;

import org.grow.canteen.dto.order.OrderDetailResponse;
import org.grow.canteen.dto.order.OrderSaveRequest;
import org.grow.canteen.dto.Response;
import org.grow.canteen.dto.order.TodayOrderList;
import org.grow.canteen.dto.pageableDto.PageableData;
import org.grow.canteen.dto.pageableDto.Pagination;

import java.util.List;

public interface OrderService {

    Response saveOrder(OrderSaveRequest request);
    List<TodayOrderList> getTodaysOrder();
    PageableData<List<TodayOrderList>> getAllOrder(Pagination pagination);

    OrderDetailResponse getOrderDetailsById(Long id);


}
