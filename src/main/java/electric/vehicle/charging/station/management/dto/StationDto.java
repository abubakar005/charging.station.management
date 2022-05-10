package electric.vehicle.charging.station.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StationDto {

    private Long id;
    private String name;
    private double latitude;
    private double longitude;
}
