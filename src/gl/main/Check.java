package gl.main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import gl.database.Mysql;
import gl.global.Context;
import gl.ibatis.CenterMysql;
import gl.mail.Mail;
import gl.object.GameInfo;
import gl.object.MyObject;

public class Check extends MyObject{
	private Mysql mysql;
	private CenterMysql centermysql;
	private Mail mail;
	private int localconf;
	private List<GameInfo> localgslist;
	private int reporttime;
	private SimpleDateFormat hdate = new SimpleDateFormat("HH");
	private String title;
	private String contentOk;
	
	public void init(){
		super.init(this.getClass());
	}
	
	public void run(){
		
		List<GameInfo> gslist = null;
		
		//read from local
		if(localconf == 1){
			gslist = localgslist;
		}else{
			gslist = centermysql.querySlavelist();
		}
		
		
		if(null == gslist) {
			printError("Check,gslist is null");
			return;
		}
		
		String emailstr = "";
		
		for(GameInfo ginfo: gslist){
			
			int check_res = mysql.checkslave(ginfo.getIp(),ginfo.getPort(),ginfo.getDbname());
			if(check_res == 1){	
				
				if(Context.Slave_default.equals(Context.Slave_IO_Running)){
					emailstr=emailstr+"<br>服务器id:"+ginfo.getGameserverid()+",connect error"+",ip:"+ginfo.getIp()+",port:"+ginfo.getPort()+",gid:"+ginfo.getGameid()+"</br>";
				}else{
					emailstr=emailstr+"<br>服务器id:"+ginfo.getGameserverid()+",slave_io:"+Context.Slave_IO_Running+",slave_sql:"+Context.Slave_SQL_Running+",Seconds_Behind_Master:"+Context.Seconds_Behind_Master+",ip:"+ginfo.getIp()+",port:"+ginfo.getPort()+",gid:"+ginfo.getGameid()+"</br>";
				}								
				
				printError("email:"+emailstr);
				printError("服务器id:"+ginfo.getGameserverid()+",slave_io:"+Context.Slave_IO_Running+",slave_sql:"+Context.Slave_SQL_Running+",Seconds_Behind_Master:"+Context.Seconds_Behind_Master+",ip:"+ginfo.getIp()+",port:"+ginfo.getPort()+",gid:"+ginfo.getGameid());
			}else{
				
				
				
				printError("slave check ok,slave ip:"+ginfo.getIp()+",port:"+ginfo.getPort()+",gsid:"+ginfo.getGameserverid()+",gid:"+ginfo.getGameid());				
			}
			
		}
		
		if(emailstr.equals("")){
			printError("slave all ok");
			
			int now = Integer.valueOf(hdate.format(new Date()));			
			if(now == reporttime){
				mail.htmlmail(title,contentOk);
			}
		}else{
			mail.htmlmail(title,emailstr);
		}
		
	}	
	
	public Mysql getMysql() {
		return mysql;
	}

	public void setMysql(Mysql mysql) {
		this.mysql = mysql;
	}

	public CenterMysql getCentermysql() {
		return centermysql;
	}

	public void setCentermysql(CenterMysql centermysql) {
		this.centermysql = centermysql;
	}

	public Mail getMail() {
		return mail;
	}

	public void setMail(Mail mail) {
		this.mail = mail;
	}

	public int getLocalconf() {
		return localconf;
	}

	public void setLocalconf(int localconf) {
		this.localconf = localconf;
	}

	public List<GameInfo> getLocalgslist() {
		return localgslist;
	}

	public void setLocalgslist(List<GameInfo> localgslist) {
		this.localgslist = localgslist;
	}

	public int getReporttime() {
		return reporttime;
	}

	public void setReporttime(int reporttime) {
		this.reporttime = reporttime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContentOk() {
		return contentOk;
	}

	public void setContentOk(String contentOk) {
		this.contentOk = contentOk;
	}
	
	
	
}
