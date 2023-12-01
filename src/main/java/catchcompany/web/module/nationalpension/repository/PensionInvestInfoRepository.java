package catchcompany.web.module.nationalpension.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import catchcompany.web.module.nationalpension.domain.PensionInvestInfo;

@Repository
public interface PensionInvestInfoRepository extends JpaRepository<PensionInvestInfo, Long> {
}
