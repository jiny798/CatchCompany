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
@Entity @Getter
public class PensionYearStock {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String corporationName;

	private Long evaluation; // (억원)

	private Double beforeShareInAsset;

	private Double changeShareInAsset;

	private Double currentShareInAsset;

	private String shareRatio;

	private int year;
}
