# Netflix ribbon example

### Technology stack

* Java, Eclipse, Maven as Development Environment
* Spring-boot and Cloud as application framework
* Eureka as Service registry Server
* Ribbon as Client Side Load balancer

We will create the following components and see how the whole eco system coordinates in distributed environment.

* Two microservices using Spring boot. One needs to invoke another as per business requirement.
* Eureka service registry server
* Ribbon in the invoking microservice to call the other service in load balanced fashion WITH service discovery.
* Invoking service in load balanced manner WITHOUT service discovery.

### Create two microservices

We will create a simple microservice using Spring boot and will expose a simple REST end point. Create one simple spring boot project named rest-service and expose one rest service to test.
And create another service called ribbon-client which will consume the rest service through load balancer.

### Ribbon configuration
In the ribbon-client application class, add two annotations @RibbonClient and @EnableDiscoveryClient to enable ribbon and Eureka client for service registry.

```
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@SpringBootApplication
@EnableEurekaClient
@RibbonClient
public class RibbonClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(RibbonClientApplication.class, args);
	}

}
```

Now we need to create one more configuration class for ribbon to mention the load balancing algorithm and health check. We will now use the default once provided by Ribbon, but in this class we can very well override those and add ours custom logic.

```
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;
 
public class RibbonConfiguration {
 
    @Autowired
    IClientConfig config;
 
    @Bean
    public IPing ribbonPing(IClientConfig config) {
        return new PingUrl();
    }
 
    @Bean
    public IRule ribbonRule(IClientConfig config) {
        return new AvailabilityFilteringRule();
    }
}
```

### Test the application

We will start the services one by one.

eureka-discovery-server first, then the back-end microservice and finally the frontend micro service.

We will start 3 instances of backend microservice, we will use 'java -jar -Dserver.port=XXXX target/YYYYY.jar' command with different port.

### Check if client side load balancing is working

In the frontend microservice, we are calling the backend microservice using RestTemplate. RestTemplate is enabled as client side load balancer using @LoadBalanced annotation.

Now go to browser and open the client microservice rest endpoint http://localhost:8888/client/frontend and see that response is coming from any one of the backend instance.

To understand this backend server is returning it’s running port and we are displaying that in client microservice response as well. Try refreshing this url couple of times and notice that the port of backend server keeps changing, that means client side load balancing is working. Now try to add more instance of backend server and check that is also registered in eureka server and eventually considered in ribbon, as once that will be registered in eureka and ribbon automatically ribbon will send request to the new instances as well.

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Client-side load-balancing with Ribbon and Spring Cloud](https://spring.io/guides/gs/client-side-load-balancing/)
