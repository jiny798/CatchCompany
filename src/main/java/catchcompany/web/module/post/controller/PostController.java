package catchcompany.web.module.post.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import catchcompany.web.module.post.controller.dto.PostForm;
import catchcompany.web.module.post.service.PostService;
import catchcompany.web.module.post.controller.dto.PostInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.nhncorp.lucy.security.xss.XssFilter;

@Slf4j
@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
	private final PostService postService;

	@GetMapping(value = "/add")
	public String getPostForm(Model model) {
		model.addAttribute("postForm", new PostForm());
		return "post/postForm";
	}

	@GetMapping
	public String getPosts(Model model, @PageableDefault(size = 10) Pageable pageable) {
		Page<PostInfo> page = postService.getPosts(pageable);
		model.addAttribute("page", page);
		return "post/list";
	}

	@GetMapping("/{id}")
	public String getPost(Model model, @PathVariable("id") Long postId) {
		PostInfo postInfo = postService.getPost(postId);
		model.addAttribute("post",postInfo);
		return "post/details";
	}

	@PostMapping
	public String save(Model model, @ModelAttribute("postForm") PostForm form) {
		XssFilter filter = XssFilter.getInstance("lucy-xss-superset-sax.xml");
		form = new PostForm(filter.doFilter(form.title),filter.doFilter(form.content));
		postService.save(form);
		return "redirect:/posts";
	}

}
