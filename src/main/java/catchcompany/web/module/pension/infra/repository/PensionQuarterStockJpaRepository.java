package catchcompany.web.module.pension.infra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import catchcompany.web.module.pension.domain.PensionQuarterStock;

public interface PensionQuarterStockJpaRepository extends JpaRepository<PensionQuarterStock, Long> {
	List<PensionQuarterStock> findByCorporationName(String corporationName);
}
