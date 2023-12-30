package org.grow.canteen.services.impl;

import jakarta.transaction.Transactional;
import org.grow.canteen.commons.context.ContextHolderService;
import org.grow.canteen.commons.exceptions.RestException;
import org.grow.canteen.constants.Days;
import org.grow.canteen.dto.productScheduler.ProductSchedulerListResponse;
import org.grow.canteen.dto.productScheduler.ProductSchedulerRequest;
import org.grow.canteen.dto.Response;
import org.grow.canteen.dto.productScheduler.UpdateRequest;
import org.grow.canteen.entities.Product;
import org.grow.canteen.entities.ProductScheduler;
import org.grow.canteen.repository.ProductSchedulerRepository;
import org.grow.canteen.services.AdminService;
import org.grow.canteen.services.ProductSchedulerService;
import org.grow.canteen.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductSchedulerServiceImpl implements ProductSchedulerService {

    private AdminService adminService;
    private ContextHolderService contextHolderService;
    private ProductService productService;
    private ProductSchedulerRepository productSchedulerRepository;

    @Autowired
    public ProductSchedulerServiceImpl(AdminService adminService,ProductSchedulerRepository productSchedulerRepository,
                                       ContextHolderService contextHolderService, ProductService productService) {
        this.adminService = adminService;
        this.contextHolderService = contextHolderService;
        this.productService = productService;
        this.productSchedulerRepository=productSchedulerRepository;
    }

    @Transactional
    @Override
    public Response saveProductForScheduling(ProductSchedulerRequest request) {
        adminService.validateAdmin(contextHolderService.getContext().getUserId());
        request.getProductId().forEach(aLong -> {
            Product product= productService.validateProductById(aLong);
            ProductScheduler scheduler=new ProductScheduler();
            scheduler.setProduct(product);
            scheduler.setDays(Days.getDaysValue(request.getDays()));
            scheduler.setCreatedBy(contextHolderService.getContext().getUserName());
            scheduler.setModifiedOn(LocalDateTime.now());
            scheduler.setCreatedOn(LocalDateTime.now());
            scheduler.setActive(true);
            scheduler.setDeleted(false);
            ProductScheduler response= productSchedulerRepository.save(scheduler);
        });

        return Response.builder().id(1L).build();
    }

    @Override
    public List<ProductSchedulerListResponse> getAllScheduledProduct() {
        adminService.validateAdmin(contextHolderService.getContext().getUserId());
        return  showAllScheduledProduct();
    }

    private ProductScheduler validateProductSchedulerById(Long id){
        Optional<ProductScheduler> optionalProductScheduler=productSchedulerRepository.validateByIdForActive(id);
        return optionalProductScheduler.orElseThrow(() -> new RestException("PS001","Product Scheduled not found"));
    }

    @Override
    public List<ProductSchedulerListResponse> activateScheduledProduct(Long id) {
        adminService.validateAdmin(contextHolderService.getContext().getUserId());
        ProductScheduler productScheduler=validateProductSchedulerById(id);
        productScheduler.setActive(true);
        productScheduler.setModifiedOn(LocalDateTime.now());
        productSchedulerRepository.save(productScheduler);
        return getAllScheduledProduct();
    }

    @Override
    public List<ProductSchedulerListResponse> deactivateScheduledProduct(Long id) {
        adminService.validateAdmin(contextHolderService.getContext().getUserId());
        ProductScheduler productScheduler=validateProductSchedulerById(id);
        productScheduler.setActive(false);
        productScheduler.setModifiedOn(LocalDateTime.now());
        productSchedulerRepository.save(productScheduler);
        return getAllScheduledProduct();
    }

    @Override
    public List<ProductSchedulerListResponse> getProductAccordingToSchedule(String day) {
        adminService.validateAdmin(contextHolderService.getContext().getUserId());
      Days days=  Days.getDaysValue(day);
List<ProductScheduler> productSchedulerList=productSchedulerRepository.getScheduledProductByDays(days);
        List<ProductSchedulerListResponse> listResponses=new ArrayList<>();
        productSchedulerList.forEach(productScheduler -> {
            ProductSchedulerListResponse productScheduler1=prepareForResponse(productScheduler);
            listResponses.add(productScheduler1);
        });
        return  listResponses;
    }

    @Override
    public List<ProductSchedulerListResponse> updateScheduler(UpdateRequest request) {
        adminService.validateAdmin(contextHolderService.getContext().getUserId());
        ProductScheduler productScheduler=validateProductSchedulerById(request.getId());
       Product product= productService.validateProductById(request.getProductId());
       productScheduler.setModifiedOn(LocalDateTime.now());
       productScheduler.setProduct(product);
       productScheduler.setDays(Days.getDaysValue(request.getDays()));
       productSchedulerRepository.save(productScheduler);
       return showAllScheduledProduct();

    }

    private List<ProductSchedulerListResponse> showAllScheduledProduct(){
        List<ProductSchedulerListResponse> listResponses=new ArrayList<>();
        List<ProductScheduler> productSchedulerList=productSchedulerRepository.getAllScheduledProduct();
        productSchedulerList.forEach(productScheduler -> {
            ProductSchedulerListResponse productScheduler1=prepareForResponse(productScheduler);
            listResponses.add(productScheduler1);
        });
        return listResponses;
    }

    private ProductSchedulerListResponse prepareForResponse(ProductScheduler productScheduler){
        ProductSchedulerListResponse response=new ProductSchedulerListResponse();
        response.setId(productScheduler.getId());
        response.setProductName(productScheduler.getProduct().getTitle());
        response.setProductId(productScheduler.getProduct().getId());
        response.setDescription(productScheduler.getProduct().getDescription());
        response.setPrice( productScheduler.getProduct().getPrice());
        response.setAddedTime(productScheduler.getCreatedOn());
        response.setActive(productScheduler.isActive());
        response.setModifiedOn(productScheduler.getModifiedOn());
        response.setAddedBy(productScheduler.getCreatedBy());
        response.setDay(productScheduler.getDays().name());
        return response;
    }
}
