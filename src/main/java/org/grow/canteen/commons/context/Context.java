package org.grow.canteen.commons.context;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Context {

    private Long userId;
    private String userName;
    private String userType;


}
