package electric.vehicle.charging.station.management.converter;

import electric.vehicle.charging.station.management.dto.NewStationRequestDto;
import electric.vehicle.charging.station.management.dto.UpdateStationRequestDto;
import electric.vehicle.charging.station.management.model.Company;
import electric.vehicle.charging.station.management.model.Station;

public interface StationConverter {

    Station newStationRequestDtoToStation(NewStationRequestDto request);
    Station updateStationRequestDtoToStation(Station station, UpdateStationRequestDto requestDto, Company company);
}
