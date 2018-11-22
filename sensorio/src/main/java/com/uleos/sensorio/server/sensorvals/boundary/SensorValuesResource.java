package com.uleos.sensorio.server.sensorvals.boundary;

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

import com.uleos.sensorio.server.sensorvals.entity.SensorValue;

@Stateless
@Consumes(value = { MediaType.APPLICATION_JSON })
@Produces(value = { MediaType.APPLICATION_JSON })
@Path("sensorvalues")
public class SensorValuesResource {

	@Inject
	private SensorValueService sensorValueService;

	@POST
	public Response addSensorValue(JsonObject json, @Context HttpServletRequest request) {
		SensorValue sensorVal = new SensorValue();
		sensorVal.setSensorVal(json.getJsonNumber("sensorval").bigDecimalValue());
		sensorVal = sensorValueService.create(sensorVal);
		return Response.created(URI.create("/" + sensorVal.getId())).build();
	}

	@GET
	public Response allSensorValues() {
		List<SensorValue> findAll = sensorValueService.findAll();
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
		findAll.forEach(a -> jsonArrayBuilder
				.add(Json.createObjectBuilder().add("id", a.getId()).add("sensorval", a.getSensorVal())));
		return Response.ok(jsonArrayBuilder.build()).build();
	}

	@GET
	@Path("{id}")
	public SensorValue sensorValue(@PathParam("id") Long id) {
		return sensorValueService.findById(id);
	}
}
