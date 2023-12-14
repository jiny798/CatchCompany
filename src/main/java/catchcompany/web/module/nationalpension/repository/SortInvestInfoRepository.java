package catchcompany.web.module.nationalpension.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import catchcompany.web.module.nationalpension.domain.SortInvestInfo;

public interface SortInvestInfoRepository extends JpaRepository<SortInvestInfo, Long> {
	List<SortInvestInfo> findByYear(int year);
}
