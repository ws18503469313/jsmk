package com.itmuch.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.itmuch.exception.BizException;
import com.itmuch.model.Resource;
import com.itmuch.util.MyWebUtils;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class ImageService {

	private static int UPLOAD_TYPE_LOCAL = 1;
	private static int UPLOAD_TYPE_CDN = 2;

	@Autowired
	private Resource resource;

	protected String saveFileAndReturnFileName(HttpServletRequest request, String formInputName) throws IOException {
		MultipartHttpServletRequest mhs = (MultipartHttpServletRequest) request;
		MultipartFile mf = mhs.getFile(formInputName);
		String fileName = mf.getOriginalFilename();
		if (mf != null && !mf.isEmpty()) {
			return this.saveImage(fileName, mf.getBytes());
		}
		return fileName;
	}

	/**
	 * 上传图片并返回完整访问路径
	 * 
	 * @return
	 */
	public String saveImage(String fileName, byte[] data) {
		if (resource.getUploadType() == UPLOAD_TYPE_LOCAL) {
			if (StringUtils.isBlank(fileName) || !fileName.contains(".")) { // 为空或没有后缀
				fileName = UUID.randomUUID().toString();
			} else {
				fileName = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));
			}

			File dir = new File(resource.getImagePath());
			if (!dir.exists()) {
				dir.mkdirs();
			}
			if (!dir.exists()) {
				throw new BizException("无法创建图片上传路径" + resource.getImagePath());
			}
			try {
				FileUtils.writeByteArrayToFile(new File(dir, fileName), data);
				return MyWebUtils.getCtx() + "/image/" + fileName;
			} catch (IOException e) {
				throw new BizException("保存图片失败:" + e.getMessage(), e);
			}
		} else if (resource.getUploadType() == UPLOAD_TYPE_CDN) {
//	    		return qiniuService.upload(data, fileName);
			throw new BizException("请实现逻辑");
		} else {
			throw new BizException("图片上传配置错误");
		}
	}

	public static String getOrCreateSmallImage(String path, int width) throws IOException {
		File existedSmallFile = new File(getSmallFileName(path, width));
		if (existedSmallFile.exists()) {
			return existedSmallFile.getPath();
		}

		File file = new File(path);
		if (!file.exists()) {
			return null;
		}
		// 需要转换的文件为桌面上的1.png
		Thumbnails.of(file).width(width) // 生成图片的格式为png
				// .outputQuality(0.8f) //生成质量为80%
				// .scale(0.5f) //缩小50%
				// 输出到桌面5文件
				// .toFile("/Users/kevin/Documents/tmp_200");
				.toFile(existedSmallFile);
		return existedSmallFile.getPath();
	}

	public static String getSmallFileName(String path, int width) {
		String suffix = path.substring(path.lastIndexOf("."));
		path = path.substring(0, path.lastIndexOf(".")) + "_" + width + suffix;
		return path;
	}

}
