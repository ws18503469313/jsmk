//package com.itmuch.dao;
//
//
//import java.io.Serializable;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.lang.reflect.ParameterizedType;
//import java.math.BigDecimal;
//import java.sql.Timestamp;
//import java.util.Collection;
//import java.util.Date;
//import java.util.List;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.servlet.http.HttpServletRequest;
//import javax.sql.DataSource;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.hibernate.Session;
//import org.hibernate.engine.spi.SessionFactoryImplementor;
//import org.hibernate.internal.SessionFactoryImpl;
//import org.hibernate.query.NativeQuery;
//import org.hibernate.query.Query;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.itmuch.model.BaseModel;
//import com.itmuch.model.Resource;
//import com.itmuch.support.BeanResultTransformer;
//import com.itmuch.support.Page;
//import com.itmuch.support.SystemContext;
//
//@Transactional
//public class BaseDAO<T> implements Serializable {
//	protected static Logger logger = LoggerFactory.getLogger(BaseDAO.class);
//
//	@Autowired
//	private DataSource dataSource;
//	
//	
//	@PersistenceContext
//	private EntityManager entityManager;
//	protected Class<T> clz;
//
//	public BaseDAO() {
//		this.clz = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
//	}
//
//	private Session getSession() {
//		return (Session) this.entityManager.unwrap(Session.class);
//	}
//
////	public T findOne(String hql, Object[] paras) {
////		Page page = new Page();
////		page.setPage(Integer.valueOf(1));
////		page.setPageSize(Integer.valueOf(1));
////		List list = find(hql, paras, page);
////		if (CollectionUtils.isEmpty(list)) {
////			return null;
////		}
////		return (T) list.get(0);
////	}
//
//	public List<T> find(String hql, Object[] paras, Integer start, Integer limit) {
//		Query query = getSession().createQuery(hql);
//		if (logger.isDebugEnabled()) {
//			String str = "";
//			str = str + "hql: " + hql;
//			for (int i = 0; i < paras.length; i++) {
//				str = str + ", 参数 " + i + ": " + paras[i];
//			}
//			logger.debug(str);
//		}
//		if (paras != null) {
//			setQueryParameters(paras, query);
//		}
//		if ((start != null) && (start.intValue() >= 0) && (limit != null) && (limit.intValue() >= 0)) {
//			query.setFirstResult(start.intValue());
//			query.setMaxResults(limit.intValue());
//		}
//		return query.list();
//	}
//
//	public <K> K find2(String hql, Object[] paras, Page page) {
//		Query query = getSession().createQuery(hql);
//
//		if (logger.isDebugEnabled()) {
//			String str = "";
//			str = str + "hql: " + hql;
//			for (int i = 0; i < paras.length; i++) {
//				str = str + ", 参数 " + i + ": " + paras[i];
//			}
//			logger.debug(str);
//		}
//		if (paras != null) {
//			setQueryParameters(paras, query);
//		}
//
//		if (page != null) {
//			StringUtils.isNotBlank(page.getSort());
//
//			int totalCount = queryTotalCount(hql, paras);
//			page.setTotalRecords(Integer.valueOf(totalCount));
//			int totalPages = totalCount % page.getPageSize().intValue() == 0
//					? totalCount / page.getPageSize().intValue()
//					: totalCount / page.getPageSize().intValue() + 1;
//			page.setTotalPages(Integer.valueOf(totalPages));
//			query.setFirstResult((page.getPage().intValue() - 1) * page.getPageSize().intValue());
//			query.setMaxResults(page.getPageSize().intValue());
//		}
//
//		return (K) query.list();
//	}
//
//	public List<T> find(String hql, Object[] paras, Boolean openPaging) {
//		Query query = getSession().createQuery(hql);
//		if (paras == null) {
//			paras = new Object[0];
//		}
//		if (logger.isDebugEnabled()) {
//			String str = "";
//			str = str + "hql: " + hql;
//			for (int i = 0; i < paras.length; i++) {
//				str = str + ", 参数 " + i + ": " + paras[i];
//			}
//			logger.debug(str);
//		}
//		if (paras != null) {
//			setQueryParameters(paras, query);
//		}
//
//		if (openPaging) {
//			Page page = SystemContext.getPage();
////			if(StringUtils.isNotBlank(page.getSort())){
////				if(!hql.contains("order by")) {
////					
////				}
////			}
//			int totalCount = queryTotalCount(hql, paras);
//			page.setTotalRecords(Integer.valueOf(totalCount));
//			int totalPages = totalCount % page.getPageSize().intValue() == 0
//					? totalCount / page.getPageSize().intValue()
//					: totalCount / page.getPageSize().intValue() + 1;
//			page.setTotalPages(Integer.valueOf(totalPages));
//			query.setFirstResult((page.getPage().intValue() - 1) * page.getPageSize().intValue());
//			query.setMaxResults(page.getPageSize().intValue());
//			
//			
//			HttpServletRequest req = SystemContext.getReq();
//			req.setAttribute("page", page);
//		}
//
//		return query.list();
//	}
//
//	public int queryTotalCount(String hql, Object[] paras) {
//		int beginPos = hql.toLowerCase().indexOf("from");
//		String countHql = "select count(*) " + hql.substring(beginPos);
//		Query query = getSession().createQuery(countHql);
//		if (paras != null) {
//			setQueryParameters(paras, query);
//		}
//		return Integer.parseInt(query.uniqueResult().toString());
//	}
//
//	public Long create(T t) {
//		return (Long) getSession().save(t);
//	}
//
//	public T load(Serializable id) {
//		return getSession().load(this.clz, id);
//	}
//
//	public T get(Serializable id) {
//		return getSession().get(this.clz, id);
//	}
//
//	public void update(T t) {
//		getSession().update(t);
//	}
//
//	public void deleteById(Serializable id) {
//		getSession().delete(load(id));
//	}
//
//	public int executeUpdate(String hql, Object[] paras) {
//		Query query = getSession().createQuery(hql);
//		if (paras != null) {
//			setQueryParameters(paras, query);
//		}
//		return query.executeUpdate();
//	}
//
//	public int executeSQLUpdate(String sql, Object[] paras) {
//		NativeQuery sqlQuery = getSession().createNativeQuery(sql);
//		if (paras != null) {
//			setQueryParameters(paras, sqlQuery);
//		}
//		return sqlQuery.executeUpdate();
//	}
//
//	private SessionFactoryImplementor getSessionFactoryImplementor() {
//		SessionFactoryImpl sfi = (SessionFactoryImpl) getSession().getSessionFactory();
//		return sfi;
//	}
//
//	public void deleteAll() {
//		executeUpdate("delete from " + this.clz.getSimpleName(), null);
//	}
//
//	protected void appendManagerSql(StringBuilder hql, List<Object> paras) {
//	}
//
//	protected Object executeSQLQueryUniqueResult(String sql, List<? extends Object> paras) {
//		Query sqlQuery = getSession().createNativeQuery(sql);
//		if (CollectionUtils.isNotEmpty(paras)) {
//			setQueryParameters(paras.toArray(), sqlQuery);
//		}
//		return sqlQuery.uniqueResult();
//	}
//
//	protected List<Object[]> executeSQLQuery(String sql, List<? extends Object> paras) {
//		Query sqlQuery = getSession().createNativeQuery(sql);
//		if (CollectionUtils.isNotEmpty(paras)) {
//			setQueryParameters(paras.toArray(), sqlQuery);
//		}
//
//		return sqlQuery.list();
//	}
//
//	protected List<Object[]> executeSQLQuery(String sql, Object[] paras, Page page) {
//		Query sqlQuery = getSession().createNativeQuery(sql);
//		if ((paras != null) && (paras.length > 0)) {
//			setQueryParameters(paras, sqlQuery);
//		}
//		if (page != null) {
//			StringUtils.isNotBlank(page.getSort());
//
//			String countSql = "select count(*) from (" + sql + ") as aaaaaaaaaaaaaaa";
//			int totalCount = countBySql(countSql, paras);
//			page.setTotalRecords(Integer.valueOf(totalCount));
//			int totalPages = totalCount % page.getPageSize().intValue() == 0
//					? totalCount / page.getPageSize().intValue()
//					: totalCount / page.getPageSize().intValue() + 1;
//			page.setTotalPages(Integer.valueOf(totalPages));
//			sqlQuery.setFirstResult((page.getPage().intValue() - 1) * page.getPageSize().intValue());
//			sqlQuery.setMaxResults(page.getPageSize().intValue());
//		}
//		return sqlQuery.list();
//	}
//
//	private Object toModel(List<String> dbColumnNames, List<Object> dbColumns, Class<? extends Object> class1) {
//		try {
//			Object t = class1.newInstance();
//			for (int i = 0; i < dbColumnNames.size(); i++) {
//				String name = (String) dbColumnNames.get(i);
//				Method m = getSetter(name, class1);
//				Class type = m.getParameterTypes()[0];
//				Object value = dbColumns.get(i);
//				if (value == null) {
//					return null;
//				}
//				if ((type == Long.class) || (type == Long.TYPE))
//					value = Long.valueOf(value.toString());
//				else if ((type == Integer.class) || (type == Integer.TYPE))
//					value = Integer.valueOf(value.toString());
//				else if (type == String.class)
//					value = value.toString();
//				else if (type == BigDecimal.class)
//					value = new BigDecimal(value.toString());
//				else if (type == Date.class)
//					value = (Date) value;
//				else if (type == Timestamp.class)
//					value = (Timestamp) value;
//				else if ((type == Double.class) || (type == Double.TYPE))
//					value = Double.valueOf(value.toString());
//				else if ((type == Boolean.class) || (type == Boolean.TYPE)) {
//					value = Boolean.valueOf(value.toString());
//				}
//				m.invoke(t, new Object[] { value });
//			}
//			return t;
//		} catch (IllegalArgumentException e) {
//			logger.error(e.getMessage(), e);
//			e.printStackTrace();
//		} catch (InstantiationException e) {
//			logger.error(e.getMessage(), e);
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			logger.error(e.getMessage(), e);
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			logger.error(e.getMessage(), e);
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	private Method getSetter(String name, Class<? extends Object> clazz) {
//		Method[] ms = clazz.getDeclaredMethods();
//		for (Method m : ms) {
//			if (("set" + name).equalsIgnoreCase(m.getName())) {
//				return m;
//			}
//		}
//		return null;
//	}
//
//	public int countByHql(String hql, Object[] params) {
//		Query query = getSession().createQuery(hql);
//		if (params != null) {
//			setQueryParameters(params, query);
//		}
//
//		return Integer.parseInt(query.uniqueResult().toString());
//	}
//
//	protected int countBySql(String sql, Object[] paras) {
//		NativeQuery query = getSession().createNativeQuery(sql);
//		setQueryParameters(paras, query);
//
//		return Integer.parseInt(query.uniqueResult().toString());
//	}
//
//	public List<T> findModelBySql(String sql, Object[] paras, Page page) {
//		NativeQuery sqlQuery = getSession().createNativeQuery(sql);
//		setQueryParameters(paras, sqlQuery);
//		sqlQuery.addEntity(this.clz);
//
//		if (page != null) {
//			String countSql = "select count(*) from (" + sql + ") ";
//			int totalCount = countBySql(countSql, paras);
//			page.setTotalRecords(Integer.valueOf(totalCount));
//			int totalPages = totalCount % page.getPageSize().intValue() == 0
//					? totalCount / page.getPageSize().intValue()
//					: totalCount / page.getPageSize().intValue() + 1;
//			page.setTotalPages(Integer.valueOf(totalPages));
//			sqlQuery.setFirstResult((page.getPage().intValue() - 1) * page.getPageSize().intValue());
//			sqlQuery.setMaxResults(page.getPageSize().intValue());
//		}
//
//		return sqlQuery.list();
//	}
//
//	public <K> K findOneModelBySql(String sql, Object[] paras, Class<? extends BaseModel> clzz) {
//		NativeQuery<?> sqlQuery = getSession().createNativeQuery(sql);
//		setQueryParameters(paras, sqlQuery);
//		sqlQuery.addEntity(clzz);
//		sqlQuery.setFirstResult(0);
//		sqlQuery.setMaxResults(1);
//		List list = sqlQuery.list();
//		return CollectionUtils.isEmpty(list) ? null : (K)list.get(0);
//	}
//
//	private void setQueryParameters(Object[] paras, Query query) {
//		if (paras == null) {
//			return;
//		}
//
//		for (int i = 0; i < paras.length; i++)
//			if ((paras[i] instanceof Collection)) {
//				query.setParameterList("list", (Collection) paras[i]);
//			} else if ((query instanceof NativeQuery))
//				query.setParameter(i + 1, paras[i]);
//			else
//				query.setParameter(i, paras[i]);
//	}
//
//	public double sumByHql(String hql, Object[] paras) {
//		Query query = getSession().createQuery(hql);
//		setQueryParameters(paras, query);
//		if (query.uniqueResult() == null) {
//			return 0.0D;
//		}
//		if (StringUtils.isBlank(query.uniqueResult().toString())) {
//			return 0.0D;
//		}
//		return Double.parseDouble(query.uniqueResult().toString());
//	}
//
//	public <K> List<K> findBySql(String sql, Object[] paras, Page page, Class<K> clz) {
//		NativeQuery query = getSession().createNativeQuery(sql);
//		setQueryParameters(paras, query);
//		if (page != null) {
//			String countSql = "select count(*) from (" + sql + ") as xyz ";
//			int totalCount = countBySql(countSql, paras);
//			page.setTotalRecords(Integer.valueOf(totalCount));
//			int totalPages = totalCount % page.getPageSize().intValue() == 0
//					? totalCount / page.getPageSize().intValue()
//					: totalCount / page.getPageSize().intValue() + 1;
//			page.setTotalPages(Integer.valueOf(totalPages));
//			query.setFirstResult((page.getPage().intValue() - 1) * page.getPageSize().intValue());
//			query.setMaxResults(page.getPageSize().intValue());
//		}
//
//		query.setResultTransformer(new BeanResultTransformer(clz));
//		return query.list();
//	}
//}
