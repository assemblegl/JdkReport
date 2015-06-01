package gl.database;

import gl.global.Context;
import gl.object.MyObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Mysql extends MyObject {	
	private static final String MYSQLDRIVERNAME="com.mysql.jdbc.Driver";
	private static final String MYSQLURLHead="jdbc:mysql://";
	private static final String MYSQLURLEnding="?useUnicode=false&characterEncoding=";//utf-8
	private static final String URLTimeout="&timeout=";
	
	//×¢Èë±äÁ¿
	private int reconnNum; 	
	private int conntimeout;
	private int sleeptime;
	private String ConnLanCode;
	private String SetNameLanCode;
	private int SBehindMasterLevel;
	private String slavecheckuser;
	private String slavecheckuserpwd;
	
	static{
    	try{   		
    		Class.forName(MYSQLDRIVERNAME);
    	}catch(ClassNotFoundException e){
    		e.printStackTrace();
    	}
    }			
	
	public void init(){
		super.init(this.getClass());
	}
	
	private ResultSet select(Connection conn,String sql){
		ResultSet result=null;		
		int reconi=1;		
        while(reconi<=reconnNum){       	
        	try {                 		       		
        		Statement st = conn.createStatement();
            	st.execute("set names "+SetNameLanCode+";");
            	result=st.executeQuery(sql);           	
                break;
        	}catch(SQLException e){            	
                printError("Mysql,"+e.getMessage());
                if(reconi==(reconnNum)){
                	break; 
                }               
                try{
                    Thread.sleep(sleeptime); 
                    }catch(InterruptedException e2){
                    	printError("Mysql,"+e2.getMessage());
                    }           
            }                                
        	reconi++;                      
        }             
        return result;      
	}
	
	private Connection MysqlgetConnection(String IP,String Port,String dbName,String username,String password,String ConnLanCode,int conntimeout){
		Connection conn=null;
		try{    		   		    			    	              	       	 
            conn=DriverManager.getConnection(MYSQLURLHead+IP+":"+Port+"/"+dbName+MYSQLURLEnding+ConnLanCode+URLTimeout+conntimeout,username,password);                               
        } catch (SQLException e) {       	       	
        	printError("Mysql,"+e.getMessage());          
        }   	    	   	        
        return conn; 		
	}
	
	public int checkslave(String ip,String port,String dbName){
		return checkslave(ip,port,dbName,slavecheckuser,slavecheckuserpwd);
	}
	
	private int checkslave(String ip,String port,String dbName,String username,String password){
		Context.init();
		int res = 1;
		String sql = "show slave status;";	
		Connection conn = MysqlgetConnection(ip,port,dbName,username,password,ConnLanCode,conntimeout);
		
		//slave error
		if(conn == null) return 1;
		
		ResultSet rs = select(conn,sql);
		
		if(null == rs) return 1;
		
		try{
			if(rs.next()){				
				if(rs.getString("Seconds_Behind_Master") == null || rs.getInt("Seconds_Behind_Master") >= SBehindMasterLevel){
					if(null != rs.getString("Seconds_Behind_Master")){
						Context.Seconds_Behind_Master = rs.getString("Seconds_Behind_Master");												
					}else{
						Context.Seconds_Behind_Master = "null";
					}
					
					Context.Slave_IO_Running = rs.getString("Slave_IO_Running");
					Context.Slave_SQL_Running = rs.getString("Slave_SQL_Running");					
					
					printError("Mysql,slave error,ip:"+ip+",port:"+port+","+rs.getString("Slave_IO_Running")+","+rs.getString("Slave_SQL_Running")+","+rs.getString("Seconds_Behind_Master"));
				}else{
					res = 0;
				}				
			}
			rs.close();
			conn.close();
		}catch(SQLException e){
			printError(e.getMessage());
		}finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {					
					printError("Mysql,"+e.getMessage());
				}
			}
			
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {					
					printError("Mysql,"+e.getMessage());
				}
			}
		}		
		return res;
	}

	public int getReconnNum() {
		return reconnNum;
	}

	public void setReconnNum(int reconnNum) {
		this.reconnNum = reconnNum;
	}

	public int getConntimeout() {
		return conntimeout;
	}

	public void setConntimeout(int conntimeout) {
		this.conntimeout = conntimeout;
	}

	public int getSleeptime() {
		return sleeptime;
	}

	public void setSleeptime(int sleeptime) {
		this.sleeptime = sleeptime;
	}

	public String getConnLanCode() {
		return ConnLanCode;
	}

	public void setConnLanCode(String connLanCode) {
		ConnLanCode = connLanCode;
	}

	public String getSetNameLanCode() {
		return SetNameLanCode;
	}

	public void setSetNameLanCode(String setNameLanCode) {
		SetNameLanCode = setNameLanCode;
	}

	public int getSBehindMasterLevel() {
		return SBehindMasterLevel;
	}

	public void setSBehindMasterLevel(int sBehindMasterLevel) {
		SBehindMasterLevel = sBehindMasterLevel;
	}

	public String getSlavecheckuser() {
		return slavecheckuser;
	}

	public void setSlavecheckuser(String slavecheckuser) {
		this.slavecheckuser = slavecheckuser;
	}

	public String getSlavecheckuserpwd() {
		return slavecheckuserpwd;
	}

	public void setSlavecheckuserpwd(String slavecheckuserpwd) {
		this.slavecheckuserpwd = slavecheckuserpwd;
	}
	
}
