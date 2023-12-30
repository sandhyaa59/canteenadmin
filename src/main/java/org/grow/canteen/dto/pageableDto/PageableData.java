package org.grow.canteen.dto.pageableDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@NoArgsConstructor
public class PageableData<T> {
    private T data;
    private int totalPage;
    private long totalData;
    private int pageNumber;
    private boolean hasNext;

    public PageableData(T data, int totalPage, long totalData){
        this.data = data;
        this.totalPage = totalPage;
        this.totalData = totalData;
    }

    public PageableData(T data, int totalPage, long totalData, int pageNumber) {
        this.data = data;
        this.totalPage = totalPage;
        this.totalData = totalData;
        this.pageNumber = pageNumber;
        this.hasNext = totalPage > pageNumber;
    }

    public PageableData(T data, Page<?> page){
        this.data = data;
        this.totalPage = page.getTotalPages();
        this.totalData = page.getTotalElements();
        this.pageNumber = page.getNumber() + 1;
        this.hasNext = totalPage > pageNumber;
    }
}