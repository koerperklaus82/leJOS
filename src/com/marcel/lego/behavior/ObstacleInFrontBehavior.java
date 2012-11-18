package com.marcel.lego.behavior;

import com.marcel.lego.cfg.Configuration;

import lejos.robotics.objectdetection.Feature;
import lejos.robotics.subsumption.Behavior;

public class ObstacleInFrontBehavior implements Behavior {

	private boolean suppressed = false;
	private float lastRange = 200;
	private int direction = 1;

	
	@Override
	public boolean takeControl() {
		Feature f = Configuration.instance.sonar.scan();
		this.lastRange = getRange();
		System.out.println("R: " + lastRange);
		return lastRange < 15;
	}

	@Override
	public void action() {
		this.suppressed = false;
		Configuration.instance.pilot.stop();
		Configuration.instance.pilot.setTravelSpeed(0.8);
		Configuration.instance.pilot.setAcceleration(3);
		while(!suppressed && lastRange < 20) {

			Configuration.instance.pilot.rotate(direction * 90, true);

			boolean changeDirection = false;
			
			while (!suppressed 
					&& Configuration.instance.pilot.isMoving() 
					&& !changeDirection) {
				Thread.yield();
				float newRange = getRange();
				System.out.println("R: " + newRange);
				changeDirection = newRange < lastRange;
				lastRange = newRange;
			}
			
			// if required change direction
			if (changeDirection) {
				direction = direction * -1;
				Configuration.instance.pilot.stop();
			} 
		}
		Configuration.instance.pilot.stop();
		Configuration.instance.pilot.setTravelSpeed(2.0);
		Configuration.instance.pilot.setAcceleration(3);
	}

	@Override
	public void suppress() {
		this.suppressed = true;
	}

	private float getRange() {
		Feature f = Configuration.instance.sonar.scan();
		float newRange =  f != null && f.getRangeReading() != null ? f.getRangeReading().getRange() : 200;
		return newRange;
	}
}
