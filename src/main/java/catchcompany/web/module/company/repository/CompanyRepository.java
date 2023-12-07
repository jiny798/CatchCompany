package catchcompany.web.module.company.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import catchcompany.web.module.company.domain.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

	List<Company> findByName(String name);

	@Query("select c from Company c where c.name = :name")
	Company findAllWithInvestInfo(@Param("name") String name);
}
