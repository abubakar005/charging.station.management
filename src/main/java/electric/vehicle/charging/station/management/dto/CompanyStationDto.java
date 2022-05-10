package electric.vehicle.charging.station.management.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CompanyStationDto {

    private Long id;
    private String name;
    private List<StationDto> stations;

    public CompanyStationDto() {
        stations = new ArrayList<>();
    }
}
