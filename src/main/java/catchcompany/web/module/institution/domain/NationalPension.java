package catchcompany.web.module.institution.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class NationalPension {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;



}