package com.scb.apipoc;

/**
 * Created by Krit on 12/19/2016.
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import com.scb.apipoc.filters.pre.SimplePreFilter;
import com.scb.apipoc.filters.post.SimplePostFilter;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@EnableZuulProxy
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public SimplePreFilter simplePreFilter() {
        return new SimplePreFilter();
    }

    @Bean
    public SimplePostFilter simplePostFilter() {
        return new SimplePostFilter();
    }
}
