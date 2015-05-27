package com.jason.database;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.log4j.Logger;

public class DBCPPoolManager {
	private static DBCPPoolManager poolManager=null;
	private ObjectPool connectionPool;
	private Logger log = Logger.getLogger(getClass().getName());;
	
	private DataSource ds;
	
	private String
	driver="",                    //驱动 
	url = "",                     //URL   
    Name="",                      //用户
    Password="";                  //密码   
	
	boolean logAbandoned=true;                            //是否在自动回收超时连接的时�?打印连接的超时错�?
    boolean removeAbandoned=true;                         //是否自动回收超时连接
	
    int initialSize=0;                                    //初始化连�?
    int maxIdle=0;                                        //�?��空闲连接
    int minIdle=0;                                        //�?��空闲连接 
    int maxActive=0;                                      //�?��连接数量
    int removeAbandonedTimeout=0;                         //超时时间(以秒数为单位)
    int maxWait=0;                                        //超时等待时间以毫秒为单位 6000毫秒/1000等于60�?
    
	public DBCPPoolManager() {
	}
	
	public void createService(){
		log.info("数据库连接池组件create成功...");
	}
	
	public void startService(){
		if(!loadProperties()){
			log.info("start service failed!");
		}
		try {
			Class.forName(driver);
			connectionPool = new GenericObjectPool(null,     //factory
												  maxActive, //�?��活动连接:连接池在同一时间能够分配的最大活动连接的数量, 如果设置为非正数则表示不限制
							                       (byte)1,  //如何处理失败的connwhenExhaustedAction 0 = fail, 1 = block, 2 = grow
							                       maxWait,  //�?��等待时间:当没有可用连接时,连接池等待连接被归还的最大时�?以毫秒计�?,超过时间则抛出异�?如果设置�?1表示无限等待
							                       maxIdle,  //�?��空闲连接:连接池中容许保持空闲状�?的最大连接数�?超过的空闲连接将被释�?如果设置为负数表示不限制 
							                       minIdle,  //�?��空闲连接:连接池中容许保持空闲状�?的最小连接数�?低于这个数量将创建新的连�?如果设置�?则不创建
							                       true,     //testOnBorrow
							                       true,     //testOnReturn
							                       1000,     //timeBetweenEvictionRunsMillis 在空闲连接回收器线程运行期间休眠的时间�?,以毫秒为单位. 如果设置为非正数,则不运行空闲连接回收器线�?
							                       5,        //numTestsPerEvictionRun	在每次空闲连接回收器线程(如果�?运行时检查的连接数量
							                       5000,     //minEvictableIdleTimeMillis 连接在池中保持空闲�?不被空闲连接回收器线�?如果�?回收的最小时间�?，单位毫�?
							                       true      //testWhileIdle
							                       );
			ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(url,Name,Password);
			new PoolableConnectionFactory(connectionFactory,connectionPool,
            		null,"select count(*) from dual",false,true);
            ds = new PoolingDataSource(connectionPool);
    		log.info("数据库连接池组件start成功...");
//    		List l=this.queryReturnList("SELECT SYS_ACCOUNT.ACCOUNT_ID," +
//    				"SYS_ACCOUNT.ACCOUNT_NAME,SYS_ACCOUNT.ACCOUNT_STATUS," +
//    				"SYS_ACCOUNT.ACCOUNT_INV_DATE,PM_T_ORGN.ORGNNAME FROM SYS_ACCOUNT ," +
//    				" PM_T_USERINFO,PM_T_ORGN WHERE SYS_ACCOUNT.ACCOUNT_ID=PM_T_USERINFO.ACCOUNT_ID" +
//    				" AND PM_T_USERINFO.ORGNID=PM_T_ORGN.ORGNID");
    		
    		

    		
    		//System.out.println("111");
		} catch (Exception e) {
			e.printStackTrace();
			log.info("启动数据库连接池组件失败：[" + e.getMessage() + "]");
		}
	}
	
	public void stopService(){
		try {
			connectionPool.clear();
		} catch (UnsupportedOperationException e) {
			log.info("停止数据库连接池组件失败：[" + e.getMessage() + "]");
		} catch (Exception e) {
			log.info("停止数据库连接池组件失败：[" + e.getMessage() + "]");
		}
	}
	
	public void destroyService(){
		connectionPool = null;
	}
	
	public  boolean loadProperties(){   
        try {
        	
        	java.io.InputStream stream=Thread.currentThread().getContextClassLoader().getResourceAsStream("jdbc.properties");
        			//new FileInputStream(ServerConfigLocator.locate().getServerConfigURL().getPath()+super.getConfigurationURL().replaceAll("resource:", ""));
            java.util.Properties props = new java.util.Properties();   
            props.load(stream);
            driver = props.getProperty("jdbc.driverClassName");        
            url = props.getProperty("jdbc.url");     
            Name = props.getProperty("jdbc.username");
            Password = props.getProperty("jdbc.password");
   
            logAbandoned = (Boolean.valueOf(props.getProperty("logAbandoned","true"))).booleanValue();
            removeAbandoned = (Boolean.valueOf(props.getProperty("removeAbandoned","true"))).booleanValue();            //超时时间(以秒数为单位)
            
            initialSize = Integer.parseInt(props.getProperty("initialSize","30"));
            maxIdle = Integer.parseInt(props.getProperty("maxIdle","20"));
            minIdle = Integer.parseInt(props.getProperty("minIdle","5"));
            maxActive = Integer.parseInt(props.getProperty("maxActive","50"));
            removeAbandonedTimeout = Integer.parseInt(props.getProperty("removeAbandonedTimeout","10"));
            maxWait = Integer.parseInt(props.getProperty("maxWait","2000"));
    
        } catch (FileNotFoundException e) {   
            System.out.println("读取配置文件异常");
            log.debug("读取配置文件异常");
            e.printStackTrace();
        } catch(IOException ie){   
            System.out.println("读取配置文件时IO异常"); 
            log.debug("读取配置文件时IO异常");
        }
        return true;
    }
	
	public Connection getConnection() throws SQLException {
		return ds.getConnection();
	}
	
	public void closeConnection(Connection conn) throws SQLException {
		conn.close();
	}
	
	public void close() throws SQLException {
		try{
            connectionPool.close();
        }catch(Exception e){
            throw new SQLException(e.getMessage());
        }
	}
	/*
	public List query(String sql){
    	//List<Map> ret=new ArrayList<Map>();
    	Connection conn = null;
    	Statement statement=null;
    	ResultSet rs=null;
    	List ret = new ArrayList();
    	try {
              conn = this.getConnection(); 
             if(conn != null){
                  statement = conn.createStatement();   
                  rs = statement.executeQuery(sql);
                  while(rs.next()){
                 	 //Map m=new HashMap();
                      for(int i=1;i<=c;i++){
                     	 if("java.sql.Timestamp".equals(rs.getMetaData().getColumnClassName(i))){
      						 // 日期类型字段�?��殊处理，以便能够提取日期中的时分�?
                     		 m.put(rs.getMetaData().getColumnName(i),rs.getTimestamp(i));
      					}else{
      						m.put(rs.getMetaData().getColumnName(i),rs.getObject(i));
      					}
                     	 
                      
                         // System.out.print(rs.getObject(i));   
                      	//System.out.println("sss"+rs.getMetaData().getColumnTypeName(i));
                      }
                      ret.add(m);
                      //System.out.println(ret);
                 }
                 //int c = rs.getMetaData().getColumnCount();   
                  
                 rs.close();
                 statement.close();
             }   
             
             this.closeConnection(conn);   
         } catch (Exception e) {
        	 e.printStackTrace();
        	 try {
				rs.close();
				 statement.close();
				 this.closeConnection(conn);   
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
             e.printStackTrace();   
         }
    	return ret;
    }
	*/
	
	/**
     * 查询结果是List<Map>   
     * @param sql
     * @return
     */
    public  List<Map> queryReturnList(String sql){
    	List<Map> ret=new ArrayList<Map>();
    	Connection conn = null;
    	Statement statement=null;
    	ResultSet rs=null;
    	 try {
              conn = this.getConnection();
              //System.out.println(conn);
             if(conn != null){
                  statement = conn.createStatement();   
                  rs = statement.executeQuery(sql);   
                 int c = rs.getMetaData().getColumnCount();   
                 while(rs.next()){
                	 Map m=new HashMap();
                     for(int i=1;i<=c;i++){
                    	 if("java.sql.Timestamp".equals(rs.getMetaData().getColumnClassName(i))){
     						 // 日期类型字段�?��殊处理，以便能够提取日期中的时分�?
                    		 m.put(rs.getMetaData().getColumnName(i),rs.getTimestamp(i));
     					}else{
     						m.put(rs.getMetaData().getColumnName(i),rs.getObject(i));
     					}
                    	 
                     
                        // System.out.print(rs.getObject(i));   
                     	//System.out.println("sss"+rs.getMetaData().getColumnTypeName(i));
                     }
                     ret.add(m);
                     //System.out.println(ret);
                 } 
                 rs.close();
                 statement.close();
             }   
             
             this.closeConnection(conn);   
         } catch (Exception e) {
        	 e.printStackTrace();
        	 try {
				rs.close();
				 statement.close();
				 this.closeConnection(conn);   
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
             e.printStackTrace();   
         }
    	return ret;
    }
    
    /**
     * 执行删除或更新操�?
     */
    public  boolean excuteSql(String sql){
    	Connection conn = null;
    	Statement statement=null;
    	try{
    		   conn = this.getConnection();   
    		   conn.setAutoCommit(false);
               if(conn != null){ 
            	   statement = conn.createStatement();   
            	   statement.execute(sql);
               }
               conn.commit();
               statement.close();
               this.closeConnection(conn);
    	}catch(Exception e){
    		 e.printStackTrace();
    		 try {
    			 conn.rollback();
				statement.close();
				 this.closeConnection(conn);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            return false;
    	}
    	return true;
    }
    
    public  int[] excuteBatchSql(List<String> sqlList){
    	Connection conn = null;
    	Statement statement=null;
    	int[] iArr=null;
    	try{
    		   conn = this.getConnection(); 
    		   conn.setAutoCommit(false);
               if(conn != null){ 
            	   statement = conn.createStatement();   
            	   if(sqlList!=null&&sqlList.size()>0){
            		   for(String sqlStr:sqlList){
            			   statement.addBatch(sqlStr);
            		   }
            		   iArr= statement.executeBatch();
            	   }
            	   
               }
               conn.commit();
               statement.close();
               this.closeConnection(conn);
               return iArr;
    	}catch(Exception e){
    		 e.printStackTrace();
    		 try {
    			 conn.rollback();
				statement.close();
				 this.closeConnection(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

            
			 return iArr;
    	}

    }
    
	synchronized public DBCPPoolManager getPoolManager(String tconfigFilePath) throws Exception{
		if (poolManager==null){
			poolManager=new DBCPPoolManager();
			poolManager.startService();
		}
		return poolManager;
	}
	
	

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public boolean isLogAbandoned() {
		return logAbandoned;
	}

	public void setLogAbandoned(boolean logAbandoned) {
		this.logAbandoned = logAbandoned;
	}

	public boolean isRemoveAbandoned() {
		return removeAbandoned;
	}

	public void setRemoveAbandoned(boolean removeAbandoned) {
		this.removeAbandoned = removeAbandoned;
	}

	public int getInitialSize() {
		return initialSize;
	}

	public void setInitialSize(int initialSize) {
		this.initialSize = initialSize;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public int getRemoveAbandonedTimeout() {
		return removeAbandonedTimeout;
	}

	public void setRemoveAbandonedTimeout(int removeAbandonedTimeout) {
		this.removeAbandonedTimeout = removeAbandonedTimeout;
	}

	public int getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}
	
	public static void main(String[] args) throws Exception {
		//FileWriter fw = new FileWriter("c:\\hello.txt");
		poolManager=new DBCPPoolManager();
		poolManager.startService();
		List<Map> l=poolManager.queryReturnList(" select *  from exam ");
		for(Map m:l){
			 System.out.println(m.get("content"));
//			 fw.write(((BigDecimal)m.get("VEHICLEID")).toString()+"\r\n");
		 }
		
//		List<Map> l=poolManager.queryReturnList("select *  from T_BASIS_VEHICLE order by VEHICLEID");
//		 for(Map m:l){
//			// System.out.println(m.get("VEHICLEID"));
//			 fw.write(((BigDecimal)m.get("VEHICLEID")).toString()+"\r\n");
//		 }
//		System.out.print("size="+l.size());
//		
//		
//		
//		fw.flush();
	}
}
