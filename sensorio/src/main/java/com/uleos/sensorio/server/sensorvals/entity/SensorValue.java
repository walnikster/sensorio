package com.uleos.sensorio.server.sensorvals.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.uleos.sensorio.server.sensorvals.AbstractNaturalKeyEntity;

@Entity
@Table(name = "SENSOR_VALS")
@SequenceGenerator(name = "seq", allocationSize = 10, sequenceName = "id_sequence")
public class SensorValue extends AbstractNaturalKeyEntity {

	public SensorValue() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
	@Column(name = "id")
	private Long id;

	@Column(name = "sensorval")
	private BigDecimal sensorVal;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getSensorVal() {
		return sensorVal;
	}

	public void setSensorVal(BigDecimal sensorVal) {
		this.sensorVal = sensorVal;
	}

}
