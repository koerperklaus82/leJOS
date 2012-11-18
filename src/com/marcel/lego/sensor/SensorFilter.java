package com.marcel.lego.sensor;

public interface SensorFilter<T> {
	public boolean matches(T value);
}
