package com.marcel.lego.util;



public class Color extends lejos.nxt.ColorSensor.Color {

	private final int rawLight;

	public Color(int red, int green, int blue, int background, int colorId, int rawLight) {
		super(red, green, blue, background, colorId);
		this.rawLight = rawLight;
	}
	
	public Color(lejos.nxt.ColorSensor.Color color, int rawLight) {
		this(color.getRed(), color.getGreen(), color.getBlue(), color.getBackground(), color.getColor(), rawLight);
	}

	public Color(lejos.robotics.Color simpleCol, int background, int rawLight) {
		super(simpleCol.getRed(), simpleCol.getGreen(), simpleCol.getBlue(), background, simpleCol.getColor());
		this.rawLight = rawLight;
	}

	public int getRawLight() {
		return rawLight;
	}

	@Override
	public String toString() {
		return toString(this, rawLight);
	}

	public static String toString(lejos.nxt.ColorSensor.Color color, int rawLight) {
		String str;
		switch (color.getColor()) {
		case Color.BLACK: str = "Black" + "(" + color.getGreen() + "." + color.getGreen() + "." + color.getBlue() +")"; break;
		case Color.WHITE: str = "White" + "(" + color.getGreen() + "." + color.getGreen() + "." + color.getBlue() +")"; break;
		case Color.GREEN: str = "Green" + "(" + color.getGreen() + "." + color.getGreen() + "." + color.getBlue() +")"; break;
		case Color.BLUE: str = "Blue" + "(" + color.getGreen() + "." + color.getGreen() + "." + color.getBlue() +")"; break;
		case Color.YELLOW: str = "Yellow" + "(" + color.getGreen() + "." + color.getGreen() + "." + color.getBlue() +")"; break;
		case Color.RED: str = "Red" + "(" + color.getGreen() + "." + color.getGreen() + "." + color.getBlue() +")"; break;
		case Color.CYAN: str = "Cyan" + "(" + color.getGreen() + "." + color.getGreen() + "." + color.getBlue() +")"; break;
		case Color.MAGENTA: str = "Magenta" + "(" + color.getGreen() + "." + color.getGreen() + "." + color.getBlue() +")"; break;
		case Color.DARK_GRAY: str = "Dark Grey" + "(" + color.getGreen() + "." + color.getGreen() + "." + color.getBlue() +")"; break;
		case Color.ORANGE: str = "Orange" + "(" + color.getGreen() + "." + color.getGreen() + "." + color.getBlue() +")"; break;
		case Color.LIGHT_GRAY: str = "Light Grey" + "(" + color.getGreen() + "." + color.getGreen() + "." + color.getBlue() +")"; break;
		case Color.PINK: str = "Pink" + "(" + color.getGreen() + "." + color.getGreen() + "." + color.getBlue() +")"; break;
		default: str = "None" + "(" + color.getGreen() + "." + color.getGreen() + "." + color.getBlue() +")";
		}
		
		str += "." + rawLight;
		return str;
	}
}
