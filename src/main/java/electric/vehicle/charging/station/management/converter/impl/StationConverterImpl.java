package electric.vehicle.charging.station.management.converter.impl;

import electric.vehicle.charging.station.management.converter.StationConverter;
import electric.vehicle.charging.station.management.dto.NewStationRequestDto;
import electric.vehicle.charging.station.management.dto.UpdateStationRequestDto;
import electric.vehicle.charging.station.management.model.Company;
import electric.vehicle.charging.station.management.model.Station;
import electric.vehicle.charging.station.management.util.Constants;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class StationConverterImpl implements StationConverter {

    @Override
    public Station newStationRequestDtoToStation(NewStationRequestDto request) {
        Station station = Station.builder().build();
        station.setName(request.getName());
        station.setLatitude(request.getLatitude());
        station.setLongitude(request.getLongitude());
        station.setCreatedBy(Constants.SYSTEM_USER);
        station.setCreationDate(LocalDateTime.now());
        return station;
    }

    @Override
    public Station updateStationRequestDtoToStation(Station station, UpdateStationRequestDto requestDto, Company company) {
        station.setName(requestDto.getName());
        station.setLatitude(requestDto.getLatitude());
        station.setLongitude(requestDto.getLongitude());
        station.setCompany(company);
        station.setUpdatedBy(Constants.SYSTEM_USER);
        station.setUpdatedDate(LocalDateTime.now());
        return station;
    }
}
