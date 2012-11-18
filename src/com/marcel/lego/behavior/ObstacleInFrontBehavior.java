package com.marcel.lego.behavior;

import com.marcel.lego.cfg.Configuration;

import lejos.robotics.objectdetection.Feature;
import lejos.robotics.subsumption.Behavior;

public class ObstacleInFrontBehavior implements Behavior {

	private boolean suppressed = false;
	private Feature lastFeature;
	private int direction = 1;

	
	@Override
	public boolean takeControl() {
		Feature f = Configuration.instance.sonar.scan();
		this.lastFeature = f;
		return f != null && f.getRangeReading().getRange() < 10.0f;
	}

	@Override
	public void action() {
		this.suppressed = false;
		Configuration.instance.pilot.stop();
		
		while(!suppressed && lastFeature != null && lastFeature.getRangeReading().getRange() < 15.0) {

			Configuration.instance.pilot.rotate(direction * 90);

			Feature newFeature = Configuration.instance.sonar.scan();
			while (!suppressed && Configuration.instance.pilot.isMoving() &&
							(newFeature == null || newFeature.getRangeReading().getRange() >= lastFeature.getRangeReading().getRange())) {
				Thread.yield();
				newFeature = Configuration.instance.sonar.scan();
			}
			
			// if required change direction
			if (newFeature.getRangeReading().getRange() < lastFeature.getRangeReading().getRange()) {
				direction = direction * -1;
			} else {
				direction = direction * 1;
			}
			this.lastFeature = newFeature;
		}
		Configuration.instance.pilot.stop();
	}

	@Override
	public void suppress() {
		this.suppressed = true;
	}

}
