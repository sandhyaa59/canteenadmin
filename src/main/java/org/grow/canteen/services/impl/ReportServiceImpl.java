package org.grow.canteen.services.impl;

import org.grow.canteen.commons.context.ContextHolderService;
import org.grow.canteen.dto.report.ReportRequest;
import org.grow.canteen.dto.report.ReportResponse;
import org.grow.canteen.repository.OrderRepository;
import org.grow.canteen.repository.ProductRepository;
import org.grow.canteen.services.AdminService;
import org.grow.canteen.services.ReportServices;
import org.grow.canteen.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ReportServiceImpl implements ReportServices {

    private AdminService adminService;
    private ContextHolderService contextHolderService;

    private OrderRepository orderRepository;
    private ProductRepository productRepository;

    @Autowired
    public ReportServiceImpl(AdminService adminService, ContextHolderService contextHolderService, OrderRepository orderRepository, ProductRepository productRepository) {
        this.adminService = adminService;
        this.contextHolderService = contextHolderService;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    private void getTotalSaleBetweenDays(){

    }

    @Override
    public ReportResponse getStatisticsReports(ReportRequest request) {
        adminService.validateAdmin(contextHolderService.getContext().getUserId());
        Map<String,Object> report=orderRepository.getOrderStatistics(Helper.parseDateTime(request.getStartDate()),Helper.parseDateTime (request.getEndDateTime()));
//        System.out.print(request.getStartDate());
        ReportResponse response=new ReportResponse();
        response.setTotalDiscount((double)report.get("totalDiscountAmount"));
        response.setTotalOrder((long)report.get("totalOrders"));
        response.setTotalSubTotal((double)report.get("totalSubTotal"));
        response.setTotalGrandTotal((double)report.get("totalSaleAmount"));
        return response;
    }
}
