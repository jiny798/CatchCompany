package catchcompany.web.module.corporation.service.port;

import java.util.List;

import org.springframework.stereotype.Repository;

import catchcompany.web.module.corporation.domain.Corporation;

@Repository
public interface CorporationRepository {

	List<Corporation> findByName(String name);

	Corporation findAllWithInvestInfo(String name);

	Boolean existsByName(String name);

	Corporation save(Corporation corporation);
}
