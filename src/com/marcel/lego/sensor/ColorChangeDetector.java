package com.marcel.lego.sensor;

import java.util.ArrayList;
import java.util.List;

import lejos.robotics.Color;
import lejos.robotics.ColorDetector;
import lejos.robotics.LightDetector;

/**
 * Creates a monitor thread that permanently checks the sensor for current color and light.
 * Informs all registered listeners after delay about the current color.
 * 
 * @author the.messias
 */
public abstract class ColorChangeDetector {
	/**
	 * Can be null!
	 */
	protected LightDetector lightSensor;
	
	/**
	 * Can be null!
	 */
	protected ColorDetector colorSensor;

	private long delay = 500;
	private final MonitorThread monitorThread;
	private boolean enabled = true;
	private List<ColorChangeListener> colorListeners = new ArrayList<ColorChangeListener>();
	
	/**
	 * Constructs a new Sensor monitor for light and color sensors.
	 * After construction monitoring starts.
	 * 
	 * @param light - sensor for light detection
	 * @param color - sensor for color detection
	 * @param delay - time in millis between each scan
	 */
	public ColorChangeDetector(LightDetector light, ColorDetector color, long delay) {
		this.lightSensor = light;
		this.colorSensor = color;
		setup();
		
		this.delay = delay >= 0 ? delay : this.delay;
		
		this.monitorThread = new MonitorThread();
		monitorThread.setDaemon(true);
		monitorThread.start();
	}
	
	protected abstract void setup();
	
	protected abstract int getBackground();
	
	protected com.marcel.lego.util.Color scan() {
		int rawLight = lightSensor != null ? lightSensor.getNormalizedLightValue() : 0;
		Color color = colorSensor != null ? colorSensor.getColor() : new Color(0, 0, 0, Color.NONE);
		com.marcel.lego.util.Color colorAndLight = new com.marcel.lego.util.Color(color, getBackground(), rawLight);
		return colorAndLight;
	}
	
	private void fireColorChanged(com.marcel.lego.util.Color color) {
		for (ColorChangeListener listener : colorListeners) {
			listener.handleColorChanged(color);
		}
	}
	
	public void addListener(ColorChangeListener listener) {
		this.colorListeners.add(listener);	
	}
	
	public void removeListener(ColorChangeListener listener) {
		this.colorListeners.remove(listener);
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public boolean isEnabled() {
		return this.enabled;
	}
	
	/**
	 * Thread to monitor the light and color detector.
	 *
	 */
	private class MonitorThread extends Thread {

		long prev_time;
		
		@Override
		public void run() {
			while(true) {
				// Only performs scan if detection is enabled.
				com.marcel.lego.util.Color col = enabled ? scan() : null;
				if(col != null) fireColorChanged(col);
				
				try {
					long elapsed_time = System.currentTimeMillis() - prev_time;
					long actual_delay = delay - elapsed_time;
					if(actual_delay < 0) actual_delay = 0;
					
					Thread.sleep(actual_delay);
					prev_time = System.currentTimeMillis();
				} catch (InterruptedException e) {
					// this is wanted when monitoring should be stopped.
				}
			} // end while
		} // end run
	} // end Thread class
}
