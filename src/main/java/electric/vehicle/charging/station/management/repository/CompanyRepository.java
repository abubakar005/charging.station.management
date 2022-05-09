package electric.vehicle.charging.station.management.repository;

import electric.vehicle.charging.station.management.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    List<Company> findByParentCompanyId(long parenCompanyId);
}
