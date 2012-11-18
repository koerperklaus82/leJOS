package com.marcel.lego.behavior;

import com.marcel.lego.cfg.Configuration;

import lejos.robotics.objectdetection.Feature;
import lejos.robotics.subsumption.Behavior;

public class ObstacleInFrontBehavior implements Behavior {

	private boolean suppressed = false;
	private Feature feature;
	
	@Override
	public boolean takeControl() {
		Feature f = Configuration.instance.sonar.scan();
		this.feature = f;
		return f != null && f.getRangeReading().getRange() < 10.0f;
	}

	@Override
	public void action() {
		this.suppressed = false;
		Configuration.instance.pilot.stop();
		
		while(!suppressed && feature != null && feature.getRangeReading().getRange() < 15.0) {
			Configuration.instance.pilot.rotate(5.0, true);
			while (!suppressed && Configuration.instance.pilot.isMoving()) {
				Thread.yield();
			}
			
		}
	}

	@Override
	public void suppress() {
		this.suppressed = true;
	}

}
