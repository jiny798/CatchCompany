package catchcompany.web.module.pension.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import catchcompany.web.module.pension.domain.PensionMonthStock;

public interface PensionMonthStockJpaRepository extends JpaRepository<PensionMonthStock, Long> {

}
