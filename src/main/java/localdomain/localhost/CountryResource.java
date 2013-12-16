/*
 * Copyright 2010-2013, the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package localdomain.localhost;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import localdomain.localhost.domain.Country;
import localdomain.localhost.domain.CountryArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Rest services
 */
@Path("/country")
@Stateless
public class CountryResource {
	Logger log = LoggerFactory.getLogger(CountryResource.class);

	@Context
	UriInfo uriInfo;

	/**
	 * EJB annotation does not work with REST services, thus we defined a
	 * provider as workaround that will inject the object annotated by @EJB (see
	 * class EJBProvider) with the specified name (in this case
	 * CountryRepository).
	 */
	@EJB(mappedName = "java:module/CountryRepository")
	private CountryRepository countryRepository;

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/")
	public Response create(@FormParam("capital") String capital,
			@FormParam("name") String name) {

		Country country = new Country(capital, name);
		countryRepository.create(country);
		log.info("created country with name "+ country.getName()+" and capital "+country.getCapital());
		return Response.created(
				uriInfo.getAbsolutePathBuilder().path(name).build()).build();

	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/")
	public Response update(@FormParam("capital") String capital,
			@FormParam("name") String name) {

		Country country = new Country(capital, name);
		countryRepository.upadte(country);
		log.info("updated country with name "+ country.getName()+" and capital "+country.getCapital());
		return Response.created(
				uriInfo.getAbsolutePathBuilder().path(name).build()).build();

	}

	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/")
	public Response getAll() {
		
		List<Country> countries=countryRepository.getAll();
		CountryArray countryarr=new CountryArray();
		countryarr.setCountries(countries);
		return Response.ok(countryarr).build();	
		
	}

	@DELETE
	@Path("/{name}")
	public Response delete(@PathParam ("name") String name) {
		
		countryRepository.delete(name);
		return Response.noContent().build();	
	}

	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/{name}")
	public Response get(@PathParam("name") String name) {

		try {
			Country country = countryRepository.findByName(name);
			return Response.ok(country).build();
		} catch (EntityNotFoundException e) {
			log.error("Country with name '" + name + "' not found");
			return Response.status(Status.NOT_FOUND).build();
		}
		catch (NoResultException e) {
			log.error("Country with name '" + name + "' not found");
			return Response.status(Status.NOT_FOUND).build();
		}

	}

}
