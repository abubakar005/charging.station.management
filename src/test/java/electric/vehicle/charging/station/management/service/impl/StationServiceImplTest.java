package electric.vehicle.charging.station.management.service.impl;

import electric.vehicle.charging.station.management.converter.StationConverter;
import electric.vehicle.charging.station.management.dto.CompanyStationDto;
import electric.vehicle.charging.station.management.dto.NewStationRequestDto;
import electric.vehicle.charging.station.management.dto.UpdateStationRequestDto;
import electric.vehicle.charging.station.management.model.Company;
import electric.vehicle.charging.station.management.model.Station;
import electric.vehicle.charging.station.management.repository.StationRepository;
import electric.vehicle.charging.station.management.service.CompanyService;
import electric.vehicle.charging.station.management.service.StationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class StationServiceImplTest {

    @InjectMocks
    private StationService stationService = new StationServiceImpl();

    @Mock
    private CompanyService companyService;

    @Mock
    private StationRepository stationRepository;

    @Mock
    private StationConverter stationConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createNewStation() {
        Optional<Station> station = getMockStation();
        NewStationRequestDto requestDto = getMockNewStationRequestDto();
        Company mockCompany = getMockCompany().get();

        when(stationRepository.save(any())).thenReturn(station.get());
        when(stationConverter.newStationRequestDtoToStation(any())).thenReturn(station.get());
        when(companyService.getCompanyById(anyLong())).thenReturn(mockCompany);

        Station response = stationService.createNewStation(requestDto);

        assertNotNull(response);
        assertEquals("Test Station", response.getName());
        assertEquals(1, response.getId());
        assertEquals(53.4788305, response.getLatitude());
        assertEquals(-2.2484721, response.getLongitude());
    }

    @Test
    void updateNewStation() {

        Optional<Station> station = getMockStation();
        UpdateStationRequestDto requestDto = getMockUpdateStationRequestDto();
        Company mockCompany = getMockCompany().get();

        when(stationRepository.findById(anyLong())).thenReturn(station);
        when(companyService.getCompanyById(anyLong())).thenReturn(mockCompany);
        when(stationConverter.updateStationRequestDtoToStation(any(), any(), any())).thenReturn(station.get());
        when(stationRepository.save(any())).thenReturn(station.get());

        Station response = stationService.updateStation(requestDto);

        assertNotNull(response);
        assertEquals("Test Station", response.getName());
        assertEquals(1, response.getId());
        assertEquals(53.4788305, response.getLatitude());
        assertEquals(-2.2484721, response.getLongitude());
    }

    @Test
    void getStationById() {
        when(stationRepository.findById(anyLong())).thenReturn(getMockStation());

        Station response = stationService.getStationById(1L);

        assertNotNull(response);
        assertEquals("Test Station", response.getName());
        assertEquals(1, response.getId());
        assertEquals(53.4788305, response.getLatitude());
        assertEquals(-2.2484721, response.getLongitude());
    }

    @Test
    void getAllStations() {
        when(stationRepository.findAll()).thenReturn(Arrays.asList(getMockStation().get()));

        List<Station> response = stationService.getAllStations();

        assertNotNull(response);
        assertEquals("Test Station", response.get(0).getName());
        assertEquals(1, response.size());
        assertEquals(53.4788305, response.get(0).getLatitude());
        assertEquals(-2.2484721, response.get(0).getLongitude());
    }

    @Test
    void deleteStationById() {
        stationService.deleteStationById(100l);
        verify(stationRepository, times(1)).deleteById(100l);
    }

    @Test
    void getAllStationsWithinRadius() {
        List<Company> companies = Arrays.asList(getMockCompany().get());
        List<Company> child = Arrays.asList(getMockChildCompany().get());
        when(companyService.getAllCompanies()).thenReturn(companies);
        when(stationRepository.searchAllStationsWithinRange(anyDouble(), anyDouble(), anyDouble())).thenReturn(Arrays.asList(getMockStation().get()));
        when(companyService.getChildsByParentId(anyLong())).thenReturn(child);

        List<CompanyStationDto> response = stationService.getAllStationsWithinRadius(53.4788305, -2.2484721, 10);

        assertNotNull(response);
        assertEquals("Test Company", response.get(0).getName());
        assertEquals(1, response.size());
    }

    private NewStationRequestDto getMockNewStationRequestDto() {
        return new NewStationRequestDto()
                .toBuilder()
                .name("Test Station")
                .latitude(53.4788305)
                .longitude(-2.2484721)
                .companyId(1)
                .build();
    }

    private UpdateStationRequestDto getMockUpdateStationRequestDto() {
        return new UpdateStationRequestDto()
                .toBuilder()
                .id(1)
                .name("Test Station")
                .latitude(53.4788305)
                .longitude(-2.2484721)
                .companyId(1)
                .build();
    }

    private Optional<Station> getMockStation() {
        return Optional.ofNullable(new Station()
                .builder()
                .id(1l)
                .name("Test Station")
                .latitude(53.4788305)
                .longitude(-2.2484721)
                .company(getMockCompany().get())
                .build());
    }

    private Optional<Company> getMockCompany() {
        return Optional.ofNullable(new Company()
                .builder()
                .id(1l)
                .name("Test Company")
                .parentCompany(null)
                .build());
    }

    private Optional<Company> getMockChildCompany() {
        return Optional.ofNullable(new Company()
                .builder()
                .id(2l)
                .name("Test Company 2")
                .parentCompany(null)
                .build());
    }
}