package com.javawiz.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleRestController {
	
	private static final Logger log = LoggerFactory.getLogger(SampleRestController.class);

    @Autowired
    Environment environment;
 
    @GetMapping("/")
    public String health() {
        return "I am Ok";
    }
 
    @GetMapping("/backend")
    public String backend() {
    	log.debug("Inside Rest Controller::backend...");
 
        String serverPort = environment.getProperty("local.server.port");
        
        log.debug("Port : {}", serverPort);
 
        return "Hello form Backend!!! " + " Host : localhost " + " :: Port : " + serverPort;
    }
}