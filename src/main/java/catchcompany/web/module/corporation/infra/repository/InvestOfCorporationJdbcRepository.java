package catchcompany.web.module.corporation.infra.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import catchcompany.web.module.corporation.domain.Corporation;
import catchcompany.web.module.corporation.domain.InvestOfCorporation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class InvestOfCorporationJdbcRepository {
	private final JdbcTemplate jdbcTemplate;

	private int batchSize = 10000;

	public void saveAll(List<InvestOfCorporation> investList) {
		int batchCount = 0;
		List<InvestOfCorporation> subInvestList = new ArrayList<>();
		for (int i = 0; i < investList.size(); i++) {
			subInvestList.add(investList.get(i));
			if ((i + 1) % batchSize == 0) {
				batchCount = batchInsert(batchCount, subInvestList);
			}
		}
		if (!subInvestList.isEmpty()) {
			batchCount = batchInsert(batchCount, subInvestList);
		}
	}

	private int batchInsert(int batchCount, List<InvestOfCorporation> investList) {
		jdbcTemplate.batchUpdate("INSERT INTO invest_of_corporation ("
				+ " `corporation_id`,"
				+ " `investor_name`,"
				+ " `corporation_code`,"
				+ " `corporation_name`,"
				+ " `corporation_class`,"
				+ " `target`,"
				+ " `initial_investment_date`,"
				+ " `current_stock_count`,"
				+ " `current_stock_share_ratio`,"
				+ " `current_stock_evaluation_value`,"
				+ " `recent_stock_amount_of_change`,"
				+ " `recent_acquisition_amount`,"
				+ " `recent_evaluation_gains_and_losses`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
			new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					if (investList.get(i).getCorporation() != null) {
						ps.setLong(1, investList.get(i).getCorporation().getId());
					} else {
						ps.setNull(1, Types.NUMERIC);
					}
					ps.setString(2, investList.get(i).getInvestorName());
					ps.setString(3, investList.get(i).getCorporationCode());
					ps.setString(4, investList.get(i).getName());
					ps.setString(5, investList.get(i).getCorporationClass());
					ps.setString(6, investList.get(i).getInvestTarget());
					ps.setString(7, investList.get(i).getInitialInvestmentDate());
					ps.setString(8, investList.get(i).getCurrentStockCount());
					ps.setString(9, investList.get(i).getCurrentStockShareRatio());
					ps.setString(10, investList.get(i).getCurrentStockEvaluationValue());
					ps.setString(11, investList.get(i).getRecentStockAmountOfChange());
					ps.setString(12, investList.get(i).getRecentAcquisitionAmount());
					ps.setString(13, investList.get(i).getRecentEvaluationGainsAndLosses());
				}

				@Override
				public int getBatchSize() {
					return investList.size();
				}
			});
		investList.clear();
		batchCount++;
		return batchCount;
	}
}
