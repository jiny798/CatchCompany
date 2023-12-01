package catchcompany.web.module.user.domian;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import catchcompany.web.module.post.domain.Post;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_id")
	private Long id;
	@Column(unique = true, nullable = false)
	private String email;
	@Column(unique = true)
	private String nickname;
	private String password;
	private boolean isValid;
	private String emailToken;
	private LocalDateTime lastLoginTime;
	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	private List<Post> postList = new ArrayList<>();

	public static Account createAccount(String email, String nickname, String password) {
		return Account.builder()
			.email(email)
			.nickname(nickname)
			.password(password)
			.build();
	}
}
