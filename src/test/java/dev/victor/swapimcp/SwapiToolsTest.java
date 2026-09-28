package dev.victor.swapimcp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

class SwapiToolsTest {

    private final SwapiClient swapiClient = mock(SwapiClient.class);
    private final SwapiTools tools = new SwapiTools(swapiClient);

    @Test
    void searchMatchesNamesAndTitlesWithoutCaseSensitivity() {
        var resources = List.of(
                Map.<String, Object>of("name", "Luke Skywalker"),
                Map.<String, Object>of("title", "A New Hope"),
                Map.<String, Object>of("name", "Leia Organa"));
        when(swapiClient.listResources("people")).thenReturn(resources);

        var matches = tools.searchResources("people", "lUkE");

        assertThat(matches).containsExactly(resources.get(0));
        verify(swapiClient).listResources("people");
    }

    @Test
    void searchMatchesFilmTitles() {
        var film = Map.<String, Object>of("title", "A New Hope");
        when(swapiClient.listResources("films")).thenReturn(List.of(film));

        assertThat(tools.searchResources("films", "new hope")).containsExactly(film);
    }
}
