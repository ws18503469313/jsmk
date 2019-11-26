package com.itmuch.testBean;

import com.cloud.model.Role;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Main {


    public static void main(String args[]) throws Exception{
        ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        UserBean bean = (UserBean)context.getBean("injectBeanName");
        System.out.print("----所有bean构建完成之后，启动完成，开始使用bean：" + bean.getName());
        Role admin = (Role)bean.getBean("RoleBean");
        System.out.println("----" + admin.getRolename());
        ((ClassPathXmlApplicationContext) context).destroy();
    }

}
