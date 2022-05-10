package electric.vehicle.charging.station.management.service;

import electric.vehicle.charging.station.management.dto.NewCompanyRequestDto;
import electric.vehicle.charging.station.management.dto.UpdateCompanyRequestDto;
import electric.vehicle.charging.station.management.model.Company;

import java.util.List;

public interface CompanyService {

    Company createNewCompany(NewCompanyRequestDto request);
    Company getCompanyById(Long id);
    List<Company> getAllCompanies();
    void deleteCompanyById(Long id);
    Company updateCompany(UpdateCompanyRequestDto request);
    List<Company> getChildsByParentId(long parentCompanyId);
}
