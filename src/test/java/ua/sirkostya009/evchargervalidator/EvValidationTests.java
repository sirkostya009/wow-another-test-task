package ua.sirkostya009.evchargervalidator;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ua.sirkostya009.evchargervalidator.station.Connector;
import ua.sirkostya009.evchargervalidator.station.Station;
import ua.sirkostya009.evchargervalidator.station.StationType;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class EvValidationTests {
    @Resource
    private MockMvc mockMvc;

    @Resource
    ObjectMapper mapper;

    private final TypeReference<Map<String, Object>> mapTypeReference = new TypeReference<>() {};

    @Test
    public void testValid() throws Exception {
        var station = new Station(
                1L,
                "Station1",
                "Description",
                "Address info",
                "123.0, 321.9",
                false,
                List.of(new Connector(1L, StationType.Type1, 200L))
        );

        var response = mockMvc.perform(post("/api/validate")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(station)))
                .andReturn()
                .getResponse();

        var map = mapper.readValue(response.getContentAsString(), mapTypeReference);

        assertThat(map).contains(entry("valid", true));
    }

    @Test
    public void testPublicWithoutRequiredFields() throws Exception {
        var station = new Station(
                1L,
                null,
                null,
                null,
                null,
                false,
                List.of(new Connector(1L, StationType.Type1, 200L))
        );

        var response = mockMvc.perform(post("/api/validate")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(station)))
                .andReturn()
                .getResponse();

        var map = mapper.readValue(response.getContentAsString(), mapTypeReference);

        assertThat(map).contains(entry("valid", false));
    }

    @Test
    public void testAllNull() throws Exception {
        var station = new Station(
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

        var response = mockMvc.perform(post("/api/validate")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(station)))
                .andReturn()
                .getResponse();

        var map = mapper.readValue(response.getContentAsString(), mapTypeReference);

        assertThat(map).contains(entry("valid", false));
    }
}
