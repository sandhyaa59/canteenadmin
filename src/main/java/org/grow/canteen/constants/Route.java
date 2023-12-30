package org.grow.canteen.constants;

public class Route {

    public static final String BASE_URL="/v1/api";
    public static final String ADMIN=BASE_URL+"/admin";

    public static final String USER=BASE_URL+"/user";
    public static final String SAVE_ADMIN=ADMIN+"/save";
    public static final String ADMIN_LOGIN=ADMIN+"/login";
    public static final String LIST_ADMIN=ADMIN+"/list-admin";
    public static final String ACTIVATE_ADMIN=ADMIN+"/activate/{id}";
    public static final String DEACTIVATE_ADMIN=ADMIN+"/deactivate/{id}";
    public static final String ADMIN_DETAIL=ADMIN+"/detail/{id}";
    public static final String UPDATE_ADMIN=ADMIN+"/update";
    public static final String DELETE_ADMIN=ADMIN+"/delete/{id}";
    public static final String SEARCH_ADMIN=ADMIN+"/search/{keyword}";

    //Category
    public static final String CATEGORY=ADMIN+"/category";
    public static final String UPDATE_CATEGORY=CATEGORY+"/update_category";
    public static final String Active_CATEGORY=CATEGORY+"/active_category/{id}";

    public static final String DEACTIVATE_CATEGORY=CATEGORY+"/deactivate/{id}";
    public static final String DELETE_CATEGORY=CATEGORY+"/delete/{id}";


    //Product
    public static final String PRODUCT=ADMIN+"/product";
    public static final String PRODUCT_LIST=PRODUCT+"/list";
    public static final String ACTIVATE_PRODUCT=PRODUCT+"/activate/{id}";
    public static final String DEACTIVATE_PRODUCT=PRODUCT+"/deactivate/{id}";

    public static final String DELETE_PRODUCT=PRODUCT+"/delete/{id}";
    public static final String UPDATE_PRODUCT=PRODUCT+"/update";

    public static final String SEARCH_PRODUCT=PRODUCT+"/search/{keyword}";
    public static final String CATEGORISED_PRODUCT=PRODUCT+"/categorised";

    //Organization
    public static final String Organization=ADMIN+"/organization";

    //Scheduler
    public static final String SCHEDULER=ADMIN+"/schedule";
    public static final String SCHEDULER_ACTIVATE=SCHEDULER+"/activate/{id}";
    public static final String SCHEDULER_DEACTIVATE=SCHEDULER+"/deactivate/{id}";
    public static final String SCHEDULED_PRODUCT=SCHEDULER+"/product/{day}";
    public static final String SCHEDULER_UPDATE=SCHEDULER+"/update";
    public static final String DAYS=SCHEDULER+"/days";

    //Order
    public static final String ORDER=ADMIN+"/order";
    public static final String TODAY=ADMIN+"/today-order";
    public static final String ORDER_DETAIL=ORDER+"/details/{id}";


    //Customer
    public static final String CUSTOMER=ADMIN+"/customer";
    public static final String SAVE_CUSTOMER=CUSTOMER+"/save";

    public static final String LIST_CUSTOMER=CUSTOMER+"/list";
    public static final String ACTIVATE_CUSTOMER=CUSTOMER+"/activate/{id}";
    public static final String DEACTIVATE_CUSTOMER=CUSTOMER+"/deactivate/{id}";

    public static final String UPDATE_CUSTOMER=CUSTOMER+"/update";
    public static final String DELETE_CUSTOMER=CUSTOMER+"/delete/{id}";
    public static final String SEARCH_CUSTOMER=CUSTOMER+"/search/{keyword}";

    //Report
    public static final String REPORT=ADMIN+"/reports";

}
