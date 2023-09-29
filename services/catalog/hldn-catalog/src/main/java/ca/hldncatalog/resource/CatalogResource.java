package ca.hldncatalog.resource;

import java.util.*;

import org.hibernate.Session;

import ca.hldncatalog.catalog.CatalogItem;
import ca.hldncatalog.hibernate.HibernateUtil;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

@Path("/catalog")
public class CatalogResource {

    @GET
    //@Path("/items")
    @Produces(MediaType.TEXT_HTML)
    public String sayHello() {
    	System.out.println("sayHello()");
    	return "Hello World!\n";
    }
    
    @GET
    @Path("/items")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CatalogItem> getCatalogItems(@QueryParam("pageSize") Integer pageSize, @QueryParam("pageIndex") Integer pageIndex) {
    	List<CatalogItem> catalogItems = null;
    	Integer _pageIndex = 0;
    	
    	System.out.println("getCatalogItems() pageSize: " + pageSize + " pageIndex: " + pageIndex);
    	
    	try (Session session = HibernateUtil.getSessionFactory().openSession()) {
    		catalogItems = session.createQuery("from CatalogItem", CatalogItem.class).list();
    		/*
    		catalogItems.forEach(ci -> {
				System.out.println(ci);
			});
			*/
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	if (pageSize != null) {
    		if (pageIndex != null) {
    			_pageIndex = pageIndex;
    		}
    		catalogItems = catalogItems.subList(_pageIndex * pageSize, Math.min(_pageIndex * pageSize + pageSize, catalogItems.size()));
    	}
    	
    	return catalogItems;
    }

    /*
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Client getById(@PathParam("id") Long id) {
        return clientRepository.getById(id);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Client insert(Client client) {
        return clientRepository.insert(client);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(Client client) {
        if (!clientRepository.exists(client.getId())) {
            return Response.status(Response.Status.BAD_REQUEST).entity(client.getId() + "Doesn't exist").build();
        }
        Client clie = clientRepository.update(client);
        return Response.ok().entity(clie).build();
    }

    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Long id) {
        if (id == 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid ID 0").build();
        }
        clientRepository.delete(id);
        return Response.ok().entity("Item has been deleted successfully.").build();
    }
    */
}
