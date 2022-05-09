package electric.vehicle.charging.station.management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "company")
public class Company extends AbstractAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_company_id")
    private Company parentCompany;

   /* @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentCompany")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Company> children;

    @JsonIgnore
    public Set<Company> getChildren() {
        return children;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company)) return false;
        Company company1 = (Company) o;
        return id.equals(company1.id) &&
                name.equals(company1.name) &&
                Objects.equals(parentCompany, company1.parentCompany);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentCompany=" + parentCompany +
                '}';
    }
}
