package gl.main;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
	
private static Logger logger = Logger.getLogger(Main.class);
	
	public static void main(String[] args) {				
		ApplicationContext ctx = new ClassPathXmlApplicationContext("conf/applicationContext.xml");
		
		logger.debug("Main,ApplicationContext created");			
		Check check = (Check)ctx.getBean("check");
		check.run();
		
		System.out.println("Main done");
		System.exit(0);
	}

}
