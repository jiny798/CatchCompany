package catchcompany.web.module.nationalpension.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import catchcompany.web.module.nationalpension.domain.PensionMonthStock;
import catchcompany.web.module.nationalpension.domain.PensionYearStock;

public interface PensionMonthStockJpaRepository extends JpaRepository<PensionMonthStock, Long> {

}
