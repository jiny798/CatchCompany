package catchcompany.web.module.post.service.port;

import java.util.Optional;

import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import catchcompany.web.module.post.domain.Post;

public interface PostRepository {
	Page<Post> findAll(Pageable pageable);

	Optional<Post> findById(Long postId);

	Post save(Post from);
}
