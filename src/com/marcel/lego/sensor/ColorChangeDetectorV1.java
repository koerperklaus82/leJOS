package com.marcel.lego.sensor;

import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.robotics.ColorDetector;

import com.marcel.lego.util.Color;

/**
 * Implementation for the old color and light sensor (NXT 1.0).
 * It does not support color measuring, just black and white.
 * 
 * @author the.messias
 */
public class ColorChangeDetectorV1 extends ColorChangeDetector {

	/**
	 * Set up old version of the light sensor.
	 * 
	 * @param light - must not be null!
	 * @param delay
	 */
	public ColorChangeDetectorV1(SensorPort port, long delay) {
		super(new LightSensor(port), null, delay);
		super.colorSensor = new FakeColorSensor((LightSensor) lightSensor);
	}

	@Override
	protected void setup() {
	}

	@Override
	protected int getBackground() {
		return lightSensor != null ? ((LightSensor) lightSensor).getFloodlight() : Color.NONE;
	}
	
	private static class FakeColorSensor implements ColorDetector {
		private final LightSensor light;
		public FakeColorSensor(LightSensor light) {
			this.light = light;
		}

		@Override
		public lejos.robotics.Color getColor() {
			int rawLight = light.getNormalizedLightValue();
			return getGreyScale(rawLight);
		}

		@Override
		public int getColorID() {
			return Color.NONE;
		}
	}
	
	public static lejos.robotics.Color getGreyScale(int rawLight) {
		int max = LightSensor.MAX_AD_RAW;
		int val = Math.abs(max / rawLight);
//		768-1024 --> white
//		512-768 --> light grey
//		256-512 --> dark grey
//		0-256 --> black
		switch (val) {
			case 0: return new lejos.robotics.Color(0, 0, 0, Color.WHITE);
			case 1: return new lejos.robotics.Color(10, 10, 10, Color.WHITE);
			case 2: return new lejos.robotics.Color(50, 50, 50, Color.LIGHT_GRAY);
			case 3: return new lejos.robotics.Color(150, 150, 150, Color.DARK_GRAY);
			default: return new lejos.robotics.Color(250, 250, 250, Color.BLACK);
		}
	}
}
