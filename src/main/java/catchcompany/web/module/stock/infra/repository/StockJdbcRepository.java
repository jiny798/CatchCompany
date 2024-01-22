package catchcompany.web.module.stock.infra.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import catchcompany.web.module.stock.domain.Stock;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class StockJdbcRepository {
	private final JdbcTemplate jdbcTemplate;
	private int batchSize = 10000;

	public void saveAll(List<Stock> stockList) {
		int batchCount = 0;
		List<Stock> subStockList = new ArrayList<>();
		for (int i = 0; i < stockList.size(); i++) {
			subStockList.add(stockList.get(i));
			if ((i + 1) % batchSize == 0) {
				batchCount = batchInsert(batchCount, subStockList);
			}
		}
		if (!subStockList.isEmpty()) {
			batchCount = batchInsert(batchCount, subStockList);
		}
	}

	private int batchInsert(int batchCount, List<Stock> stockList) {
		jdbcTemplate.batchUpdate("INSERT INTO STOCK ("
				+ " `bas_dt`,"
				+ " `srtn_cd`,"
				+ " `isin_cd`,"
				+ " `itms_nm`,"
				+ " `mrkt_ctg`,"
				+ " `clpr`,"
				+ " `vs`,"
				+ " `flt_rt`,"
				+ " `mkp`,"
				+ " `hipr`,"
				+ " `lopr`,"
				+ " `trqu`,"
				+ " `tr_prc`,"
				+ " `lstg_st_cnt`,"
				+ " `mrkt_tot_amt`,"
				+ " `status`"
				+ ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
			new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setString(1, stockList.get(i).getBasDt());
					ps.setString(2, stockList.get(i).getSrtnCd());
					ps.setString(3, stockList.get(i).getIsinCd());
					ps.setString(4, stockList.get(i).getItmsNm());
					ps.setString(5, stockList.get(i).getMrktCtg());
					ps.setInt(6, stockList.get(i).getClpr());
					ps.setInt(7, stockList.get(i).getVs());
					ps.setDouble(8, stockList.get(i).getFltRt());
					ps.setInt(9, stockList.get(i).getMkp());
					ps.setInt(10, stockList.get(i).getHipr());
					ps.setInt(11, stockList.get(i).getLopr());
					ps.setLong(12, stockList.get(i).getTrqu());
					ps.setLong(13, stockList.get(i).getTrPrc());
					ps.setLong(14, stockList.get(i).getLstgStCnt());
					ps.setLong(15, stockList.get(i).getMrktTotAmt());
					ps.setBoolean(16, stockList.get(i).isStatus());
				}

				@Override
				public int getBatchSize() {
					return stockList.size();
				}
			});
		stockList.clear();
		batchCount++;
		return batchCount;
	}
}
