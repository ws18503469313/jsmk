## springboot_test

各种整合

updateByPrimaryKeySelective只是根据主键更新不为空的字段

整合 shiro 
    shiroConfigirution 配置shiro的用户自定义实现 UserRealm

七.Mybatis
	Ⅰ.独立使用
		1.首先要配置 Mybatis
			见 附十四.
		2.编写mapper
		3.测试
	Ⅱ.spring_with_mybatis					| --> FactoryBean
		1.spring 提供了 SqlSessionFactoryBean | --> ApplicationEvent 来配置mybatis
											| --> InitializingBean 
			InitializingBean.afterPropertiesSet()
				构建 DefaultSqlSessionFactory, SpringManagedTransactionFactory  这个过程spring 还添加了以spring的方式来注入属性的方式,并兼容了mybatis自己的解析方式.
			FactoryBean.getObject() 和 InitializingBean 功能一样
			创建 SqlSessionFactory 的时候,会根据 配置的 mapperLocation 去扫描 mapper, 然后将一系列 sql 转换为 MappedStatement
		2.spring 提供了 MapperFactoryBean, | --> InitializingBean initDao
										 | --> FactoryBean
			 InitializingBean 
			 	SqlSession(自动注入)的验证, 然后将 类型-配置文件注册到 knownMappers中,然后再 FactoryBean.getObject 的 时候,通过 knownMappers获取配置 通过jdk MapperProxy 动态代理来创建代理
	 	3.使用 MapperFactoryBean 需要手动一个个的去注册mapper, 拉低效率,
	 	因此spring 还提供了 MapperScannerConfigurer 来通过扫描的方式自动注册 mapperBean
	 		 						| --> InitializingBean 			校验 配置的 mapperLocation
			MapperScannerConfigurer | --> ApplicationContextAware 	注入 AopContext
	 								| --> BeanNameAware				注入beanName
	 								| --> BeanDefinitionRegistryPostProcessor.postProcessBeanDefinitionRegistry()

				1.spring 在主流程中, 会根据一定顺序 (见二.ApplicationContext) 激活(invokeBeanFactoryPostProcessors)所有注册的 BeanDefinitionRegistryPostProcessor --> BeanFactoryPostProcessor,
				执行他们的 postProcessBeanDefinitionRegistry(), postProcessBeanFactory();
				2.这时候 MapperScannerConfigurer 会进行 mapper 的扫描配置注册
					1. 根据配置注册过滤器,在后期扫描到java 类的时候进行过滤
					2.把配置文件中传入的路径进行分割,在分别进行扫描 (PathMatchingResourcePatternResolver)
						这时候会对 basePackage 进行包分隔符转换 "\"-->" "/", 然后进行递归扫描, 包下所有.class 文件("**/*.class"),然后进行验证, 
						区分: vfs类型, bundle类型, jar包类型, 
					3.把扫描到的 mapper 封装为 ScannedGenericBeanDefinition, 然后注入resource...,最终封装为 BeanDefinitionHolder,
					4.创建的时候,通过aop 把 sqlSessionTemplate 封装到 bean中	




			AntPathMatcher	"""路径匹配器"""
				match(String, String)
				1.先判断二者的开头是否一致
				2.把 patten --> token
					把 patten 用 "/" 分割, 然后缓存起来, 返回String[]
	Ⅲ. myatis 运作:

		1. 通过 MapperProxy 来代理bean, 从代理中获取到缓存的 method, 然后传入 sqlSessionTemplate (mapper 内部封装) 和 args 激活 方法
		2. 判断 当前方法是什么类型的方法(增/删/改/查)
			查: 通过 sqlSessionProxy(DefaultSqlSession) 来实现方法
				检查 mehtod  的返回类型: void Many, Map, Curstor, 对象类型
					对象类型: 将 args ==> sqlCommondParams 调用 selectOne --> selectList 然后判断结果集数量, 0 == null, 1== obj, >1 throw TooManyResultsException()
							通过 SqlSessionInterceptor 来创建代理,实现增强事务,
								1.创建会话: 开启事务, 将事务交给 SpringManagedTransaction 来管理, 根据配置的 executorType (default simple) 来创建 sql Executor, 如果允许缓存 便封装为CacheedExecutor,然后添加 interceptor(org.apache.ibatis.plugin.Interceptor)
								2.激活方法	通过方法全名作为id,在缓存好的 mappedStatements 中找到 MappedStatement
								3.使用 executor 来执行 查询
									首先在 创建cacheKey (date-method-sql-params-bean), 在 MappedStatement 的缓存中查询, 如果有则验证缓存是否有效, 然后直接解析返回, 
									否则 创建handler并把 statement 封装为preparedstatement, 然后获取 connection 来查询, 最终通过 DeafaultResultSetHandler 来解析结果返回, 
                                    "解析result"(根据resultMap)
                                    ResultSetWrapper 里封装了<resultMap> 中的一些 属性: javatype, jdbcType, columnName
                                    如果没有resultHandler则使用默认的 DefaultResultHandler 来进行解析,并对结果进行封装
                                        如果是嵌套的结果类型 (nested:嵌套的), 则需要对resultHandler类型进行检查: configuration.isSafeResultHandlerEnabled() && !mappedStatement.isResultOrdered()
                                        循环对resultSet进行解析: context.isStop = false, rowBonds < limt(Integer.MAX_VALUE)
                                            根据resultMap中的colums 来创建cachekey: '88911490:-850141472:com.itmuch.mapper.SysRoleMapper.roleAccessMap:id:1'
                                            然后根据这个 cacheKey 从解析的结果集中获取 已经解析的obj, 然后根据这个已经解析的obj去解析本行, 解析结束后,如果 obj != null 则 存储起来
                                                解析的时候,如果 传来的 obj 为 null, 则会通过反射来创建一个 接收对象, 所以 封装对象的 集合 对象必须在创建的时候,进行初始化.
                                                所以这个rowKey 就是能否对 resultMap 中的 collection 进行封装解析的关键, 它的创建规则需要根据 resultMap 中的 column==> id 来进行
                                    "所以得出结论==> 对于collection类型的resultMap, 必须配置 id"
                                              "===> 这个collection对应的属性,必须进行初始化"

							提交事务
							关闭链接
			增: 


