package catchcompany.web.module.pension.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import catchcompany.web.module.pension.domain.PensionQuarterStock;

public interface PensionQuarterStockJpaRepository extends JpaRepository<PensionQuarterStock, Long> {

}
