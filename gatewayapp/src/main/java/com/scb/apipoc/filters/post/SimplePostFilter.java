package com.scb.apipoc.filters.post;

/**
 * Created by Krit on 12/19/2016.
 */
import javax.servlet.http.HttpServletRequest;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.ZuulFilter;
import com.netflix.client.IResponse;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimplePostFilter extends ZuulFilter {
    private static Logger log = LoggerFactory.getLogger(SimplePostFilter.class);

    @Override
    public String filterType() {
        return "post";
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
        //RequestContext ctx = RequestContext.getCurrentContext();
        //HttpServletRequest request = ctx.getRequest();

        //log.info(String.format("(AFTER) %s request is sent to %s", request.getMethod(), request.getRequestURL().toString()));

        RequestContext ctx = RequestContext.getCurrentContext();
        URI ribbonResponseURI = ((IResponse) ctx.get("ribbonResponse")).getRequestedURI();
        log.info("[POST filter] Incoming URI: " + ctx.getRequest().getRequestURL().toString());
        log.info("[POST filter] Outgoing URI: " + ribbonResponseURI.toString());
        return null;
    }
}
