package electric.vehicle.charging.station.management.resource;

import electric.vehicle.charging.station.management.dto.NewCompanyRequestDto;
import electric.vehicle.charging.station.management.dto.UpdateCompanyRequestDto;
import electric.vehicle.charging.station.management.model.Company;
import electric.vehicle.charging.station.management.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company addCompany(@RequestBody NewCompanyRequestDto requestDto) {
        return companyService.createNewCompany(requestDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Company updateCompany(@RequestBody UpdateCompanyRequestDto requestDto) {
        return companyService.updateCompany(requestDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Company> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Company getCompanyById(@PathVariable("id") Long id) {
        return companyService.getCompanyById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompanyById(@PathVariable("id") Long id) {
        companyService.deleteCompanyById(id);
    }
}
