package org.grow.canteen.dto.Admin;

import lombok.Builder;
import lombok.Setter;

@Builder
@Setter
public class LoginResponse {
    public Long id;
    public String token;
}
