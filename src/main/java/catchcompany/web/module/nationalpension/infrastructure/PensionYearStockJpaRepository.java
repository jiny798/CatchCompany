package catchcompany.web.module.nationalpension.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import catchcompany.web.module.nationalpension.domain.PensionYearStock;

@Repository
public interface PensionYearStockJpaRepository extends JpaRepository<PensionYearStock, Long> {

	List<PensionYearStock> findByYear(int year);

	List<PensionYearStock> findByYearAndCorporationName(int year, String corporationName);
}
