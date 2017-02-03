package com.scb.apipoc;

/**
 * Created by Krit on 12/19/2016.
 */

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.util.UriComponentsBuilder;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.AbstractPathProvider;
import springfox.documentation.spring.web.paths.Paths;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.*;

@RestController
@EnableAutoConfiguration
@EnableDiscoveryClient
@SpringBootApplication
@EnableCircuitBreaker
@EnableSwagger2
//Use tags property to specify name to be displayed, description is obsoleted but it is used to show description at API level
@Api(tags = "API1", value = "/rest/v1/poc", description = "Sample API for POC purpose")
@RequestMapping(value="/poc")
public class API1 {

    private static Logger log = LoggerFactory.getLogger(API1.class);

    @ApiOperation(value = "resource1", nickname = "resource1", notes = "Test API that returns simple string as output")
    @RequestMapping(value = "/resource1", method = RequestMethod.GET, produces = "application/json")
    public String available() {
        log.info("resource1 initiated");
        return "Simple resource1 response";
    }

    @ApiOperation(value = "resource2", nickname = "resource2", notes = "Test API that returns simple string containing IP address of the API provider instance as output")
    @RequestMapping(value = "/resource2", method = RequestMethod.GET, produces = "application/json")
    public String checkedOut() {
        String ipAddr = "";
        NetworkInterface netInt;

        try {
            ipAddr = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        log.info("resource2 initiated");
        return "Simple resource2 response from: " + ipAddr;
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
                .paths(regex("/poc.*"))
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

    class BasePathAwareRelativePathProvider extends AbstractPathProvider {
        private String basePath;

        public BasePathAwareRelativePathProvider(String basePath) {
            this.basePath = basePath;
        }

        @Override
        protected String applicationPath() {
            return basePath;
        }

        @Override
        protected String getDocumentationPath() {
            return "/";
        }

        @Override
        public String getOperationPath(String operationPath) {
            UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath("/");
            return Paths.removeAdjacentForwardSlashes(
                    uriComponentsBuilder.path(operationPath.replaceFirst(basePath, "")).build().toString());
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(API1.class, args);
    }
}
