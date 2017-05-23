package net.slipp;

import java.io.File;

import javax.servlet.ServletException;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

public class WebServletLauncher {

	public static void main(String[] args) throws ServletException, LifecycleException {
		
		String webappDirLocation = "webapp/";
		Tomcat tomcat = new Tomcat();
		tomcat.setPort(8181);
		
		tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());
		
		tomcat.start();
		tomcat.getServer().await();
	}
}