package electric.vehicle.charging.station.management.service;

import electric.vehicle.charging.station.management.dto.CompanyStationDto;
import electric.vehicle.charging.station.management.dto.NewStationRequestDto;
import electric.vehicle.charging.station.management.dto.UpdateStationRequestDto;
import electric.vehicle.charging.station.management.model.Station;

import java.util.List;

public interface StationService {

    Station createNewStation(NewStationRequestDto request);
    Station updateNewStation(UpdateStationRequestDto request);
    Station getStationById(Long id);
    List<Station> getAllStations();
    void deleteStationById(Long id);
    List<CompanyStationDto> getAllStationsWithinRadius(double latitude, double longitude, double distance);
}
