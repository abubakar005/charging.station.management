package electric.vehicle.charging.station.management.converter;

import electric.vehicle.charging.station.management.dto.NewCompanyRequestDto;
import electric.vehicle.charging.station.management.dto.UpdateCompanyRequestDto;
import electric.vehicle.charging.station.management.model.Company;

public interface CompanyConverter {

    Company NewCompanyRequestDtoToCompany(NewCompanyRequestDto request);
    Company UpdateCompanyRequestDtoToCompany(Company company, UpdateCompanyRequestDto request, Company parent);
}
