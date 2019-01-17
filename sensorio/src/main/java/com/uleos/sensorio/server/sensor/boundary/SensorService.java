package com.uleos.sensorio.server.sensor.boundary;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.uleos.sensorio.server.JpaResultHelper;
import com.uleos.sensorio.server.sensor.entity.Sensor;
import com.uleos.sensorio.server.sensor.entity.Sensor_;

@Stateless
public class SensorService {

	@PersistenceContext(unitName = "pu")
	EntityManager entityManager;

	public Sensor create(String sensorId, String name) {
		Sensor sensor = new Sensor();
		sensor.setSensorId(sensorId);
		sensor.setName(name);
		entityManager.persist(sensor);
		return sensor;
	}

	public List<Sensor> findAll() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Sensor> cq = cb.createQuery(Sensor.class);
		Root<Sensor> root = cq.from(Sensor.class);
		cq.select(root);
		cq.orderBy(cb.asc(root.get(Sensor_.id)));
		TypedQuery<Sensor> q = entityManager.createQuery(cq);
		return q.getResultList();
	}

	public Sensor merge(Long id, String sensorId, String name) {
		Sensor sensorInDB = entityManager.find(Sensor.class, id);
		sensorInDB.setSensorId(sensorId);
		sensorInDB.setName(name);
		return entityManager.merge(sensorInDB);
	}

	public void remove(Sensor sensor) {
		if (!entityManager.contains(sensor)) {
			sensor = entityManager.merge(sensor);
		}
		entityManager.remove(sensor);
	}

	public void remove(Long id) {
		entityManager.remove(findById(id));
	}

	public Sensor findById(Long id) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Sensor> cq = cb.createQuery(Sensor.class);
		Root<Sensor> root = cq.from(Sensor.class);
		cq.select(root).where(cb.equal(root.get(Sensor_.id), id));
		TypedQuery<Sensor> q = entityManager.createQuery(cq);
		return JpaResultHelper.getSingleResultOrNull(q);
	}

	public Sensor getSensorBySensorId(String sensorId) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Sensor> cq = cb.createQuery(Sensor.class);
		Root<Sensor> root = cq.from(Sensor.class);
		cq.select(root).where(cb.equal(root.get(Sensor_.sensorId), sensorId));
		TypedQuery<Sensor> q = entityManager.createQuery(cq);
		return JpaResultHelper.getSingleResultOrNull(q);
	}

}
