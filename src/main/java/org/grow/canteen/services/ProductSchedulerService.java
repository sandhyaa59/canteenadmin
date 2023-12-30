package org.grow.canteen.services;

import org.grow.canteen.dto.productScheduler.ProductSchedulerListResponse;
import org.grow.canteen.dto.productScheduler.ProductSchedulerRequest;
import org.grow.canteen.dto.Response;
import org.grow.canteen.dto.productScheduler.UpdateRequest;

import java.util.List;

public interface ProductSchedulerService {

    Response saveProductForScheduling(ProductSchedulerRequest request);
    List<ProductSchedulerListResponse> getAllScheduledProduct();
    List<ProductSchedulerListResponse> activateScheduledProduct(Long id);
    List<ProductSchedulerListResponse> deactivateScheduledProduct(Long id);
    List<ProductSchedulerListResponse> getProductAccordingToSchedule(String day);

    List<ProductSchedulerListResponse> updateScheduler( UpdateRequest request);

}
