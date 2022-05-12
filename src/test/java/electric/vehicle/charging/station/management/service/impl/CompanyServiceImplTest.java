package electric.vehicle.charging.station.management.service.impl;

import electric.vehicle.charging.station.management.converter.CompanyConverter;
import electric.vehicle.charging.station.management.dto.NewCompanyRequestDto;
import electric.vehicle.charging.station.management.dto.UpdateCompanyRequestDto;
import electric.vehicle.charging.station.management.model.Company;
import electric.vehicle.charging.station.management.repository.CompanyRepository;
import electric.vehicle.charging.station.management.service.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class CompanyServiceImplTest {

    @InjectMocks
    private CompanyService companyService = new CompanyServiceImpl();

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private CompanyConverter companyConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createNewCompany() {
        NewCompanyRequestDto requestDto = getMockedNewCompanyRequestDto();
        Company mockCompany = getMockedCompany().get();

        when(companyRepository.findById(anyLong())).thenReturn(getMockedParentCompany());
        when(companyConverter.NewCompanyRequestDtoToCompany(any())).thenReturn(mockCompany);
        when(companyRepository.save(any())).thenReturn(mockCompany);

        Company response = companyService.createNewCompany(requestDto);

        assertNotNull(response);
        assertEquals("Test Company", response.getName());
        assertEquals(1, response.getId());
    }

    @Test
    void updateCompany() {
        UpdateCompanyRequestDto requestDto = getMockedUpdateCompanyRequestDto();
        Company mockCompany = getMockedCompany().get();

        when(companyRepository.findById(anyLong())).thenReturn(getMockedCompany());
        when(companyConverter.UpdateCompanyRequestDtoToCompany(any(), any(), any())).thenReturn(mockCompany);
        when(companyRepository.save(any())).thenReturn(mockCompany);

        Company response = companyService.updateCompany(requestDto);

        assertNotNull(response);
        assertEquals("Test Company", response.getName());
        assertEquals(1, response.getId());
    }

    @Test
    void getCompanyById() {
        when(companyRepository.findById(anyLong())).thenReturn(getMockedCompany());

        Company response = companyService.getCompanyById(1l);

        assertNotNull(response);
        assertEquals(1, response.getId());
        assertEquals("Test Company", response.getName());
    }

    @Test
    void getAllCompanies() {
        when(companyRepository.findAll()).thenReturn(Arrays.asList(getMockedCompany().get()));

        List<Company> response = companyService.getAllCompanies();

        assertNotNull(response);
        assertEquals(1, response.get(0).getId());
        assertEquals("Test Company", response.get(0).getName());
        assertEquals(1, response.size());
    }

    @Test
    void deleteCompanyById() {
        companyService.deleteCompanyById(100l);
        verify(companyRepository, times(1)).deleteById(100l);
    }

    private NewCompanyRequestDto getMockedNewCompanyRequestDto() {
        return NewCompanyRequestDto
                .builder()
                .name("Test Company")
                .parentCompanyId(null)
                .build();
    }

    private UpdateCompanyRequestDto getMockedUpdateCompanyRequestDto() {
        return UpdateCompanyRequestDto
                .builder()
                .id(1)
                .name("Test Company")
                .parentCompanyId(null)
                .build();
    }

    private Optional<Company> getMockedCompany() {
        return Optional.of(
                Company.builder()
                        .id(1l)
                        .name("Test Company")
                        .parentCompany(null)
                        .build());
    }

    private Optional<Company> getMockedParentCompany() {
        return Optional.of(
                Company.builder()
                        .id(2l)
                        .name("Parent Company")
                        .parentCompany(null)
                        .build());
    }
}