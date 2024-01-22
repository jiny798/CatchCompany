package catchcompany.web.module.pension.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
public class PensionQuarterStock {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String corporationName;

	private String date;

	private Double beforeShareRatio;

	private Double changeShareRatio;

	private Double currentShareRatio;

	public void setBeforeShareRatio(Double value) {
		this.beforeShareRatio = value;
	}
	public void setChangeShareRatio(Double value) {
		this.changeShareRatio = value;
	}
}
