package org.grow.canteen.filters;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.grow.canteen.commons.ErrorResponse;
import org.grow.canteen.commons.context.ContextHolderService;
import org.grow.canteen.commons.exceptions.RestException;
import org.grow.canteen.constants.Route;
import org.grow.canteen.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class AuthRequestFilter implements Filter {

    private final JwtTokenUtil jwtTokenUtil;
    private final ContextHolderService contextHolderService;

    @Autowired
    public AuthRequestFilter(JwtTokenUtil jwtTokenUtil, ContextHolderService contextHolderService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.contextHolderService = contextHolderService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
log.info("Inside JWT filter");
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        HttpServletResponse response= (HttpServletResponse) servletResponse;
        final String requestTokenHeader=request.getHeader("Authorization");
        String url=request.getRequestURI();
        if(!isByPassUrl(url)){
            try{
                if(requestTokenHeader !=null &&requestTokenHeader.startsWith("Bearer ")){
                   String jwtToken=requestTokenHeader.substring(7);
                   String userName=jwtTokenUtil.getUsernameFromToken(jwtToken);
//                   String userType=jwtTokenUtil.getByKey(jwtToken,"userType");
                   jwtTokenUtil.validateToken(jwtToken);
                   contextHolderService.setContext(userName, "ADMIN");
                }else{
                    log.error("JWT Token does not begin with Bearer String");
                    throw  new RestException("A001","Invalid access Token");
                }
            }catch (Exception ex){
                log.error("Error while validating token with message {}",ex.getMessage());
                handleException(response);
                return;
            }
        }
        filterChain.doFilter(request,response);
    }

    private void handleException(HttpServletResponse httpServletResponse) throws IOException{
        ErrorResponse errorResponse=new ErrorResponse("A002","Authorization failed");
        ObjectMapper mapper=new ObjectMapper();
        PrintWriter out=httpServletResponse.getWriter();
        out.print(mapper.writeValueAsString(errorResponse));
        out.flush();
    }

    private boolean isByPassUrl(String url){
        List<String> byPassUrl= Arrays.asList(Route.ADMIN_LOGIN);
        return byPassUrl.stream().anyMatch(url::equalsIgnoreCase);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
