package electric.vehicle.charging.station.management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class NewCompanyRequestDto {

    private String name;
    private Long parentCompanyId; // It can be null
}
