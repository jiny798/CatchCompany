package catchcompany.web.module.post.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import catchcompany.web.module.post.controller.dto.PostForm;
import catchcompany.web.module.post.domain.Post;
import catchcompany.web.module.post.controller.dto.PostInfo;
import catchcompany.web.module.post.service.port.PostRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
	private final PostRepository postRepository;

	public Long save(PostForm form) {
		return postRepository.save(Post.from(form)).getId();
	}

	public Page<PostInfo> getPosts(Pageable pageable) {
		Page<PostInfo> posts = postRepository.findAll(pageable)
			.map(post -> new PostInfo(post.getId(), post.getTitle(), post.getContent()));
		return posts;
	}

	public PostInfo getPost(Long postId) {
		Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("해당 리소스를 찾을 수 없습니다"));
		PostInfo postInfo = new PostInfo(post.getId(), post.getTitle(), post.getContent());
		return postInfo;
	}

}
