package com.scb.apipoc;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.net.*;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Created by Krit on 1/6/2017.
 */

@RestController
@EnableDiscoveryClient
@SpringBootApplication
@EnableCircuitBreaker
@EnableFeignClients
@Api(tags = "POCAPIs", value = "/rest/v1/poc", description = "Sample APIs for POC purpose")
@RequestMapping(value="/rest/v1/poc")
public class AggregateAPI {

    private static Logger log = LoggerFactory.getLogger(AggregateAPI.class);

    @Autowired
    POCAPIsResource client;

    @ApiOperation(value = "resource3", nickname = "resource3", notes = "Test API that returns simple string as output")
    @RequestMapping(value = "/resource3")
    public String available() {
        log.info("resource3... initiated");
        return "Simple resource3 response + (" + client.resource2() + ")";
    }

    @ApiOperation(value = "resource4", nickname = "resource4", notes = "Test API that returns simple string containing IP address of the API provider instance as output")
    @RequestMapping(value = "/resource4")
    public String resource4() {
        String ipAddr = "";
        NetworkInterface netInt;

        try {
            ipAddr = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        log.info("resource4... initiated");
        return "Simple resource4 response from: " + ipAddr;
    }
    // Use Feign for calling other microservices
    @FeignClient("http://API1")
    public interface POCAPIsResource {
        @RequestMapping(value = "/rest/v1/poc/resource2", method = RequestMethod.GET)
        String resource2();
    }

    @Bean
    public Docket api() {
        Docket thisDocket;
        thisDocket = new Docket(DocumentationType.SWAGGER_2)
                // Optional
                .groupName("Test Swagger from Springboot")
                .apiInfo(apiInfo())
//                .pathProvider(new BasePathAwareRelativePathProvider("/rest"))
                //
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(regex("/rest/v1/poc2.*"))
                // Use .any will query all every APIs under this class
                //.paths(PathSelectors.any())
                .build();
        return thisDocket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring REST Sample with Swagger and Springboot")
                .contact(new Contact("Krit Sudduen","", "sudduenk@gmail.com"))
                .description("This is Spring REST Sample with Swagger and Springboot")
                .version("1.0")
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(AggregateAPI.class, args);
    }
}