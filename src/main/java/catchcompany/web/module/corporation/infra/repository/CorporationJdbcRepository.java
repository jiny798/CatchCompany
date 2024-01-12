package catchcompany.web.module.corporation.infra.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import catchcompany.web.module.company.infra.dto.CompanyDataDto;
import catchcompany.web.module.corporation.domain.Corporation;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CorporationJdbcRepository {

	private final JdbcTemplate jdbcTemplate;

	private int batchSize = 10000;

	public void saveAll(List<Corporation> corporations) {
		int batchCount = 0;
		List<Corporation> subCorporationList = new ArrayList<>();
		for (int i = 0; i < corporations.size(); i++) {
			subCorporationList.add(corporations.get(i));
			if ((i + 1) % batchSize == 0) {
				batchCount = batchInsert(batchCount, subCorporationList);
			}
		}
		if (!subCorporationList.isEmpty()) {
			batchCount = batchInsert(batchCount, subCorporationList);
		}
	}

	private int batchInsert(int batchCount, List<Corporation> corporations) {
		jdbcTemplate.batchUpdate("INSERT INTO corporation (`corporation_code`,"
				+ " `corporation_name`,"
				+ " `stock_code`) VALUES (?, ?, ?)",
			new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setString(1, corporations.get(i).getCorporationCode());
					ps.setString(2, corporations.get(i).getName());
					ps.setString(3, corporations.get(i).getStockCode());
				}

				@Override
				public int getBatchSize() {
					return corporations.size();
				}
			});
		corporations.clear();
		batchCount++;
		return batchCount;
	}
}
