package com.uleos.sensorio.server.sensorvals.boundary;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.uleos.sensorio.server.JpaResultHelper;
import com.uleos.sensorio.server.sensorvals.entity.SensorValue;
import com.uleos.sensorio.server.sensorvals.entity.SensorValue_;

@Stateless
public class SensorValueService {

	@PersistenceContext(unitName = "pu")
	EntityManager entityManager;

	public SensorValue create(SensorValue sensorVal) {
		entityManager.persist(sensorVal);
		return sensorVal;
	}

	public List<SensorValue> findAll() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<SensorValue> cq = cb.createQuery(SensorValue.class);
		Root<SensorValue> root = cq.from(SensorValue.class);
		cq.select(root);
		cq.orderBy(cb.asc(root.get(SensorValue_.id)));
		TypedQuery<SensorValue> q = entityManager.createQuery(cq);
		return q.getResultList();
	}

	public SensorValue merge(SensorValue sensorVal) {
		return entityManager.merge(sensorVal);
	}

	public void remove(SensorValue sensorVal) {
		if (!entityManager.contains(sensorVal)) {
			sensorVal = entityManager.merge(sensorVal);
		}
		entityManager.remove(sensorVal);
	}

	public SensorValue findById(Long id) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<SensorValue> cq = cb.createQuery(SensorValue.class);
		Root<SensorValue> root = cq.from(SensorValue.class);
		cq.select(root).where(cb.equal(root.get(SensorValue_.id), id));
		TypedQuery<SensorValue> q = entityManager.createQuery(cq);
		return JpaResultHelper.getSingleResultOrNull(q);
	}

	public void save(List<SensorValue> sensorValues) {
		for (SensorValue btcAdress : sensorValues) {
			if (btcAdress.getId() == null) {
				entityManager.persist(btcAdress);
			} else {
				entityManager.merge(btcAdress);
			}
			if (btcAdress.isMarkedToDelete()) {
				remove(btcAdress);
			}
		}
	}

}
