package org.grow.canteen.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.grow.canteen.constants.Days;
import org.grow.canteen.constants.Route;
import org.grow.canteen.dto.productScheduler.ProductSchedulerListResponse;
import org.grow.canteen.dto.productScheduler.ProductSchedulerRequest;
import org.grow.canteen.dto.Response;
import org.grow.canteen.dto.productScheduler.UpdateRequest;
import org.grow.canteen.services.ProductSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class ProductSchedulerController {

    private ProductSchedulerService productSchedulerService;

    @Autowired
    public ProductSchedulerController(ProductSchedulerService productSchedulerService) {
        this.productSchedulerService = productSchedulerService;
    }


    @PostMapping(Route.SCHEDULER)
    private Response scheduleProduct(@Valid @RequestBody ProductSchedulerRequest request){
        log.info("Schedule Product::{{}}",request);
        return  productSchedulerService.saveProductForScheduling(request);
    }

    @GetMapping(Route.SCHEDULER)
    private List<ProductSchedulerListResponse> getScheduleProduct(){
        log.info("Fetch All Schedule Product::");
        return  productSchedulerService.getAllScheduledProduct();
    }
    @GetMapping(Route.SCHEDULER_ACTIVATE)
    private List<ProductSchedulerListResponse> activate(@PathVariable("id") Long id){
        log.info("Activate Schedule Product::{}",id);
        return  productSchedulerService.activateScheduledProduct(id);
    }
    @GetMapping(Route.SCHEDULER_DEACTIVATE)
    private List<ProductSchedulerListResponse> deactivate(@PathVariable("id") Long id){
        log.info("Deactivate  Schedule Product::{}",id);
        return  productSchedulerService.deactivateScheduledProduct(id);
    }
    @GetMapping(Route.SCHEDULED_PRODUCT)
    private List<ProductSchedulerListResponse> getProductByDays(@PathVariable("day") String day){
        log.info("Deactivate  Schedule Product::{}",day);
        return  productSchedulerService.getProductAccordingToSchedule(day);
    }

    @GetMapping(Route.SCHEDULER_UPDATE)
    private List<ProductSchedulerListResponse> updateScheduler(@RequestBody @Valid UpdateRequest request){
        log.info("Deactivate  Schedule Product::{{}}",request);
        return  productSchedulerService.updateScheduler(request);
    }
    @GetMapping(Route.DAYS)
    private Days[] getAllDays(){
        log.info("Fetch all days::");
        return Days.values();
    }
}
