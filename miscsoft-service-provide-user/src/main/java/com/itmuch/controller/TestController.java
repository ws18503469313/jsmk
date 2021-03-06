package com.itmuch.controller;

import com.itmuch.mapper.RoleMapper;
import com.itmuch.mapper.UserMapper;
import com.cloud.model.Role;
import com.cloud.model.User;
import com.itmuch.service.NoteService;
import com.itmuch.service.RoleService;
import com.cloud.util.JSONResult;
import com.itmuch.util.ImageUtils;
import org.n3r.idworker.Sid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

@RestController
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private Sid sid;

    @Autowired
    private RoleService roleSerive;
    @Autowired
    private NoteService noteService;
    @Autowired
    private UserMapper userMapper;
    /**
     * Transactional
     * roleService中的@Transactional使用的事务机制是require_new 所以处于两个事务,两个事务不相关
     * 各自事务控制各自的业务,
     * 但是前面的业务如果报错了,后面的业务也就无法执行了
     *
     * @param role
     * @return
     */
    @RequestMapping("/addRole")
    @ResponseBody
    @Transactional
    public JSONResult add(Role role) {
        int i = 1/0;
        roleSerive.doSth(role);
        String id = sid.nextShort();
        role.setId(id);
        role.setRolename(role.getRolename() + "=======");
        roleMapper.insert(role);

        return JSONResult.ok();
    }

    @RequestMapping("/addVisit")
    @ResponseBody
    @Transactional
    public JSONResult test() {
        log.info("Thread name: {}", Thread.currentThread().getName());
        noteService.test();
        return JSONResult.ok();
    }
    @RequestMapping("/findById/{id}")
    public User findById(@PathVariable String id)  throws Exception {
        log.info("getbyid=--------------------------{}", id);
        return userMapper.selectByPrimaryKey(id);

    }
    @RequestMapping("/framework/{pro}")
    public void getFramework(@PathVariable String pro, HttpServletResponse resp)  throws Exception {
        FileInputStream fis = null;
        OutputStream outStream = null;
        try{
            File file = new File(this.getClass().getResource("/" + pro + ".png").getPath());
//            resp.addHeader("Content-Disposition", "attachment;" + pro + ".png" );
            resp.setContentType("image/png");
            outStream = resp.getOutputStream();// 得到向客户端输出二进制数据的对象
            fis = new FileInputStream(file); // 以byte流的方式打开文件
            // 读数据
            byte data[] = new byte[1000];
            while (fis.read(data) > 0) {
                outStream.write(data);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            fis.close();
            outStream.close();
        }
    }

    @RequestMapping("/resume")
    public void resume(HttpServletResponse resp)  throws Exception {
        FileInputStream fis = null;
        OutputStream outStream = null;
        try{
            File file = new File(this.getClass().getResource("/resume.doc").getPath());
            resp.addHeader("Content-Disposition", "attachment;filename=" + new String("个人简历-王帅.doc".getBytes(), "ISO-8859-1") );
            outStream = resp.getOutputStream();// 得到向客户端输出二进制数据的对象
            fis = new FileInputStream(file); // 以byte流的方式打开文件
            // 读数据
            byte data[] = new byte[1000];
            while (fis.read(data) > 0) {
                outStream.write(data);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            fis.close();
            outStream.close();
        }
    }

}
