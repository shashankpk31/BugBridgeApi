package com.shashankpk.BugBridgeApi.Config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RequestLoggingFilter implements Filter{
	private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);
	
	private String getRequestBody(ContentCachingRequestWrapper request) {
        byte[] content = request.getContentAsByteArray();
        return content.length > 0 ? new String(content) : "-";
    }

    private String getResponseBody(ContentCachingResponseWrapper response) {
        byte[] content = response.getContentAsByteArray();
        return content.length > 0 ? new String(content) : "-";
    }
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest) request;
		HttpServletResponse res=(HttpServletResponse) response;
		
		ContentCachingRequestWrapper wrapperReq=new ContentCachingRequestWrapper(req);
		ContentCachingResponseWrapper wrapperRes=new ContentCachingResponseWrapper(res);
		
		long startTime=System.currentTimeMillis();
		
		String reqBody=getRequestBody(wrapperReq);
		logger.info("REQUEST: {} {} | Headers: {} | Body: {}",
                req.getMethod(), req.getRequestURI(), req.getHeader("User-Agent"), reqBody);
		
		chain.doFilter(wrapperReq, wrapperRes);

        long duration = System.currentTimeMillis() - startTime;

        String responseBody = getResponseBody(wrapperRes);
        
        logger.info("RESPONSE: {} | Status: {} | Duration: {}ms | Body: {}",
                req.getRequestURI(), res.getStatus(), duration, responseBody);

        wrapperRes.copyBodyToResponse(); 
	}

}
