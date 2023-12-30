package catchcompany.web.module.company.service.port;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import catchcompany.web.module.company.domain.Company;

public interface CompanyRepository {

	List<Company> findByName(String name);

	Company findAllWithInvestInfo(String name);

	Boolean existsByName(String name);

	Company save(Company company);
}
