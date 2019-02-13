package com.itmuch.controller.busyness;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;
import com.itmuch.dto.NoteDTO;
import com.itmuch.model.Note;
import com.itmuch.model.NoteDetail;
import com.itmuch.model.Resource;
import com.itmuch.service.FileUploadService;
import com.itmuch.service.NoteService;
import com.itmuch.util.JSONResult;

@Controller
@RequestMapping("/note/")
public class NoteController {
	
	
	private static final Logger log = LoggerFactory.getLogger(NoteController.class);
	@Autowired
	private NoteService noteService;
	
	
	@Autowired
	private Resource resource;
	@Autowired
	private FileUploadService fileUpLoadService;
	
	/**
	 * 进入发帖主页
	 * @return
	 */
	@RequiresRoles(value="manager")
	@RequestMapping("list")
	@ResponseBody
	public JSONResult list(NoteDTO query, @RequestParam(name="page",defaultValue="0") int page) {
		Map<String, Object> result = noteService.list(query, page);
		return JSONResult.ok(result.get("list"),((Page)result.get("page")).getTotal());
	}
	
	/**
	 * 发帖
	 * @param note
	 * @param noteDetails
	 * @param files
	 * @param map
	 * @return
	 */
	@RequiresRoles(value="manager")
	@RequestMapping("add")
	public JSONResult save(	@RequestParam(name="帖子主体")Note note,
							@RequestBody List<NoteDetail> noteDetails,
							@RequestParam(name="文件上传")List<MultipartFile> files,
							ModelMap map) {
		map.put("note", note);
		map.put("noteDetails", noteDetails);
		String result  = null;
		if(StringUtils.isNotBlank(note.getId())) {
			result = noteService.update(note, noteDetails, files);
		}else {
			result = noteService.create(note, noteDetails, files);
		}
		
		return JSONResult.ok(result);
	}
	@RequiresRoles(value="manager")
	@RequestMapping("delete")
	@ResponseBody
	public JSONResult delete(String id) {
		String msg = noteService.delete(id);
		log.info("------------------------"+msg+"--------------------------------");
		return JSONResult.ok();
	}
	
	@RequestMapping("fileupload")
	@ResponseBody
	public JSONResult fileupload(@RequestParam MultipartFile[] files) {
		String result = null;
		for (MultipartFile file : files) {
			result = fileUpLoadService.fileUpload(file);
		}
		return JSONResult.ok(result);
	}
	
}
