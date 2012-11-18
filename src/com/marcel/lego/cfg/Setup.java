package com.marcel.lego.cfg;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.RegulatedMotor;

public class Setup {

	// Connections
	public static final RegulatedMotor MOTOR_LEFT = Motor.A;
	public static final RegulatedMotor MOTOR_RIGHT = Motor.B;
	
	public static final SensorPort SENSOR_LIGHT_LEFT_V1 = SensorPort.S1;
	public static final SensorPort SENSOR_LIGHT_RIGHT_V2 = SensorPort.S2;
	
//	public static final SensorPort SENSOR_DIST_FRONT = SensorPort.S1;
//	public static final SensorPort SENSOR_DIST_REAR = SensorPort.S4;
	
	// Metrics
	public static final double WHEEL_DIAMETER = 1.0;
	public static final double WHEEL_DISTANCE = 6.0;
}
