package com.marcel.lego.sensor;

import lejos.nxt.ColorSensor;
import lejos.nxt.ColorSensor.Color;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.robotics.LightDetector;

/**
 * Implementation for the new color and light sensor (NXT 2.0).
 * Only when red light is switched on, light can be measured.
 * 
 * @author the.messias
 *
 */
public class ColorChangeDetectorV2 extends ColorChangeDetector {

	private final SensorPort port;
	private final boolean isOnlyColor;
	
	/**
	 * Inits light and color sensor.
	 * 
	 * @param port
	 * @param delay
	 */
	public ColorChangeDetectorV2(SensorPort port, long delay) {
		this(new LightSensor(port), new ColorSensor(port), delay, port, false);
	}
	
	/**
	 * Inits light and Color sensor.
	 * 
	 * @param light
	 * @param color
	 * @param delay
	 */
	private ColorChangeDetectorV2(LightDetector light, ColorSensor color, long delay, SensorPort port, boolean isOnlyColor) {
		super(light, color, delay);
		this.port = port;
		this.isOnlyColor = isOnlyColor;
	}
	
	/**
	 * Inits only light or only color or both sensors.
	 * (the color sensor interface is required because the LED RED can only be controlled by this driver)
	 * 
	 * @param port
	 * @param light
	 * @param color
	 * @param delay
	 */
	public ColorChangeDetectorV2(SensorPort port, boolean light, boolean color, long delay) {
		this(light ? new LightSensor(port) : null, new ColorSensor(port), delay, port, color && !light);
	}

	@Override
	protected void setup() {
		if (!isOnlyColor) {
		// this is required to measure light intensity
			((ColorSensor) colorSensor).setFloodlight(Color.RED);
		}
	}

	@Override
	protected int getBackground() {
		return lightSensor != null ? ((LightSensor) lightSensor).getFloodlight() : Color.NONE;
	}
}
