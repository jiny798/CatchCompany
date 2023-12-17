package catchcompany.web.module.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import catchcompany.web.module.stock.domain.Stock;

public interface StockRepository extends JpaRepository<Stock,Long> {
}
