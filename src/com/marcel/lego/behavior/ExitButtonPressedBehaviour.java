package com.marcel.lego.behavior;

import com.marcel.lego.cfg.Configuration;

import lejos.nxt.Button;
import lejos.robotics.subsumption.Behavior;

public class ExitButtonPressedBehaviour implements Behavior {

	@Override
	public boolean takeControl() {
		if (Button.ESCAPE.isDown()) {
			return true;
		} return false;
	}

	@Override
	public void action() {
		Configuration.instance.pilot.stop();
		System.exit(0);
	}

	@Override
	public void suppress() {
	}

}
