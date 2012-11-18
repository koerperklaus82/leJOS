package com.marcel.lego.cfg;

import com.marcel.lego.behavior.ExitButtonPressedBehaviour;
import com.marcel.lego.behavior.ObstacleInFrontBehavior;
import com.marcel.lego.behavior.StraightAheadBehavior;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.objectdetection.FeatureDetector;
import lejos.robotics.objectdetection.RangeFeatureDetector;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Configuration {

	// Connections
	public static final RegulatedMotor MOTOR_LEFT = Motor.A;
	public static final RegulatedMotor MOTOR_RIGHT = Motor.B;
	
	public static final SensorPort SENSOR_PUSH = SensorPort.S1;
	public static final SensorPort SENSOR_SONAR = SensorPort.S2;
	public static final SensorPort SENSOR_LIGHT_LEFT_V1 = SensorPort.S3;
	public static final SensorPort SENSOR_LIGHT_RIGHT_V2 = SensorPort.S4;
	
	// Metrics
	public static final double WHEEL_DIAMETER = 1.0;
	public static final double WHEEL_DISTANCE = 6.0;
	
	public static Configuration instance = new Configuration();
	
	public final DifferentialPilot pilot;
	public final Navigator navigator;
	public final FeatureDetector sonar;
	public final Arbitrator arbi;
	
	private Configuration() {
		this.pilot = new DifferentialPilot(
				Configuration.WHEEL_DIAMETER, Configuration.WHEEL_DISTANCE, 
				Configuration.MOTOR_LEFT, Configuration.MOTOR_RIGHT, true);
		this.pilot.setTravelSpeed(2.0);
		this.pilot.setAcceleration(6);
		// this means using a OdometryPoseProvider (count wheel tracks)
		// alternatively MCLPoseProvider can be used but uses Distance tracking for navigation
		PoseProvider poseProvider = null;
		this.navigator = new Navigator(pilot, poseProvider);
		this.sonar = new RangeFeatureDetector(new UltrasonicSensor(SENSOR_SONAR), 50.0f /*cm*/, 500);
		
		Behavior[] behaviorList = new Behavior[] {
				new StraightAheadBehavior(),
				new ObstacleInFrontBehavior(),
				new ExitButtonPressedBehaviour()
		};
		this.arbi = new Arbitrator(behaviorList);
	}
	
	
}
