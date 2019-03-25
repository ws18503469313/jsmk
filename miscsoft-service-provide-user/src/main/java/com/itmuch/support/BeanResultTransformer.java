package com.itmuch.support;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.PropertyValue;

public class BeanResultTransformer implements ResultTransformer {
	private static final long serialVersionUID = 1L;
	private final Class<?> resultClass;
	private PropertyAccessor propertyAccessor;

	public BeanResultTransformer(Class<?> resultClass) {
		if (resultClass == null)
			throw new IllegalArgumentException("resultClass cannot be null");
		this.resultClass = resultClass;
	}

	public Object transformTuple(Object[] tuple, String[] aliases) {
		Object result;
		try {
			result = this.resultClass.newInstance();
			this.propertyAccessor = PropertyAccessorFactory.forBeanPropertyAccess(result);

			for (int i = 0; i < aliases.length; i++)
				this.propertyAccessor.setPropertyValue(new PropertyValue(aliases[i], tuple[i]));
		} catch (InstantiationException e) {
			throw new HibernateException("Could not instantiate resultclass: " + this.resultClass.getName());
		} catch (IllegalAccessException e) {
			throw new HibernateException("Could not instantiate resultclass: " + this.resultClass.getName());
		}
		
		return result;
	}

	public List transformList(List collection) {
		return collection;
	}
}