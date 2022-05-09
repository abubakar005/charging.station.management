package electric.vehicle.charging.station.management.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import electric.vehicle.charging.station.management.dto.CompanyStationDto;
import electric.vehicle.charging.station.management.dto.NewStationRequestDto;
import electric.vehicle.charging.station.management.model.Station;
import electric.vehicle.charging.station.management.service.StationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StationController.class)
class StationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StationService stationService;

    @Test
    @DisplayName("When a station creation is requested then it is persisted")
    void addNewStation() throws Exception {
        Station station = new Station().toBuilder().build();

        NewStationRequestDto newStation = NewStationRequestDto
                .builder()
                .name("Test Station")
                .latitude(53.4788305)
                .longitude(-2.2484721)
                .companyId(1)
                .build();

        when(stationService.createNewStation(any()))
                .thenReturn(station);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/v1/station")
                .content(asJsonString(newStation))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    @DisplayName("When a station is requested by id then it will be returned")
    void getStationById() throws Exception {
        Station station = new Station().toBuilder().build();
        when(stationService.getStationById(anyLong()))
                .thenReturn(station);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/v1/station/{id}", 1)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DisplayName("When all stations are requested then they are all returned")
    void getAllStations() throws Exception {
        List<Station> stations = new ArrayList<>();
        when(stationService.getAllStations())
                .thenReturn(stations);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/v1/station")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DisplayName("When a delete station is requested by id then it will be deleted")
    void deleteStationById() throws Exception {
        doNothing().when(stationService).deleteStationById(anyLong());

        RequestBuilder request = MockMvcRequestBuilders
                .delete("/api/v1/station/{id}", 1)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    @DisplayName("When all stations are requested within radius and from some location")
    void getAllStationsWithinRadius() throws Exception {

        List<CompanyStationDto> stations = new ArrayList<>();
        when(stationService.getAllStationsWithinRadius(anyDouble(), anyDouble(), anyDouble()))
                .thenReturn(stations);

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("latitude", "52.3756755");
        requestParams.add("longitude", "4.8668628");
        requestParams.add("distance", "10");

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/v1/station/search/all-stations-within-radius")
                .params(requestParams)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}