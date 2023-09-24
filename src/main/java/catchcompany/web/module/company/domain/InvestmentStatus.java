package catchcompany.web.module.company.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class InvestmentStatus {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "rcept_no")
	private String rceptNum;

	@Column(name = "corp_cls")
	private CorpCls corpCls; // 법인구분

	@Column(name = "corp_code")
	private String corpCode; //공시대상회사 고유번호 8자리

	@Column(name = "corp_name")
	private String corpName; // 회사명 - 공시대상회사명

	@Column(name = "inv_prm")
	private String invPrm; // 법인명

	@Column(name = "frst_acqs_de")
	private LocalDate firstAcqsDe; // 최초 취득 일자

	@Column(name = "invstmnt_purps")
	private String InvestMntPurps; // 출자 목적

	@Column(name = "frst_acqs_amount")
	private String firstAcqsAmount; //	최초 취득 금액

	@Column(name = "bsis_blce_qy")
	private String bsisBlceQy; // 기초 잔액 수량

	@Column(name = "bsis_blce_qota_rt")
	private String bsisBlceQotaRt; // 기초 잔액 지분 율

	@Column(name = "bsis_blce_acntbk_amount")
	private String bsisBlceAcntbkAmount; // 기초 잔액 장부 가액

	@Column(name = "incrs_dcrs_acqs_dsps_qy")
	private String incrsDcrsAcqsDspsQy; // 증가 감소 취득 처분 수량

	@Column(name = "incrs_dcrs_acqs_dsps_amount")
	private String incrsDcrsAcqsDspsAmount; // 증가 감소 취득 처분 금액

	@Column(name = "incrs_dcrs_evl_lstmn")
	private String incrsDcrsEvlLstmn; // 증가 감소 평가 손액

	@Column(name = "trmend_blce_qy")
	private String trmendBlceQy; // 기말 잔액 수량

	@Column(name = "trmend_blce_qota_rt")
	private String trmendBlceQotaRt; // 기말 잔액 지분 율

	@Column(name = "trmend_blce_acntbk_amount")
	private String trmendBlceAcntbkAmount; // 기말 잔액 장부 가액

	@Column(name = "recent_bsns_year_fnnr_sttus_tot_assets")
	private String recentBsnsYearFnnrSttusTotAssets; // 최근 사업 연도 재무 현황 총 자산

	@Column(name = "recent_bsns_year_fnnr_sttus_thstrm_ntpf")
	private String recentBsnsYearFnnrSttusThstrmNtpf; // 최근 사업 연도 재무 현황 당기 순이익

}
