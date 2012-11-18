package com.marcel.lego;

import lejos.nxt.Button;

import com.marcel.lego.cfg.Configuration;



public class Test {
	
	
	private void test(float length) {
		System.out.println("Start Test Behaviour\nPress Key");
		Button.waitForAnyPress();
		
		Configuration.instance.arbi.start();
	}
	
	
	public static void main(String args[]) {
		Test test = new Test();
		test.test(20);
	}
}
