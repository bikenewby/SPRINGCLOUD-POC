package com.scb.apipoc.filters.pre;

/**
 * Created by Krit on 12/19/2016.
 */
import javax.servlet.http.HttpServletRequest;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.ZuulFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimplePreFilter extends ZuulFilter {
    private static Logger log = LoggerFactory.getLogger(SimplePreFilter.class);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        log.info(String.format("(BEFORE) %s request is sent to %s", request.getMethod(), request.getRequestURL().toString()));

        return null;
    }
}
