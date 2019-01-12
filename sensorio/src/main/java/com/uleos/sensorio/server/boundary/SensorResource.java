package com.uleos.sensorio.server.boundary;

import java.net.URI;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.uleos.sensorio.server.sensor.entity.Sensor;

@Stateless
@Consumes(value = { MediaType.APPLICATION_JSON })
@Produces(value = { MediaType.APPLICATION_JSON })
@Path("sensors")
public class SensorResource {

	@Inject
	private SensorService sensorService;

	@POST
	public Response addSensorValue(JsonObject json, @Context HttpServletRequest request) {
		Sensor sensor = new Sensor();
		sensor.setSensorId(json.getJsonString("sensorId").getString());
		sensor.setName(json.getJsonString("name").getString());

		sensor = sensorService.create(sensor);
		return Response.created(URI.create("/" + sensor.getId())).build();
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
}
