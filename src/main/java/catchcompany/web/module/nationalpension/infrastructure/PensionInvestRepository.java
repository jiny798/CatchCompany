package catchcompany.web.module.nationalpension.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import catchcompany.web.module.nationalpension.domain.PensionStock;

@Repository
public interface PensionInvestRepository extends JpaRepository<PensionStock, Long> {

	List<PensionStock> findByYear(int year);

	List<PensionStock> findByYearAndCorporationName(int year, String corporationName);
}
