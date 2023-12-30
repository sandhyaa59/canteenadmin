package org.grow.canteen.dto.pageableDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Page;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class PageableDataWithObject<T> extends PageableData<T>{
    private Map<String, Object> object;

    public PageableDataWithObject(T data, Page<?> page, Map<String, Object> object) {
        super(data, page);
        this.object = object;
    }    
    
}
