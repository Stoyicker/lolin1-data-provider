package org.lolin1.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.lolin1.data.DataAccessObject;

@Path("/champions")
public final class Services {

	@GET
	@Produces("application/json")
	public final String getChampions(@PathParam("realm") String realm,
			@PathParam("locale") String locale) {
		return DataAccessObject.getJSONChampions(realm, locale);
	}

	@GET
	@Produces("application/json")
	public final String getDragonMagicVersion(@PathParam("realm") String realm) {
		return DataAccessObject.getJSONVersion(realm);
	}

	@GET
	@Produces("image/png")
	public final Response getImage(@PathParam("type") String type,
			@PathParam("name") String name) {

	}

	@GET
	@Produces("text/plain")
	public final String getImageMD5(@PathParam("type") String type,
			@PathParam("name") String name) {

	}
}
