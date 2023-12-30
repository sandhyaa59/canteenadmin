package org.grow.canteen.dto.pageableDto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Pagination {
    private int page =1;
    private int pageSize =20;
    private String sort ="descending";
    private String sortParameter = "id";

    public Pagination(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }
}
