package electric.vehicle.charging.station.management.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import electric.vehicle.charging.station.management.dto.NewCompanyRequestDto;
import electric.vehicle.charging.station.management.model.Company;
import electric.vehicle.charging.station.management.service.CompanyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CompanyController.class)
class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyService companyService;

    @Test
    @DisplayName("When a company creation is requested then it is persisted")
    void addNewCompany() throws Exception {
        Company company = new Company().toBuilder().build();

        NewCompanyRequestDto newCompany = NewCompanyRequestDto
                .builder()
                .name("Test Company")
                .parentCompanyId(null)
                .build();

        when(companyService.createNewCompany(any()))
                .thenReturn(company);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/v1/company")
                .content(asJsonString(newCompany))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    @DisplayName("When all Companies are requested then they are all returned")
    void getAllCompanies() throws Exception {
        List<Company> companies = new ArrayList<>();
        when(companyService.getAllCompanies())
                .thenReturn(companies);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/v1/company")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DisplayName("When a Company is requested by id then it will be returned")
    void getCompanyById() throws Exception {

        Company company = new Company().toBuilder().build();
        when(companyService.getCompanyById(anyLong()))
                .thenReturn(company);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/v1/company/{id}", 1)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DisplayName("When a delete company is requested by id then it will be deleted")
    void deleteCompanyById() throws Exception {

        doNothing().when(companyService).deleteCompanyById(anyLong());

        RequestBuilder request = MockMvcRequestBuilders
                .delete("/api/v1/company/{id}", 1)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNoContent())
                .andReturn();
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}