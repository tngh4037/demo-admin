package com.example.demo.admin.global.config.filter;

import jakarta.servlet.*;
import org.slf4j.MDC;

import java.io.IOException;
import java.util.UUID;

public class MDCLogFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        try {
            MDC.put("transId", getTransId());
            chain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }

    private String getTransId() {
        return "[" + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 12) + "]";
    }
}
