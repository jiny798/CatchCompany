package catchcompany.web.module.pension.infra.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import catchcompany.web.module.pension.domain.PensionQuarterStock;

public interface PensionQuarterStockJpaRepository extends JpaRepository<PensionQuarterStock, Long> {
	List<PensionQuarterStock> findByQuarter(String quarter);

	Page<PensionQuarterStock> findPageByQuarter(String quarter, Pageable pageable);
}
