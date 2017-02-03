## Projects
- api1: Spring boot REST API for resource#1 & resource#2
- aggregateapi: Spring boot REST API for resource#3 & resource#4. The resource#3 use Feign to call resource#2 and aggregate the response
- gatewayapp: The Zuul server
- poceureka: The Eureka server
- hystrixdashboard: The Hystrix Dashboard server
- turbinehost: The Turbine server (still unable to get it working)
- springconfigserver: The centralized configuration server based on Spring
- springconfigrepo: Repo for storing configurations (used by springconfigserver)

## Test URLs (localhost)
* http://localhost:1111/  (to view Eureka dashboard)
* http://localhost:2222/hystrix  (to view Hystrix dashboard)
* GET: http://localhost:8040/rest/v1/poc/resource1  (to call resource1 API directly)
* GET: http://localhost:8040/rest/v1/poc/resource2  (to call resource2 API directly)
* GET: http://localhost:8057/rest/v1/poc/resource3  (to call resource3 API directly) - This is aggregated API that use Feign to subsequently call resource2 API.
* GET: http://localhost:8057/rest/v1/poc/resource4  (to call resource4 API directly)
* GET: http://localhost:8090/API1/rest/v1/poc/resource1  (to call resource1 API via Zuul)
* GET: http://localhost:8090/API1/rest/v1/poc/resource2  (to call resource2 API via Zuul)
* GET: http://localhost:8090/AGGREGATEAPI/rest/v1/poc/resource3  (to call resource3 API via Zuul)
* GET: http://localhost:8090/AGGREGATEAPI/rest/v1/poc/resource4  (to call resource4 API via Zuul)