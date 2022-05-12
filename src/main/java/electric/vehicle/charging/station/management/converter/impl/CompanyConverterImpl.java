package electric.vehicle.charging.station.management.converter.impl;

import electric.vehicle.charging.station.management.converter.CompanyConverter;
import electric.vehicle.charging.station.management.dto.NewCompanyRequestDto;
import electric.vehicle.charging.station.management.dto.UpdateCompanyRequestDto;
import electric.vehicle.charging.station.management.model.Company;
import electric.vehicle.charging.station.management.util.Constants;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CompanyConverterImpl implements CompanyConverter {

    @Override
    public Company NewCompanyRequestDtoToCompany(NewCompanyRequestDto request) {
        Company company = Company.builder().build();
        company.setName(request.getName());
        company.setCreatedBy(Constants.SYSTEM_USER);
        company.setCreationDate(LocalDateTime.now());
        return company;
    }

    @Override
    public Company UpdateCompanyRequestDtoToCompany(Company company, UpdateCompanyRequestDto request, Company parent) {
        company.setName(request.getName());
        company.setParentCompany(parent);
        company.setUpdatedBy(Constants.SYSTEM_USER);
        company.setUpdatedDate(LocalDateTime.now());
        return company;
    }
}
