package electric.vehicle.charging.station.management.resource;

import electric.vehicle.charging.station.management.dto.CompanyStationDto;
import electric.vehicle.charging.station.management.dto.NewStationRequestDto;
import electric.vehicle.charging.station.management.dto.UpdateStationRequestDto;
import electric.vehicle.charging.station.management.model.Station;
import electric.vehicle.charging.station.management.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/station")
public class StationController {

    @Autowired
    private StationService stationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Station addStation(@RequestBody NewStationRequestDto requestDto) {
        return stationService.createNewStation(requestDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Station updateStation(@RequestBody UpdateStationRequestDto requestDto) {
        return stationService.updateNewStation(requestDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Station getStationById(@PathVariable("id") Long id) {
        return stationService.getStationById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Station> getAllStations() {
        return stationService.getAllStations();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStationById(@PathVariable("id") Long id) {
        stationService.deleteStationById(id);
    }

    @GetMapping("/search/all-stations-within-radius")
    @ResponseStatus(HttpStatus.OK)
    public List<CompanyStationDto> getAllStationsWithinRadius(@RequestParam("latitude") double latitude,
                                                               @RequestParam("longitude") double longitude,
                                                               @RequestParam("distance") double distance) {
        return stationService.getAllStationsWithinRadius(latitude, longitude, distance);
    }
}
