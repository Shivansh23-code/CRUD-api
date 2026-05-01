package com.review.crud.filter;


import com.review.crud.config.ProfilingConfig;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class RequestProfilingFilter implements Filter {

    private final ProfilingConfig config;

    public RequestProfilingFilter(ProfilingConfig config){
        this.config = config;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                    throws IOException, ServletException {

        if(!config.isEnabled()){
            chain.doFilter(request, response);
            return;
        }

        long start = System.currentTimeMillis();
        chain.doFilter(request, response);
        long time = System.currentTimeMillis()- start;
        HttpServletRequest req = (HttpServletRequest) request;

        if("DEBUG".equalsIgnoreCase(config.getLoglevel())){
            System.out.println("[DEV API] "
            + req.getMethod() + " "
            + req.getRequestURI()
            + " -> " + time + "ms");
        }
        else{
            if(time > 1000){
                System.out.println("[PROD ALERT] Slow API: "
                + req.getRequestURI()
                + " -> " + time + "ms");
            }else{
                System.out.println("[PROD API] "
                + req.getMethod()
                + " " + req.getRequestURI()
                + " -> " + time + "ms");
            }
        }
    }
}
