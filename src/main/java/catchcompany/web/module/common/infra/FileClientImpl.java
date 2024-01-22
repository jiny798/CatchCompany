package catchcompany.web.module.common.infra;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import catchcompany.web.module.common.service.port.FileClient;

@Component
public class FileClientImpl implements FileClient {

	@Value("${file.dir}")
	private String fileDir;

	@Override
	public String save(MultipartFile file) {
		String fullPath = null;
		try {
			if (!file.isEmpty()) {
				fullPath = fileDir + file.getOriginalFilename();
				file.transferTo(new File(fullPath));
			}
		} catch (IOException e) {
			throw new RuntimeException("파일 저장 에러");
		}
		return fullPath;
	}

	@Override
	public boolean remove(String path) {
		File file = new File(path);
		return file.delete();
	}
}
