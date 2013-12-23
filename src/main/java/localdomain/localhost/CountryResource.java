/*
 * Copyright 2010-2013, CloudBees Inc.
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

import static javax.ws.rs.core.MediaType.*;
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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import localdomain.localhost.domain.Country;
import localdomain.localhost.domain.CountryArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * rest services
 * 
 * @author valentina armenise
 * 
 */
@Path("/country")
@Stateless
public class CountryResource {
	Logger logger = LoggerFactory.getLogger(CountryResource.class);

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

	@Path("/")
	@POST
	@Consumes(APPLICATION_FORM_URLENCODED)
	@Produces(APPLICATION_JSON)
	public Response create(@FormParam("capital") String capital,
			@FormParam("name") String name) {

		Country country = new Country(capital, name);
		countryRepository.create(country);
		logger.info("created country with name {} and capital {}",
				country.getName(), country.getCapital());
		return Response.created(
				uriInfo.getAbsolutePathBuilder().path(name).build()).build();

	}

	@Path("/")
	@PUT
	@Consumes(APPLICATION_FORM_URLENCODED)
	@Produces(APPLICATION_JSON)
	public Response updateCountry(@FormParam("capital") String capital,
			@FormParam("name") String name) {

		Country country = new Country(capital, name);
		countryRepository.upadte(country);
		logger.info("created country with name {} and capital {}",
				country.getName(), country.getCapital());
		return Response.created(
				uriInfo.getAbsolutePathBuilder().path(name).build()).build();

	}

	@Path("/")
	@GET
	@Produces({ APPLICATION_JSON, APPLICATION_XML })
	public Response getAll() {

		List<Country> countries = countryRepository.getAll();
		CountryArray countryarr = new CountryArray();
		countryarr.setCountries(countries);
		return Response.ok(countryarr).build();

	}

	@Path("/{name}")
	@DELETE
	public Response deleteCountry(@PathParam("name") String name) {

		countryRepository.delete(name);
		return Response.noContent().build();
	}

	@Path("/{name}")
	@GET
	@Produces({ APPLICATION_JSON, APPLICATION_XML })
	public Response get(@PathParam("name") String name) {

		try {
			Country country = countryRepository.findByName(name);
			return Response.ok(country).build();
		} catch (EntityNotFoundException e) {
			logger.error("Country name {} not found", name);
			return Response.status(Status.NOT_FOUND).build();
		} catch (NoResultException e) {
			logger.error("Country with name {} not found", name);
			return Response.status(Status.NOT_FOUND).build();
		}

	}

}
