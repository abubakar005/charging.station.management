package electric.vehicle.charging.station.management.service.impl;

import electric.vehicle.charging.station.management.converter.CompanyConverter;
import electric.vehicle.charging.station.management.dto.NewCompanyRequestDto;
import electric.vehicle.charging.station.management.dto.UpdateCompanyRequestDto;
import electric.vehicle.charging.station.management.enums.Error;
import electric.vehicle.charging.station.management.exception.ElementNotFoundException;
import electric.vehicle.charging.station.management.model.Company;
import electric.vehicle.charging.station.management.repository.CompanyRepository;
import electric.vehicle.charging.station.management.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyConverter companyConverter;

    @Override
    public Company createNewCompany(NewCompanyRequestDto request) {

        Company parent = null;

        Optional<Long> parentCompanyId = Optional.ofNullable(request.getParentCompanyId());

        if(parentCompanyId.isPresent()) {
            Optional<Company> parentCompany = companyRepository.findById(parentCompanyId.get());

            if(!parentCompany.isPresent())
                throw new ElementNotFoundException(Error.PARENT_COMPANY_NOT_FOUND.getCode(), String.format(Error.PARENT_COMPANY_NOT_FOUND.getMsg(), parentCompanyId.get()));

            parent = parentCompany.get();
        }

        Company company = companyConverter.NewCompanyRequestDtoToCompany(request);
        company.setParentCompany(parent);

        return companyRepository.save(company);
    }

    @Override
    public Company getCompanyById(Long id) {
        Optional<Company> company = companyRepository.findById(id);

        if(!company.isPresent())
            throw new ElementNotFoundException(Error.COMPANY_NOT_FOUND.getCode(), String.format(Error.COMPANY_NOT_FOUND.getMsg(), id));

        return company.get();
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public void deleteCompanyById(Long id) {
        companyRepository.deleteById(id);
    }

    @Override
    public Company updateCompany(UpdateCompanyRequestDto request) {

        Company company = getCompanyById(request.getId());
        Company parent = null;

        if(request.getParentCompanyId() != null) {
            Optional<Company> parentOptional = companyRepository.findById(request.getParentCompanyId());

            if(!parentOptional.isPresent())
                throw new ElementNotFoundException(Error.PARENT_COMPANY_NOT_FOUND.getCode(), String.format(Error.PARENT_COMPANY_NOT_FOUND.getMsg(), request.getParentCompanyId()));

            parent = parentOptional.get();

            if(parent.getParentCompany() != null && parent.getParentCompany().getId() == company.getId())
                throw new ElementNotFoundException(Error.PARENT_NOT_ALLOWED.getCode(), Error.PARENT_NOT_ALLOWED.getMsg());
        }

        company = companyConverter.UpdateCompanyRequestDtoToCompany(company, request, parent);
        companyRepository.save(company);

        return company;
    }

    @Override
    public List<Company> getChildsByParentId(long parentCompanyId) {
        return companyRepository.findByParentCompanyId(parentCompanyId);
    }
}
