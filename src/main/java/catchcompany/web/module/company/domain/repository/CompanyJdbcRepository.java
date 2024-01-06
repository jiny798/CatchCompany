package catchcompany.web.module.company.domain.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import catchcompany.web.module.company.domain.Company;
import catchcompany.web.module.company.infrastructure.dto.CompanyDataDto;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CompanyJdbcRepository {
	private final JdbcTemplate jdbcTemplate;
	private int batchSize = 10000;

	public void saveAll(List<CompanyDataDto> companies) {
		int batchCount = 0;
		List<CompanyDataDto> subCompanyList = new ArrayList<>();
		for (int i = 0; i < companies.size(); i++) {
			subCompanyList.add(companies.get(i));
			if ((i + 1) % batchSize == 0) {
				batchCount = batchInsert(batchCount, subCompanyList);
			}
		}
		if (!subCompanyList.isEmpty()) {
			batchCount = batchInsert(batchCount, subCompanyList);
		}
	}

	private int batchInsert(int batchCount, List<CompanyDataDto> subCompanyList) {
		jdbcTemplate.batchUpdate("INSERT INTO company (`business_type`,"
				+ " `company_registration_code`,"
				+ " `name`,"
				+ " `street_name_address`,"
				+ " `street_number_address`,"
				+ " `date`,"
				+ " `exit_member_count`,"
				+ " `member_count`,"
				+ " `new_member_count`"
				+ ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
			new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setString(1, subCompanyList.get(i).getBusinessType());
					ps.setString(2, subCompanyList.get(i).getCompanyRegistrationCode());
					ps.setString(3, subCompanyList.get(i).getName());
					ps.setString(4, subCompanyList.get(i).getStreetNameAddress());
					ps.setString(5, subCompanyList.get(i).getStreetNumberAddress());
					ps.setString(6, subCompanyList.get(i).getDate());
					ps.setString(7, subCompanyList.get(i).getExitMemberCount());
					ps.setString(8, subCompanyList.get(i).getMemberCount());
					ps.setString(9, subCompanyList.get(i).getNewMemberCount());
				}

				@Override
				public int getBatchSize() {
					return subCompanyList.size();
				}
			});
		subCompanyList.clear();
		batchCount++;
		return batchCount;
	}
}
