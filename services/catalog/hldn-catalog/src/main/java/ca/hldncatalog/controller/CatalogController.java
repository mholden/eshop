package ca.hldncatalog.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import ca.hldncatalog.catalog.CatalogItem;
import ca.hldncatalog.repository.CatalogItemRepository;

@RestController
@RequestMapping("/catalog")
public class CatalogController {
	
	@Autowired
    CatalogItemRepository catalogItemRepository;

	@GetMapping("/ping")
    public String ping() {
    	System.out.println("ping()");
    	return "Ping successful!\n";
    }
    
    @GetMapping("/items")
    public List<CatalogItem> getCatalogItems(@RequestParam(required = false) Integer pageSize, @RequestParam(required = false) Integer pageIndex) {
    	List<CatalogItem> catalogItems = null;
    	Integer _pageIndex = 0;
    	
    	System.out.println("getCatalogItems() pageSize: " + pageSize + " pageIndex: " + pageIndex);
    	
    	if (pageSize != null) {
    		if (pageIndex != null) {
    			_pageIndex = pageIndex;
    		}
    		catalogItems = catalogItemRepository.findAll(PageRequest.of(_pageIndex, pageSize)).getContent();
    	} else {
    		catalogItems = catalogItemRepository.findAll();
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
