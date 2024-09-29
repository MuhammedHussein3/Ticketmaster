package com.ticketmaster.event.config;
import org.apache.catalina.core.StandardThreadExecutor;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class TomcatVirtualThreadConfig {

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> customizer() {
        return factory -> factory.addConnectorCustomizers(connector -> {
            Executor virtualThreadExecutor = Executors.newVirtualThreadPerTaskExecutor();
            connector.getProtocolHandler().setExecutor((java.util.concurrent.Executor) virtualThreadExecutor);
        });
    }
}

