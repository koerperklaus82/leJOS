package com.marcel.lego;

import lejos.nxt.Button;

import com.marcel.lego.move.MoveController;



public class Test {
	
	
	private void test(float length) {
		System.out.println("Start Test Color\nPress Key");
		Button.waitForAnyPress();
		
		final MoveController moveController = new MoveController();
		moveController.start();
		
		Button.waitForAnyPress();
		moveController.stop();
		
		System.out.println("End Test Color - Press Key");
		Button.waitForAnyPress();
	}
	
	
	public static void main(String args[]) {
		Test test = new Test();
		test.test(20);
	}
}
