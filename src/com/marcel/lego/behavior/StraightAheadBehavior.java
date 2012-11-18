package com.marcel.lego.behavior;

import com.marcel.lego.cfg.Configuration;

import lejos.robotics.subsumption.Behavior;

/**
 * Just runs straight ahead.
 * main behaviour which should always have control but has lowest prio.
 * @author U411045
 *
 */
public class StraightAheadBehavior implements Behavior {

	private boolean suppressed = false;
	
	@Override
	public boolean takeControl() {
		// main behaviour which should always have control but has lowest prio.
		return true;
	}

	@Override
	public void action() {
		this.suppressed = false;
		Configuration.instance.pilot.forward();
		while (!suppressed) {
			Thread.yield();
		}
		Configuration.instance.pilot.stop();
	}

	@Override
	public void suppress() {
		this.suppressed = true;
	}
	
}
 