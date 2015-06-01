package gl.object;

public class GameInfo implements Cloneable{
	
	private String servername;
	private String ip;
	private String port;
	private String gameserverid;
	private String dbname;
	private String gameid;
	private String gametype;
	private String username;
	private String password;
	private String version;
	
	public GameInfo clone() throws CloneNotSupportedException{
		return (GameInfo)super.clone();
	}
	
	public String getServername() {
		return servername;
	}
	public void setServername(String servername) {
		this.servername = servername;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getGameserverid() {
		return gameserverid;
	}
	public void setGameserverid(String gameserverid) {
		this.gameserverid = gameserverid;
	}
	public String getDbname() {
		return dbname;
	}
	public void setDbname(String dbname) {
		this.dbname = dbname;
	}
	public String getGameid() {
		return gameid;
	}
	public void setGameid(String gameid) {
		this.gameid = gameid;
	}
	public String getGametype() {
		return gametype;
	}
	public void setGametype(String gametype) {
		this.gametype = gametype;
	}
		
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}	

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	
}
