package catchcompany.web.module.post.domain;

import java.time.LocalDateTime;

import catchcompany.web.module.post.controller.dto.PostForm;
import catchcompany.web.module.user.domian.Account;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id")
	private Long id;

	private String title;

	private String content;

	private Long star;

	private LocalDateTime createdTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id") //FK 이름
	private Account account;

	public static Post from(PostForm form) {
		return Post.builder()
			.title(form.title)
			.content(form.content)
			.build();
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}
}
