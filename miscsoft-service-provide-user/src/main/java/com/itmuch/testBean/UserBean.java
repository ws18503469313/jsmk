package com.itmuch.testBean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class UserBean implements BeanNameAware, BeanFactoryAware, InitializingBean, DisposableBean, ApplicationContextAware {


    private String name;

    private Integer age;

    private DefaultListableBeanFactory beanFactory;

    public UserBean() {
        System.out.println();
        System.out.println("=========================");
        System.out.println("implements BeanNameAware, BeanFactoryAware, InitializingBean, DisposableBean");
        System.out.println("=========================");
        System.out.println("----none arg constructor, 一个bean的生命周期从它的无参构造开始");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("----set beanFactory: ****" + beanFactory.toString()+  "**** 把bean 工厂设置到这个bean上, beanFactory: " + beanFactory.getClass().getName()  );

        this.beanFactory =  (DefaultListableBeanFactory)beanFactory;
    }

    @Override
    public void setBeanName(String s) {
        System.out.println("----set bean name: " + s + " ，然后把xml中配置的bean的name设置给bean");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("----InitializingBean method， 这时候，xml的属性都设置好了，然后开始执行自定义的 对 bean的操作");
    }


    @Override
    public void destroy() throws Exception {
        System.out.println("----destory");
    }
    public void myinit(){
        System.out.println("----afterPropertiesSet， myinit， 两个init 方法来实现 对bean 的init");
    }

    public void mydestroy(){
        System.out.println("----mydestory");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println("----userbean's setName(property): " + name +" ，然后开始给把xml中的属性赋给bean");
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public Object getBean(String name) {
        return beanFactory.getBean(name );
    }
}
