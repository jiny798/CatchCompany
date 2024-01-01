package catchcompany.web.module.corporation.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import catchcompany.web.module.corporation.domain.Corporation;

public interface CorporationJpaRepository extends JpaRepository<Corporation, Long> {

	List<Corporation> findByName(String name);

	@Query("select c from Company c where c.name = :name")
	Corporation findAllWithInvestInfo(@Param("name") String name);

	Boolean existsByName(String name);
}
