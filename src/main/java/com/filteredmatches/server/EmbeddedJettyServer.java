package com.filteredmatches.server;

import java.io.IOException;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class EmbeddedJettyServer {

	private static final String CONTEXT_PATH = "/";
	private static final Integer PORT = 8080;
	private static final String MAPPING_URL = "/";
	private static final String CONFIG_LOCATION = "com.filteredmatches.config";

	private Server server;

	

	public void startServer() throws Exception {
		server = new Server(PORT);
		server.setHandler(getServletContextHandler(getContext()));
		org.eclipse.jetty.webapp.Configuration.ClassList classlist = org.eclipse.jetty.webapp.Configuration.ClassList
				.setServerDefault(server);
		classlist.addAfter("org.eclipse.jetty.webapp.FragmentConfiguration",
				"org.eclipse.jetty.plus.webapp.EnvConfiguration",
				"org.eclipse.jetty.plus.webapp.PlusConfiguration");
		classlist.addBefore("org.eclipse.jetty.webapp.JettyWebXmlConfiguration",
				"org.eclipse.jetty.annotations.AnnotationConfiguration");
		server.start();

	}

	public String getServerURI() {
		return server.getURI().toString();
	}

	public void stopServer() throws Exception {
		server.stop();

	}

	private WebAppContext getServletContextHandler(
			WebApplicationContext context) throws IOException {
		WebAppContext contextHandler = new WebAppContext();
		contextHandler.setErrorHandler(null);
		contextHandler.setContextPath(CONTEXT_PATH);
		contextHandler.addServlet(
				new ServletHolder(new DispatcherServlet(context)), MAPPING_URL);
		contextHandler.addEventListener(new ContextLoaderListener(context));
		contextHandler.setResourceBase("src/main/webapp");
		// Set the ContainerIncludeJarPattern so that jetty examines these
		// container-path jars for tlds, web-fragments etc.
		// If you omit the jar that contains the jstl .tlds, the jsp engine will
		// scan for them instead.
		contextHandler.setAttribute(
				"org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",
				".*/[^/]*servlet-api-[^/]*\\.jar$|.*/javax.servlet.jsp.jstl-.*\\.jar$|.*/[^/]*taglibs.*\\.jar$|.*/jstl-.*\\.jar$");
		
		return contextHandler;
	}

	private WebApplicationContext getContext() {
		AnnotationConfigWebApplicationContext context =
				new AnnotationConfigWebApplicationContext();
		context.setConfigLocation(CONFIG_LOCATION);
		
		return context;
	}

}
