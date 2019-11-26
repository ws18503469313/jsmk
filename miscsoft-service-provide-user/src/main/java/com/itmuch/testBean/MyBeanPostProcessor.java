package com.itmuch.testBean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.print("----bean post processor BEFOR: " + bean.getClass().getName());
        System.out.print(" " + bean.toString());
        System.out.println(" " + beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.print("----bean post processor AFTER: " + bean.getClass().getName());
        System.out.print(" " + bean.toString());
        System.out.println(" " + beanName);
        return bean;
    }
}
