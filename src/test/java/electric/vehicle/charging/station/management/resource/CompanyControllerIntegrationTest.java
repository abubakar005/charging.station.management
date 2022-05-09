package electric.vehicle.charging.station.management.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import electric.vehicle.charging.station.management.dto.NewCompanyRequestDto;
import electric.vehicle.charging.station.management.model.Company;
import electric.vehicle.charging.station.management.repository.CompanyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CompanyControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CompanyRepository companyRepository;

    static Long newCompanyId;

    @Test
    @DisplayName("When a company creation is requested then it is persisted")
    void companyCreatedCorrectly() throws Exception {

        NewCompanyRequestDto newCompany = NewCompanyRequestDto
                .builder()
                .name("Test Company")
                .parentCompanyId(null)
                .build();

        newCompanyId =
                mapper
                        .readValue(
                                mockMvc
                                        .perform(
                                                post("/api/v1/company")
                                                        .contentType(MediaType.APPLICATION_JSON)
                                                        .content(mapper.writeValueAsString(newCompany)))
                                        .andExpect(status().isCreated())
                                        .andReturn()
                                        .getResponse()
                                        .getContentAsString(),
                                Company.class)
                        .getId();

        Company company = companyRepository
                .findById(newCompanyId)
                .orElseThrow(
                        () -> new IllegalStateException("New Company has not been saved in the repository"));
        assertThat(company.getName(),
                equalTo(newCompany.getName()));
    }

    @Test
    @DisplayName("When all Companies are requested then they are all returned")
    void allCompaniesRequested() throws Exception {
        mockMvc
                .perform(get("/api/v1/company"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize((int) companyRepository.count())));
    }

    @Test
    @DisplayName("When a Company is requested by id then it will be returned")
    void getCompanyById() throws Exception {
        mockMvc
                .perform(get("/api/v1/company/"+newCompanyId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("name", equalTo("Test Company")));
    }

    @Test
    @DisplayName("When a delete company is requested by id then it will be deleted")
    void deleteCompanyById() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/company/"+newCompanyId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}