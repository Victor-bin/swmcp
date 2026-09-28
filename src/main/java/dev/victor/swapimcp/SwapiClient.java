package dev.victor.swapimcp;

import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class SwapiClient {

    private static final Set<String> CATEGORIES = Set.of(
            "films", "people", "planets", "species", "vehicles", "starships");
    private static final ParameterizedTypeReference<Map<String, String>> CATEGORY_MAP =
            new ParameterizedTypeReference<>() { };
    private static final ParameterizedTypeReference<List<Map<String, Object>>> RESOURCE_LIST =
            new ParameterizedTypeReference<>() { };
    private static final ParameterizedTypeReference<Map<String, Object>> RESOURCE_MAP =
            new ParameterizedTypeReference<>() { };

    private final RestClient restClient;

    public SwapiClient(SwapiProperties properties) {
        var httpClient = java.net.http.HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();
        var requestFactory = new JdkClientHttpRequestFactory(httpClient);
        requestFactory.setReadTimeout(Duration.ofSeconds(10));
        this.restClient = RestClient.builder()
                .baseUrl(properties.baseUrl())
                .requestFactory(requestFactory)
                .build();
    }

    public Map<String, String> listCategories() {
        return restClient.get()
                .uri("/api/")
                .retrieve()
                .body(CATEGORY_MAP);
    }

    public List<Map<String, Object>> listResources(String category) {
        return restClient.get()
                .uri("/api/{category}", validateCategory(category))
                .retrieve()
                .body(RESOURCE_LIST);
    }

    public Map<String, Object> getResource(String category, String id) {
        String validCategory = validateCategory(category);
        if (id == null || !id.matches("[1-9][0-9]*")) {
            throw new IllegalArgumentException("El ID debe ser un entero positivo.");
        }
        return restClient.get()
                .uri("/api/{category}/{id}", validCategory, id)
                .retrieve()
                .body(RESOURCE_MAP);
    }

    private String validateCategory(String category) {
        String normalized = category == null ? "" : category.toLowerCase(Locale.ROOT);
        if (!CATEGORIES.contains(normalized)) {
            throw new IllegalArgumentException("Categoría no válida. Usa: " + String.join(", ", CATEGORIES));
        }
        return normalized;
    }
}
