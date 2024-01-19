package catchcompany.web.module.common.service.port;

import org.springframework.web.multipart.MultipartFile;

public interface FileClient {
	public String save(MultipartFile multipartFile);
}
