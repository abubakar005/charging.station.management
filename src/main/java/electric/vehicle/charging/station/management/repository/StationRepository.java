package electric.vehicle.charging.station.management.repository;

import electric.vehicle.charging.station.management.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> {

    List<Station> findByCompanyId(long companyId);

    String HAVERSINE_PART = "(6371 * acos(cos(radians(:latitude)) * cos(radians(s.latitude)) *" +
            " cos(radians(s.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(s.latitude))))";
    @Query(value = "SELECT * FROM station s WHERE " + HAVERSINE_PART + " < :distance ORDER BY "+ HAVERSINE_PART + " ASC", nativeQuery = true)
    List<Station> searchAllStationsWithinRange(@Param("latitude") double latitude, @Param("longitude") double longitude, @Param("distance") double distanceWithInKM );

}
