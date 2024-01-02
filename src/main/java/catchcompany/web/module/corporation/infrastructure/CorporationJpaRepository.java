package catchcompany.web.module.corporation.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import catchcompany.web.module.corporation.domain.Corporation;

public interface CorporationJpaRepository extends JpaRepository<Corporation, Long> {

	List<Corporation> findByName(String name);

	@Query("select c from Corporation c where c.name = :name")
	Corporation findAllWithInvestInfo(@Param("name") String name);

	Boolean existsByName(String name);
}
