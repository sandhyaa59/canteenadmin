package org.grow.canteen.utils;


import org.grow.canteen.dto.pageableDto.Pagination;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PaginationUtil {
    public static Pageable performPagination(int page, int size){
        return PageRequest.of((page - 1), size);
    }

    public static Pageable performPagination(int page, int size, String sort, String sortParameter) {
        Pageable pageable;
//        System.out.println(sortParameter);
        if(sort.equalsIgnoreCase("descending")) {
            pageable = PageRequest.of((page - 1), size, Sort.by(sortParameter).descending());
        }
        else{
            pageable = PageRequest.of((page - 1), size, Sort.by(sortParameter).ascending());
        }
        return pageable;
    }
    public static Pageable performPagination(Pagination pagination){
        return PaginationUtil.performPagination(pagination.getPage(), pagination.getPageSize(), pagination.getSort(), pagination.getSortParameter());
    }
}
