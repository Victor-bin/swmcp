package dev.victor.swapimcp;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Component;

@Component
public class SwapiTools {

    private final SwapiClient swapiClient;

    public SwapiTools(SwapiClient swapiClient) {
        this.swapiClient = swapiClient;
    }

        @McpTool(name = "swapi_list_categories", description = "Lista las categorías disponibles en SWAPI.",
            annotations = @McpTool.McpAnnotations(
                readOnlyHint = true, destructiveHint = false, idempotentHint = true, openWorldHint = true))
    public Map<String, String> listCategories() {
        return swapiClient.listCategories();
    }

        @McpTool(name = "swapi_list_resources", description = "Lista todos los registros de una categoría SWAPI.",
            annotations = @McpTool.McpAnnotations(
                readOnlyHint = true, destructiveHint = false, idempotentHint = true, openWorldHint = true))
    public List<Map<String, Object>> listResources(
            @McpToolParam(description = "Categoría: films, people, planets, species, vehicles o starships", required = true)
            String category) {
        return swapiClient.listResources(category);
    }

        @McpTool(name = "swapi_get_resource", description = "Obtiene un registro SWAPI por categoría e ID.",
            annotations = @McpTool.McpAnnotations(
                readOnlyHint = true, destructiveHint = false, idempotentHint = true, openWorldHint = true))
    public Map<String, Object> getResource(
            @McpToolParam(description = "Categoría: films, people, planets, species, vehicles o starships", required = true)
            String category,
            @McpToolParam(description = "ID numérico positivo del registro", required = true)
            String id) {
        return swapiClient.getResource(category, id);
    }

        @McpTool(name = "swapi_search_resources", description = "Busca por nombre o título dentro de una categoría SWAPI.",
            annotations = @McpTool.McpAnnotations(
                readOnlyHint = true, destructiveHint = false, idempotentHint = true, openWorldHint = true))
    public List<Map<String, Object>> searchResources(
            @McpToolParam(description = "Categoría: films, people, planets, species, vehicles o starships", required = true)
            String category,
            @McpToolParam(description = "Texto que debe aparecer en el nombre o título", required = true)
            String query) {
        if (query == null || query.isBlank()) {
            throw new IllegalArgumentException("La búsqueda no puede estar vacía.");
        }
        String normalizedQuery = query.toLowerCase(Locale.ROOT);
        return swapiClient.listResources(category).stream()
                .filter(resource -> Stream.of(resource.get("name"), resource.get("title"))
                        .filter(String.class::isInstance)
                        .map(String.class::cast)
                        .anyMatch(value -> value.toLowerCase(Locale.ROOT).contains(normalizedQuery)))
                .toList();
    }
}
