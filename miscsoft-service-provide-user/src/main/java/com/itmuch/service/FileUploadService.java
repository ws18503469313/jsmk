package com.itmuch.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.itmuch.exception.BizException;
import com.itmuch.model.Resource;
import com.itmuch.util.MyWebUtils;

@Component
public class FileUploadService {
	
	@Autowired
	private Resource resource;
	
	public String fileUpload(MultipartFile file) {
		
		String fileName = file.getOriginalFilename();
		
		if (StringUtils.isBlank(fileName) || !fileName.contains(".")) { // 为空或没有后缀
			fileName = UUID.randomUUID().toString();
		} else {
			fileName = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));
		}
		
		File dir = new File(resource.getFilePath());
		if (!dir.exists()) {
			dir.mkdirs();
		}
		if (!dir.exists()) {
			throw new BizException("无法创建图片上传路径" + resource.getFilePath());
		}
		try {
			FileUtils.writeByteArrayToFile(new File(dir, fileName), file.getBytes());
			return MyWebUtils.getCtx() + "/image/" + fileName;
		}catch (IOException e) {
			throw new BizException("保存图片失败:" + e.getMessage(), e);
		}
	}
}
