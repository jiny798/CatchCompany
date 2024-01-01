package catchcompany.web.module.corporation.infrastructure;

import java.util.List;

import org.springframework.stereotype.Repository;

import catchcompany.web.module.corporation.domain.Corporation;
import catchcompany.web.module.corporation.service.port.CorporationRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CorporationRepositoryImpl implements CorporationRepository {
	private final CorporationJpaRepository corporationJpaRepository;
	@Override
	public List<Corporation> findByName(String name) {
		return corporationJpaRepository.findByName(name);
	}

	@Override
	public Corporation findAllWithInvestInfo(String name) {
		return corporationJpaRepository.findAllWithInvestInfo(name);
	}

	@Override
	public Corporation save(Corporation corporation) {
		return corporationJpaRepository.save(corporation);
	}

	@Override
	public Boolean existsByName(String name) {
		return corporationJpaRepository.existsByName(name);
	}
}
