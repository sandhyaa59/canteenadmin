package org.grow.canteen.services.impl;

import jakarta.transaction.Transactional;
import org.aspectj.weaver.ast.Or;
import org.grow.canteen.commons.context.ContextHolderService;
import org.grow.canteen.commons.exceptions.RestException;
import org.grow.canteen.dto.order.*;
import org.grow.canteen.dto.Response;
import org.grow.canteen.dto.pageableDto.PageableData;
import org.grow.canteen.dto.pageableDto.Pagination;
import org.grow.canteen.entities.Order;
import org.grow.canteen.entities.OrderedItem;
import org.grow.canteen.entities.Product;
import org.grow.canteen.repository.OrderRepository;
import org.grow.canteen.repository.OrderedItemRepository;
import org.grow.canteen.services.AdminService;
import org.grow.canteen.services.OrderService;
import org.grow.canteen.services.ProductService;
import org.grow.canteen.utils.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private AdminService adminService;
    private ProductService productService;
    private ContextHolderService contextHolderService;
    private OrderRepository orderRepository;

    private OrderedItemRepository orderedItemRepository;

    @Autowired
    public OrderServiceImpl(AdminService adminService, ProductService productService, ContextHolderService contextHolderService,
                            OrderRepository orderRepository,OrderedItemRepository orderedItemRepository) {
        this.adminService = adminService;
        this.productService = productService;
        this.contextHolderService = contextHolderService;
        this.orderRepository = orderRepository;
        this.orderedItemRepository=orderedItemRepository;
    }

    @Transactional
    @Override
    public Response saveOrder(OrderSaveRequest request) {
        adminService.validateAdmin(contextHolderService.getContext().getUserId());
        Order order=prepareToSaveOrder(request);
        Order response=orderRepository.save(order);
        request.getOrderItem().forEach(request1 -> {
         Product product= productService.validateProductById(request1.getProductId());
         OrderedItem item=   prepareToSaveOrderedItem(request1);
         item.setProduct(product);
         item.setOrderId(response);
         orderedItemRepository.save(item);
        });
        return Response.builder().id(response.getId()).build();
    }

    @Override
    public List<TodayOrderList> getTodaysOrder() {
        adminService.validateAdmin(contextHolderService.getContext().getUserId());
        List<TodayOrderList> todayOrderLists=new ArrayList<>();
        List<Order> orderList=orderRepository.getTodayOrder(LocalDateTime.now());
        orderList.forEach(order -> {
            TodayOrderList todayOrderList=prepareToSendOrder(order);
            todayOrderLists.add(todayOrderList);
        });
        return todayOrderLists;
    }

    @Override
    public PageableData<List<TodayOrderList>> getAllOrder(Pagination pagination) {
        adminService.validateAdmin(contextHolderService.getContext().getUserId());
        List<TodayOrderList> todayOrderLists=new ArrayList<>();
        Pageable pageable = PaginationUtil.performPagination(pagination);
        Page<Order> orderList=orderRepository.getAllOrder(pageable);
        orderList.forEach(order -> {
            TodayOrderList todayOrderList=prepareToSendOrder(order);
            todayOrderLists.add(todayOrderList);
        });
        return new PageableData<>(todayOrderLists, orderList.getTotalPages(), orderList.getTotalElements(), pagination.getPage());
    }

    @Override
    public OrderDetailResponse getOrderDetailsById(Long id) {
        adminService.validateAdmin(contextHolderService.getContext().getUserId());
        Order order=validateOrder(id);
        List<OrderedItem> orderedItems=orderedItemRepository.getOrderedItemById(order);
        List<OrderItemResponse> responses=new ArrayList<>();
        orderedItems.forEach(orderedItem -> {
            OrderItemResponse orderItemResponse=preprareToSendResponse(orderedItem);
            responses.add(orderItemResponse);
        });
        return getOrderDetailResponse(responses, order);
    }

    private static OrderDetailResponse getOrderDetailResponse(List<OrderItemResponse> responses, Order order) {
        OrderDetailResponse detailResponse=new OrderDetailResponse();
        detailResponse.setItemResponse(responses);
        detailResponse.setId(order.getId());
        detailResponse.setSubtotal(order.getSubtotal());
        detailResponse.setTotal(order.getTotal());
        detailResponse.setCustomerName(order.getCustomerName());
        detailResponse.setCreatedDateTime(order.getCreatedAt());
        detailResponse.setPaymentMethod(order.getPaymentMode());
        detailResponse.setToken(order.getTokenNo());
        detailResponse.setDiscountAmount(order.getDiscountAmount());
        return detailResponse;
    }

    private OrderItemResponse preprareToSendResponse(OrderedItem orderedItem){
        OrderItemResponse response=new OrderItemResponse();
        response.setId(orderedItem.getId());
        response.setRate(orderedItem.getRate());
        response.setProductId(orderedItem.getProduct().getId());
        response.setTotalPrice(orderedItem.getPrice());
        response.setQuantity(orderedItem.getQuantity());
        return  response;
    }

    private Order validateOrder(Long id){
        Optional<Order> optionalOrder=orderRepository.getOrderByIdIs(id);
        return optionalOrder.orElseThrow(() -> new RestException("O001","Order not found"));
    }

    private TodayOrderList prepareToSendOrder(Order order){
        TodayOrderList todayOrderList=new TodayOrderList();
        todayOrderList.setId(order.getId());
        todayOrderList.setCustomer(order.getCustomerName());
        todayOrderList.setSubtotal(order.getSubtotal());
        todayOrderList.setToken(order.getTokenNo());
        todayOrderList.setAddedDateTime(order.getCreatedAt());
        todayOrderList.setTotal(order.getTotal());
        return  todayOrderList;

    }

    private Order prepareToSaveOrder(OrderSaveRequest request){
        Order order=new Order();
        order.setOrderDateTime(request.getOrderDateTime());
        order.setCreatedAt(LocalDateTime.now());
        order.setCreatedBy(contextHolderService.getContext().getUserName());
        order.setVoid(false);
        order.setTotal(request.getTotal());
        order.setCustomerName(request.getCustomerName());
        order.setSubtotal(request.getSubTotal());
        order.setPaymentMode(request.getPaymentMode());
        order.setDiscountAmount(request.getDiscountAmount());
        int token=createTokenNumber();
        System.out.print(token);
        order.setTokenNo(String.valueOf(token));
        order.setTokenValue(token);
        return  order;
    }

    private OrderedItem prepareToSaveOrderedItem(OrderItemRequest request){
        OrderedItem item=new OrderedItem();
        item.setAddedTime(LocalDateTime.now());
        item.setRate(request.getPrice());
        item.setPrice(request.getTotalPrice());
        item.setQuantity(request.getQuantity());
        return item;
    }

    private int createTokenNumber(){

        Optional<Integer> token=orderRepository.findTokenForToday(LocalDateTime.now());
        if(token.isPresent()){
            System.out.print(token.get()+1);
            return token.get()+1;
        }else {
            System.out.print("In 1");
            return 1;
        }

    }
}
