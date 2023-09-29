package ca.hldncatalog.main;

import java.net.URI;

import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import org.hibernate.Session;

import ca.hldncatalog.hibernate.*;
import ca.hldncatalog.resource.CatalogResource;

public class Main {
	public static void main(String[] args) {
		try {
			System.out.println("main()");
			
			// scan packages
			// final ResourceConfig config = new ResourceConfig().packages("com.mkyong");

			ResourceConfig config = new ResourceConfig(CatalogResource.class);
			
			Server server = JettyHttpContainerFactory.createServer(URI.create("http://localhost:5121/"), config);
			
			Runtime.getRuntime().addShutdownHook(new Thread(() -> {
				try {
					System.out.println("Shutting down the application...");
					server.stop();
					System.out.println("Done, exit.");
				} catch (Exception e) {
					System.out.println("Exception on shutdown");
					e.printStackTrace();
				}
			}));
			
			try (Session session = HibernateUtil.getSessionFactory().openSession()) {
				// just make sure hibernate is working..
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println(String.format("Application started.%nStop the application using CTRL+C"));

			// block and wait shut down 
			Thread.currentThread().join();
		} catch (Exception e) {
			System.out.println("Exception in main");
			e.printStackTrace();
		}
	}
}
