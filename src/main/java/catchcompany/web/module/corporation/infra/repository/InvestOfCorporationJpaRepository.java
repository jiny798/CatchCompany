package catchcompany.web.module.corporation.infra.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import catchcompany.web.module.corporation.domain.Corporation;
import catchcompany.web.module.corporation.domain.InvestOfCorporation;

public interface InvestOfCorporationJpaRepository extends JpaRepository<InvestOfCorporation, Long> {

	@Query("select inv from InvestOfCorporation inv where inv.corporation = :corporation and inv.corporationClass = :type ")
	Page<InvestOfCorporation> findInvestByCompanyAndType(@Param("corporation") Corporation corporation, Pageable pageable, @Param("type") String type);

	@Query("select inv from InvestOfCorporation inv where inv.corporation = :corporation ")
	Page<InvestOfCorporation> findInvestByCompany(@Param("corporation") Corporation corporation, Pageable pageable);
}
