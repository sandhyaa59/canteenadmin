package org.grow.canteen.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.grow.canteen.constants.Route;
import org.grow.canteen.dto.order.OrderDetailResponse;
import org.grow.canteen.dto.order.OrderSaveRequest;
import org.grow.canteen.dto.Response;
import org.grow.canteen.dto.order.TodayOrderList;
import org.grow.canteen.dto.pageableDto.PageableData;
import org.grow.canteen.dto.pageableDto.Pagination;
import org.grow.canteen.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(Route.ORDER)
    private Response saveOrder(@Valid @RequestBody OrderSaveRequest request){
        log.info("Saving order::{{}}",request);
        return orderService.saveOrder(request);
    }

    @GetMapping(Route.TODAY)
    private List<TodayOrderList> getTodayOrder(){
        log.info("Get Today order::");
        return orderService.getTodaysOrder();
    }
    @GetMapping(Route.ORDER)
    private PageableData<List<TodayOrderList>> getAllOrder(@Valid Pagination pagination){
        log.info("Get All orders::{{}}",pagination);
        return orderService.getAllOrder(pagination);
    }

    @GetMapping(Route.ORDER_DETAIL)
    private OrderDetailResponse getOrderById(@PathVariable("id") Long id){
        log.info("Get  order by Id::{}",id);
        return orderService.getOrderDetailsById(id);
    }
}
