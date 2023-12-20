package catchcompany.web.module.stock.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import catchcompany.web.module.stock.domain.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {

	List<Stock> findByBasDt(String basDt);

	Optional<Stock> findByIdAndBasDt(Long id, String basDt);
}
