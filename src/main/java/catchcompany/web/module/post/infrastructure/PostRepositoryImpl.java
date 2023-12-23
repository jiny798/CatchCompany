package catchcompany.web.module.post.infrastructure;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import catchcompany.web.module.post.domain.Post;
import catchcompany.web.module.post.service.port.PostRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {
	private final PostJpaRepository postJpaRepository;

	@Override
	public Page<Post> findAll(Pageable pageable) {
		return postJpaRepository.findAll(pageable);
	}

	@Override
	public Optional<Post> findById(Long postId) {
		return postJpaRepository.findById(postId);
	}

	@Override
	public Post save(Post post) {
		return postJpaRepository.save(post);
	}
}
