package com.mont.decor.service.s3;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {
	public String uploadFile(MultipartFile file);
}
