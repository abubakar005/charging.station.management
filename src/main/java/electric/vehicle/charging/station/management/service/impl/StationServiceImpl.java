package electric.vehicle.charging.station.management.service.impl;

import electric.vehicle.charging.station.management.converter.StationConverter;
import electric.vehicle.charging.station.management.dto.CompanyStationDto;
import electric.vehicle.charging.station.management.dto.NewStationRequestDto;
import electric.vehicle.charging.station.management.dto.StationDto;
import electric.vehicle.charging.station.management.dto.UpdateStationRequestDto;
import electric.vehicle.charging.station.management.enums.Error;
import electric.vehicle.charging.station.management.exception.ElementNotFoundException;
import electric.vehicle.charging.station.management.model.Company;
import electric.vehicle.charging.station.management.model.Station;
import electric.vehicle.charging.station.management.repository.StationRepository;
import electric.vehicle.charging.station.management.service.CompanyService;
import electric.vehicle.charging.station.management.service.StationService;
import electric.vehicle.charging.station.management.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StationServiceImpl implements StationService {

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private StationConverter stationConverter;

    @Override
    public Station createNewStation(NewStationRequestDto request) {

        Company company = companyService.getCompanyById(request.getCompanyId());

        if(company == null)
            throw new ElementNotFoundException(Error.COMPANY_NOT_FOUND.getCode(), String.format(Error.COMPANY_NOT_FOUND.getMsg(), request.getCompanyId()));

        Station station = stationConverter.newStationRequestDtoToStation(request);
        station.setCompany(company);

        return stationRepository.save(station);
    }

    @Override
    public Station updateStation(UpdateStationRequestDto request) {

        Station station = getStationById(request.getId());
        Company company = companyService.getCompanyById(request.getCompanyId());

        if(company == null)
            throw new ElementNotFoundException(Error.COMPANY_NOT_FOUND.getCode(), String.format(Error.COMPANY_NOT_FOUND.getMsg(), request.getCompanyId()));

        station = stationConverter.updateStationRequestDtoToStation(station, request, company);
        stationRepository.save(station);

        return station;
    }

    @Override
    public Station getStationById(Long id) {

        Optional<Station> station = stationRepository.findById(id);

        if(!station.isPresent())
            throw new ElementNotFoundException(Error.STATION_NOT_FOUND.getCode(), String.format(Error.STATION_NOT_FOUND.getMsg(), id));

        return station.get();
    }

    @Override
    public List<Station> getAllStations() {
        return stationRepository.findAll();
    }

    @Override
    public void deleteStationById(Long id) {
        stationRepository.deleteById(id);
    }

    @Override
    public List<CompanyStationDto> getAllStationsWithinRadius(double latitude, double longitude, double distance) {

        List<Company> companies = companyService.getAllCompanies();
        List<Station> stations = stationRepository.searchAllStationsWithinRange(latitude, longitude, distance);
        List<CompanyStationDto> companyStationDtoList = new ArrayList<>();
        CompanyStationDto companyStationDto;
        List<StationDto> stationList;
        StationDto stationDto;

        for(Company company : companies) {

            companyStationDto = new CompanyStationDto();
            stationList = new ArrayList<>();
            companyStationDto.setId(company.getId());
            companyStationDto.setName(company.getName());
            companyStationDtoList.add(companyStationDto);

            List<Station> li = stations.stream()
                    .filter(station-> station.getCompany().getId() == company.getId())
                    .collect(Collectors.toList());

            Queue<Station> queue = new LinkedList<>();
            queue.addAll(li);
            long previous = 0l;

            while (!queue.isEmpty()) {

                Station temp = queue.poll();

                if(previous != temp.getCompany().getId()) {
                    List<Company> childs = companyService.getChildsByParentId(temp.getCompany().getId());

                    for(Company child : childs) {
                        li = stations.stream()
                                .filter(station-> station.getCompany().getId() == child.getId())
                                .collect(Collectors.toList());
                        queue.addAll(li);
                    }

                    previous = temp.getCompany().getId();
                }

                stationDto = new StationDto();
                stationDto.setId(temp.getId());
                stationDto.setName(temp.getName());
                stationDto.setLatitude(temp.getLatitude());
                stationDto.setLongitude(temp.getLongitude());
                stationList.add(stationDto);
            }

            companyStationDto.getStations().addAll(stationList);
        }

        return companyStationDtoList;
    }

    private void newStationRequestDtoToStation(Station station, NewStationRequestDto request) {
        station.setName(request.getName());
        station.setLatitude(request.getLatitude());
        station.setLongitude(request.getLongitude());
        station.setCreatedBy(Constants.SYSTEM_USER);
        station.setCreationDate(LocalDateTime.now());
    }

    private void updateStationRequestDtoToStation(Station station, UpdateStationRequestDto requestDto, Company company) {
        station.setName(requestDto.getName());
        station.setLatitude(requestDto.getLatitude());
        station.setLongitude(requestDto.getLongitude());
        station.setCompany(company);
        station.setUpdatedBy(Constants.SYSTEM_USER);
        station.setUpdatedDate(LocalDateTime.now());
    }
}
