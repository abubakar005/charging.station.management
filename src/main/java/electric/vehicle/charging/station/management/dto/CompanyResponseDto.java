package electric.vehicle.charging.station.management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CompanyResponseDto {

    private Long id;
    private String name;
    private Long parentCompanyId;
}
