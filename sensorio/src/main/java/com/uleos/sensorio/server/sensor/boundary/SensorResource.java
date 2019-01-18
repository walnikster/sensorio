package com.uleos.sensorio.server.sensor.boundary;

import java.net.URI;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.uleos.sensorio.server.sensor.entity.Sensor;

@Stateless
@Consumes(value = { MediaType.APPLICATION_JSON })
@Produces(value = { MediaType.APPLICATION_JSON })
@Path("sensors")
public class SensorResource {

	@Inject
	private SensorService sensorService;

	@POST
	public Response addSensor(JsonObject json, @Context HttpServletRequest request, @Context UriInfo uriInfo) {
		String sensorId = json.getJsonString("sensorId").getString();
		String name = json.getJsonString("name").getString();
		Sensor sensor = sensorService.create(sensorId, name);
		URI uri = uriInfo.getRequestUri();
		return Response.created(URI.create(uri + "/" + sensor.getId())).entity(Json.createObjectBuilder()
				.add("id", sensor.getId()).add("sensorId", sensor.getSensorId()).add("name", sensor.getName()).build())
				.build();
	}

	@PUT
	@Path("{id}")
	public Response update(@PathParam("id") Long id, JsonObject json, @Context HttpServletRequest request) {
		String sensorId = json.getJsonString("sensorId").getString();
		String name = json.getJsonString("name").getString();
		sensorService.merge(id, sensorId, name);
		return Response.ok().build();
	}

	@GET
	public Response allSensors() {
		List<Sensor> findAll = sensorService.findAll();
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
		findAll.forEach(a -> jsonArrayBuilder.add(Json.createObjectBuilder().add("id", a.getId())
				.add("sensorId", a.getSensorId()).add("name", a.getName())));
		return Response.ok(jsonArrayBuilder.build()).build();
	}

	@GET
	@Path("{id}")
	public Sensor sensor(@PathParam("id") Long id) {
		return sensorService.findById(id);
	}

	@DELETE
	@Path("{id}")
	public Response removeSensor(@PathParam("id") Long id) {
		sensorService.remove(id);
		return Response.noContent().build();
	}
}
