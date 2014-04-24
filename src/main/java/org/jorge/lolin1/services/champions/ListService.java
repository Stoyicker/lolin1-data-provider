package org.jorge.lolin1.services.champions;

import org.jorge.lolin1.data.DataAccessObject;
import org.jorge.lolin1.data.DataUpdater;

import javax.servlet.http.HttpServlet;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * This file is part of lolin1-data-provider.
 * <p/>
 * lolin1-data-provider is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * lolin1-data-provider is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with lolin1-data-provider.  If not, see <http://www.gnu.org/licenses/>.
 */

@Path("/champions/list/{realm}/{locale}")
@Produces("application/json; charset=UTF-8")
public class ListService extends HttpServlet {
    @GET
    public final Response get(@PathParam("realm") String realm,
                              @PathParam("locale") String locale) {
        if (realm.isEmpty() || locale.isEmpty()) {
            return Response.ok(DataAccessObject.getResponseUnsupported())
                    .build();
        }
        if (DataUpdater.isUpdating()) {
            return Response.status(409).build();
        } else {
            return Response.ok(DataAccessObject.getJSONList(realm, locale))
                    .build();
        }
    }
}
