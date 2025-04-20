package com.roldaice.myboardgames.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ConfigCheckListener implements ApplicationListener<ApplicationReadyEvent> {

    private final ConfigurableEnvironment configurableEnvironment;
    private final Environment env;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        System.out.println("🔍 SPRING CONFIG PROPERTY DUMP");
        configurableEnvironment.getPropertySources().forEach(source -> {
            System.out.println("📦 Source: " + source.getName());
            if (source instanceof EnumerablePropertySource<?> eps) {
                for (String name : eps.getPropertyNames()) {
                    System.out.println(" → " + name + " = " + env.getProperty(name));
                }
            }
        });

        String loadedProp = env.getProperty("spring.application.name");
        if (loadedProp == null) {
            throw new IllegalStateException("application.yml was not loaded or is invalid, please check file structure and values");
        }
    }
}