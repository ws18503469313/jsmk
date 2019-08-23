package com.itmuch.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itmuch.dto.NoteDTO;
import com.itmuch.mapper.UserMapper;
import com.itmuch.model.Categray;
import com.itmuch.model.Resource;
import com.itmuch.model.User;
import com.itmuch.service.CategrayService;
import com.itmuch.service.FileUploadService;
import com.itmuch.service.ImageService;
import com.itmuch.service.NoteService;
import com.itmuch.service.UserService;
import com.itmuch.util.JSONResult;
import com.itmuch.util.JedisUtil;
import com.itmuch.util.WebParamUtils;

@Controller
@RequestMapping("/index")
public class IndexController extends CoreController{
	private static final Logger log = LoggerFactory.getLogger(IndexController.class);
	@Autowired
	private NoteService noteService;
	@Autowired
	private CategrayService categrayService;
	@Autowired
	private FileUploadService fileUpLoadService;
	@Autowired
	private Resource resource;
	
	/**
	 * 用户默认访问首页
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/")
	public String view(NoteDTO note,  Map<String, Object> map,@RequestParam(name="page",defaultValue="1") int page) {
		//根据用户的条件查出对应的结果
		Map<String, Object> notes = noteService.list(note, page);
		//查出所有分类
		Map<String, Object> cate = categrayService.listCategray(new Categray(), false, 0);
		//开始存放结果
		map.put("note", note);
		map.put("notes", notes.get("list"));
		map.put("categray", cate.get("result"));
		return "view";
	}
	
	/**
	 * 帖子详情
	 * @param id
	 * @return
	 */
	@RequestMapping("/detail")
	public String viewDetail(NoteDTO query, Map<String, Object> map) {
		NoteDTO noteDetail = noteService.getNoteDetail(query, false);
		map.put("note", noteDetail);
		return "noteDetail";
	}
	@Autowired
	private UserService userServicer;
	
	@RequestMapping("addAge")
	@ResponseBody
	public JSONResult testConcurrent() {
		User user = userServicer.beforTransation();
		return JSONResult.ok(user.getAge());
	}

	@RequestMapping("test")
	@ResponseBody
	public JSONResult test(HttpServletResponse response) {
		int i = 1/0;
		return JSONResult.ok();
	}
	/**
	 * 随便写一个文件,这个url没有用
	 * @return
	 */
//	@RequestMapping("/write")
//	@ResponseBody
	public JSONResult write() {
		fileUpLoadService.write();
		return JSONResult.ok();
	}
	
	@ResponseBody
	@RequestMapping(value = "{name}.{suffix}", produces="image/*")
	public void image(HttpServletRequest request, HttpServletResponse response, @PathVariable("name") String name, @PathVariable("suffix") String suffix) throws IOException {
		String imageName = name + "." + suffix;
		imageName = WebParamUtils.getUTF8Param(imageName);
		File file = new File(resource.getImagePath(), imageName);
		if (!file.exists()) {
		    return;
		}
		
		Integer width = getImageWidth(request);
		if (width != null && width > 0) {
		    String path = ImageService.getOrCreateSmallImage(file.getPath(), width);
		    file = new File(path);
		}
		
		response.setContentType("image/jpeg"); // 设置返回的文件类型
		ServletOutputStream outStream = response.getOutputStream();// 得到向客户端输出二进制数据的对象  
        FileInputStream fis = new FileInputStream(file); // 以byte流的方式打开文件  
        // 读数据  
        byte data[] = new byte[1000];  
        while (fis.read(data) > 0) {  
            outStream.write(data);  
        }  
        fis.close();
          
        outStream.write(data); // 输出数据  
        outStream.close();  
		
	}
	
	
	private Integer getImageWidth(HttpServletRequest request) {
        Integer width = ServletRequestUtils.getIntParameter(request, "width", 0);
        return width;
    }
	
	
}
