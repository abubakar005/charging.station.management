package electric.vehicle.charging.station.management.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorInfoDTO {

    @Schema(name = "code", description = "Error Code", type = "int", required = true)
    private int code;

    @Schema(name = "message", description = "Error Message", type = "String", required = true)
    private String message;

    public ErrorInfoDTO(int code) {
        this.code = code;
    }

}
