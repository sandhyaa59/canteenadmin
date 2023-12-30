package org.grow.canteen.dto.Category;


import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Setter
@NoArgsConstructor
public class CategoryListtResponse {
    public  Long id;
    public  String name;
   public boolean isActive;
  public   boolean isDeleted;
    public LocalDateTime modifiedBy;
    public  String description;
    public String createdBy;

}
