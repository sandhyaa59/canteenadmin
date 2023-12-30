package org.grow.canteen.dto.pageableDto;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.Map;

@Data
public class PageableDataWithObject<T> extends PageableData<T>{
    private Map<String, Object> object;

    public PageableDataWithObject(T data, Page<?> page, Map<String, Object> object) {
        super(data, page);
        this.object = object;
    }    
    
}
