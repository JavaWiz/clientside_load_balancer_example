package com.javawiz.ribbonclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;
 
@Configuration
public class RibbonConfiguration {
 
    @Bean
    public IPing ribbonPing() {
        return new PingUrl();
    }
 
    @Bean
    public IRule ribbonRule() {
        return new AvailabilityFilteringRule();
    }
}