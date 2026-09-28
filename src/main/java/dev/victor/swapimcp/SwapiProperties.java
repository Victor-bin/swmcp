package dev.victor.swapimcp;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "swapi")
public record SwapiProperties(String baseUrl) {
}
