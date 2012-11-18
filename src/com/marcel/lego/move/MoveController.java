package com.marcel.lego.move;

import lejos.nxt.Sound;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;

import com.marcel.lego.cfg.Setup;
import com.marcel.lego.sensor.ColorChangeDetector;
import com.marcel.lego.sensor.ColorChangeDetectorV1;
import com.marcel.lego.sensor.ColorChangeDetectorV2;
import com.marcel.lego.sensor.ColorFilter;
import com.marcel.lego.sensor.ColorFoundListener;
import com.marcel.lego.util.Color;

public class MoveController {
	
	private final Navigator navigator;
	private final DifferentialPilot pilot;
	private final ColorChangeDetector colorSensorLeft;
	private final ColorChangeDetector colorSensorRight;
	
	private int leftColor;
	private int rightColor;
	private boolean wasLastKnownBlackRight = true;
	
	public MoveController() {
		this.pilot = new DifferentialPilot(Setup.WHEEL_DIAMETER, Setup.WHEEL_DISTANCE, Setup.MOTOR_LEFT, Setup.MOTOR_RIGHT);
		this.pilot.setTravelSpeed(0.7);
		// this means using a OdometryPoseProvider (count wheel tracks)
		// alternatively MCLPoseProvider can be used but uses Distance tracking for navigation
		PoseProvider poseProvider = null;
		this.navigator = new Navigator(pilot, poseProvider);
		
		// setup sensors
		this.colorSensorLeft = new ColorChangeDetectorV1(Setup.SENSOR_LIGHT_LEFT_V1, 500);
		this.colorSensorRight = new ColorChangeDetectorV2(Setup.SENSOR_LIGHT_RIGHT_V2, true, false, 500);
		
		this.colorSensorLeft.addListener(new ColorFoundListener(new ColorFilter(Color.BLACK)) {
			@Override
			public void handleFilterColorDetected(Color color) {
				leftColor = Color.BLACK;
				wasLastKnownBlackRight = false;
				checkAndGo();
			}
		});
		
		this.colorSensorLeft.addListener(new ColorFoundListener(new ColorFilter(Color.WHITE)) {
			@Override
			public void handleFilterColorDetected(Color color) {
				leftColor = Color.WHITE;
				wasLastKnownBlackRight = false;
				checkAndGo();
			}
		});
		
		this.colorSensorRight.addListener(new ColorFoundListener(new ColorFilter(Color.BLACK)) {
			@Override
			public void handleFilterColorDetected(Color color) {
				rightColor = Color.BLACK;
				wasLastKnownBlackRight = true;
				checkAndGo();
			}
		});

		this.colorSensorRight.addListener(new ColorFoundListener(new ColorFilter(Color.WHITE)) {
			@Override
			public void handleFilterColorDetected(Color color) {
				rightColor = Color.WHITE;
				wasLastKnownBlackRight = false;
				checkAndGo();
			}
		});
	}
	
	private void checkAndGo() {

	}
	
	public void start() {
		checkAndGo();
	}
	
	public void stop() {
		pilot.stop();
	}
}
