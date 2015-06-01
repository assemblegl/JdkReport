package gl.global;

public class Context {
	public static final String Slave_default = "default";
	
	public static String Seconds_Behind_Master = Slave_default;	
	public static String Slave_IO_Running = Slave_default;
	public static String Slave_SQL_Running = Slave_default;
	
	public static void init(){
		Seconds_Behind_Master = Slave_default;
		Slave_IO_Running = Slave_default;
		Slave_SQL_Running = Slave_default;
	}
	

	

}
