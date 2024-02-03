package catchcompany.web.module.pension.infra.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import catchcompany.web.module.pension.domain.PensionYearStock;

@Repository
public interface PensionYearStockJpaRepository extends JpaRepository<PensionYearStock, Long> {

	List<PensionYearStock> findByYear(int year);

	List<PensionYearStock> findByYearAndCorporationName(int year, String corporationName);

	Page<PensionYearStock> findPageByYear(int year, Pageable pageable);
}
