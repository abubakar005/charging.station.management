package electric.vehicle.charging.station.management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UpdateStationRequestDto implements Serializable {

    private long id;
    private String name;
    private double latitude;
    private double longitude;
    private long companyId;
}
