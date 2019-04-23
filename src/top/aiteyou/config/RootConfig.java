package top.aiteyou.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.springframework.web.socket.WebSocketHandler;

import com.sun.org.apache.xml.internal.security.Init;

import redis.clients.jedis.JedisPoolConfig;
import top.aiteyou.websocket.WebSocketPushHandler;

/**	
 * spring Ioc 环境配置
	*@author :tb
	*@version 2018年3月18日 下午3:47:46
*/
/**import*/
@Configuration//标识一个配置类

//定义spring 扫描的包
@ComponentScan(value="top.aiteyou.*",excludeFilters={
		@Filter(type=FilterType.ANNOTATION,value=Service.class)} )
//使用事务驱动管理器
@EnableTransactionManagement
//使用缓存
@EnableCaching 
public class RootConfig implements TransactionManagementConfigurer{
	private DataSource dataSource=null;
	public Logger logger=Logger.getLogger(this.getClass());
	
	
	
	/**
	 * 使用redis作为缓存
	 * @param redisTemplate
	 * @return
	 */
	@Bean(name="cacheManager")
	public CacheManager cacheManager(@Autowired RedisTemplate redisTemplate){
		RedisCacheManager redisCacheManager=new RedisCacheManager(redisTemplate);
		redisCacheManager.setDefaultExpiration(60*2);//2分钟
		return redisCacheManager;
	}
	
	/**
	 * 配置自动扫描，发现 Mybatis Mapper接口 交给ioc管理
	 * @return Mapper 扫描器
	 */
	@Bean
	public MapperScannerConfigurer initMapperScannerConfigurer(){
		MapperScannerConfigurer msc=new MapperScannerConfigurer();
		msc.setBasePackage("top.aiteyou.mapper");
		msc.setSqlSessionFactoryBeanName("sqlSessionFactory");
		msc.setAnnotationClass(Repository.class);
		return msc;
	}
	
	/**
	 * 创建RedisTemplate
	 * @return RedisTemplate
	 */
	@Bean(name="redisTemplate")
	public RedisTemplate initRedisTemplate(){
		//redis连接池配置
		JedisPoolConfig poolConfig=new JedisPoolConfig();
		poolConfig.setMaxIdle(50);//最大空闲数
		poolConfig.setMaxTotal(100);//最大连接数
		poolConfig.setMaxWaitMillis(20000);//最大等待毫秒数
		//创建连接工厂 配置redis服务器信息 -
		JedisConnectionFactory connectionFactory=
				new JedisConnectionFactory(poolConfig);
		RedisSentinelConfiguration clusterConfig;
		// 获取Redis集群配置信息
		//new JedisConnectionFactory((List<String>)(new ArrayList<>()), poolConfig);
		connectionFactory.setHostName("39.105.183.126");//连接主机
		connectionFactory.setPort(6379);//端口
		connectionFactory.setPassword("tangbo1997814");//密码
		
		connectionFactory.afterPropertiesSet();
		
		//定义RedisTemplate 并设置序列化器 java对象通过套接字传输需要序列化
		RedisTemplate redisTemplate=new RedisTemplate();
		redisTemplate.setConnectionFactory(connectionFactory);
		RedisSerializer stringRedisSerilazier=new StringRedisSerializer();
		RedisSerializer jdkRedisSerilazier=new JdkSerializationRedisSerializer();
		redisTemplate.setDefaultSerializer(stringRedisSerilazier);
		redisTemplate.setKeySerializer(stringRedisSerilazier);
		redisTemplate.setValueSerializer(jdkRedisSerilazier);
		redisTemplate.setHashKeySerializer(stringRedisSerilazier);
		redisTemplate.setHashValueSerializer(jdkRedisSerilazier);
		 
		return redisTemplate;
	}
	
	/**
	 * 配置SqlSessionFatoryBean
	 * @return SqlSessionFatoryBean
	 */
	@Bean(name="sqlSessionFactory")
	public SqlSessionFactoryBean initSqlSessionFactory(){
		SqlSessionFactoryBean sqlSessionFactoryBean=new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(initDataSource());
		//配置mybatis									
		Resource resource=new ClassPathResource("mybatis-config.xml");//mybatis的配置文件
		sqlSessionFactoryBean.setConfigLocation(resource);
		return sqlSessionFactoryBean;
	}
	/**
	 * 配置数据库
	 * @return数据库连接池
	 */
	
	@Bean(name="dataSource") //getBean method
	public DataSource initDataSource(){
		if (dataSource !=null) {
			return dataSource;
		}
		Properties properties=new Properties();
			properties.setProperty("driverClassName", "com.mysql.jdbc.Driver");
			properties.setProperty("url", "jdbc:mysql://39.105.183.126:3306/schoolapp");
			properties.setProperty("username", "changzhang");
			properties.setProperty("password", "clearlove");
			properties.setProperty("maxIdle", "20");
			properties.setProperty("maxWait", "30000");
			properties.setProperty("connectionInitSqls","set names utf8mb4;");
		try {
			dataSource=BasicDataSourceFactory.createDataSource(properties);
		} catch (Exception e) {
			logger.error("数据源创建失败", e);
		}
		return dataSource;
		
	}

	/**
	 * 注册注解事务，当@Transaction使用的时候产生数据库事务
	 */
	@Override
	@Bean(name="annotationDrivenTransactionManager")
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		DataSourceTransactionManager transactionManager=
				new DataSourceTransactionManager();
		transactionManager.setDataSource(initDataSource());
		return transactionManager;
	}
}


