package catchcompany.web.module.post.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import catchcompany.web.module.post.domain.Post;

public interface PostJpaRepository extends JpaRepository<Post,Long> {
}
