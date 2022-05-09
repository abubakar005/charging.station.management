package electric.vehicle.charging.station.management.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import electric.vehicle.charging.station.management.dto.NewStationRequestDto;
import electric.vehicle.charging.station.management.model.Station;
import electric.vehicle.charging.station.management.repository.StationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private StationRepository stationRepository;

    static Long newStationId;

    @Test
    @DisplayName("When a station creation is requested then it is persisted")
    void addNewStation() throws Exception {

        NewStationRequestDto newStation = NewStationRequestDto
                .builder()
                .name("Test Station")
                .latitude(53.4788305)
                .longitude(-2.2484721)
                .companyId(1)
                .build();

        newStationId =
                mapper
                        .readValue(
                                mockMvc
                                        .perform(
                                                post("/api/v1/station")
                                                        .contentType(MediaType.APPLICATION_JSON)
                                                        .content(mapper.writeValueAsString(newStation)))
                                        .andExpect(status().isCreated())
                                        .andReturn()
                                        .getResponse()
                                        .getContentAsString(),
                                Station.class)
                        .getId();

        Station station = stationRepository
                .findById(newStationId)
                .orElseThrow(
                        () -> new IllegalStateException("New Station has not been saved in the repository"));
        assertThat(station.getName(),
                equalTo(newStation.getName()));
    }

    @Test
    @DisplayName("When all stations are requested then they are all returned")
    void getAllStations() throws Exception {
        mockMvc
                .perform(get("/api/v1/station"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize((int) stationRepository.count())));
    }

    @Test
    @DisplayName("When a station is requested by id then it will be returned")
    void getStationById() throws Exception {
        mockMvc
                .perform(get("/api/v1/station/"+newStationId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("name", equalTo("Test Station")));
    }

    @Test
    @DisplayName("When a delete station is requested by id then it will be deleted")
    void deleteStationById() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/station/"+newStationId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}