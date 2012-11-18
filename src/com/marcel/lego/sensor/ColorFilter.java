package com.marcel.lego.sensor;

import com.marcel.lego.util.Color;

import lejos.nxt.LightSensor;

/**
 * Compares 2 color objects (considering a tolerance) and says if they are identical or not.
 * 
 * @author the.messias
 */
public class ColorFilter implements SensorFilter<Color> {

	private int rMax = -1;
	private int rMin = -1;
	private int gMax = -1;
	private int gMin = -1;
	private int bMax = -1;
	private int bMin = -1;
	private int lMax = -1;
	private int lMin = -1;
	
	private int colorId = -1;
	
	/**
	 * A filter that compares 2 color objects.
	 * 
	 * @param colorToMatch - the color to match (-1 means this value is ignored)
	 * @param toleranceRGB - tolerance in percent
	 * @param toleranceLight - tolerance in percent
	 */
	public ColorFilter(Color colorToMatch, double toleranceRGB, double toleranceLight) {
		this.rMax = (int) Math.max(0, Math.round(colorToMatch.getRed() + (255 * toleranceRGB))); 
		this.gMax = (int) Math.max(0, Math.round(colorToMatch.getGreen() + (255 * toleranceRGB))); 
		this.bMax = (int) Math.max(0, Math.round(colorToMatch.getBlue() + (255 * toleranceRGB))); 
		this.lMax = (int) Math.max(0, Math.round(colorToMatch.getRawLight() + (LightSensor.MAX_AD_RAW * toleranceLight))); 
		
		this.rMin = (int) Math.max(0, Math.round(colorToMatch.getRed() - (255 * toleranceRGB))); 
		this.gMin = (int) Math.max(0, Math.round(colorToMatch.getGreen() - (255 * toleranceRGB))); 
		this.bMin = (int) Math.max(0, Math.round(colorToMatch.getBlue() - (255 * toleranceRGB))); 
		this.lMin = (int) Math.max(0, Math.round(colorToMatch.getRawLight() - (LightSensor.MAX_AD_RAW * toleranceLight))); 
	}
	
	/**
	 * Color Filter with a tolerance of 20%.
	 * 
	 * @param colorToMatch
	 */
	public ColorFilter(Color colorToMatch) {
		this(colorToMatch, 0.2, 0.2);
	}
	
	/**
	 * Color Filter thats checks if one of the predefined colors was found.
	 * 
	 * @param int colorId of lejos.nxt.ColorSensor.Color
	 */
	public ColorFilter(int colorId) {
		this.colorId = colorId;
	}
	
	@Override
	public boolean matches(Color value) {
		if (colorId == -1) {
			return matchRGB(value);
		} else {
			return matchColorId(value.getColor());
		}
	}
	
	private boolean matchColorId(int colorIdValue) {
		return colorId == colorIdValue;
	}
	
	private boolean matchRGB(Color colorVal) {
		boolean isMatching = matchColorAttribute(colorVal.getRed(), rMin, rMax)
				&& matchColorAttribute(colorVal.getGreen(), gMin, gMax)
				&& matchColorAttribute(colorVal.getBlue(), bMin, bMax)
				&& matchColorAttribute(colorVal.getRawLight(), lMin, lMax);
		return isMatching;
	}
	
	private boolean matchColorAttribute(int attr, int expectedMin, int expectedMax) {
		return attr == -1 || (attr >= expectedMin && attr <= expectedMax);
	}
}
