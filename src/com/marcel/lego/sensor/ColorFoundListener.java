package com.marcel.lego.sensor;

import com.marcel.lego.util.Color;


/**
 * This listener is informed when the color of the color sensor has been changed.
 * If checks if the current color matches the defined color and if yes, then it processes the event.
 * 
 * @author the.messias
 */
public abstract class ColorFoundListener implements ColorChangeListener {
	
	private final ColorFilter filter;
	
	public ColorFoundListener(ColorFilter filter) {
		this.filter = filter;
	}

	@Override
	public void handleColorChanged(Color color) {
		if (filter.matches(color)) {
			handleFilterColorDetected(color);
		}
	}
	
	/**
	 * The desired color was detected.
	 * 
	 * @param color
	 */
	public abstract void handleFilterColorDetected(Color color);
}
