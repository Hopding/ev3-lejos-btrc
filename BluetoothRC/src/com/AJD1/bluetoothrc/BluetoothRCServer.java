package com.AJD1.bluetoothrc;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import lejos.hardware.Sound;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;

//To be run on the EV3
public class BluetoothRCServer {
	public static void main(String args[]) throws IOException {
		
		//Creating various resources to be used in the class.
		int input;
		ServerSocket server = new ServerSocket(1111);
		EV3LargeRegulatedMotor motorA = new EV3LargeRegulatedMotor(MotorPort.A);
		EV3LargeRegulatedMotor motorB = new EV3LargeRegulatedMotor(MotorPort.B);
		EV3LargeRegulatedMotor motorC = new EV3LargeRegulatedMotor(MotorPort.C);
		
		//Setting up the IsEscapeDownChecker thread, and passing it the ServerSocket to be used.
		IsEscapeDownChecker isEscapeDown = new IsEscapeDownChecker(server);
		isEscapeDown.setDaemon(true);
		isEscapeDown.start();
		
		//Main loop of the program. The EV3 checks incoming data from the socket in the form of integers. 
		//Depending on the incoming integer the EV3 executes a action.
		while (true) {
			Socket socket;
			try {
				socket = server.accept();
			} catch (IOException e) {
				break;
			}
			DataInputStream in = new DataInputStream(socket.getInputStream());
			input = in.readInt();
			
			if (input == 1) {
				motorA.forward();
				motorB.forward();
			} 
			
			if (input == 2) {
				motorA.backward();
				motorB.backward();
			}
			
			if (input == 3) {
				motorA.backward();
				motorB.forward();
			}
			
			if (input == 4) {
				motorA.forward();
				motorB.backward();
			}
			
			if (input == 5) {
				motorA.stop();
				motorB.stop();
			}
			
			if (input == 6) {
				Sound.setVolume(100);
				Sound.buzz();
				server.close();
				motorA.close();
				motorB.close();
				System.exit(0);
			}
			
			if (input == 7) {
				Sound.setVolume(100);
				Sound.beep();
			}
			
			if (input == 8) { 
				motorC.forward();
			}
			
			if (input == 9) {
				motorC.stop();
			}
		}
		
		//If IsEscapeDown instance of IsEscapeDownChecker closes the socket connection, an error occurs and
		//the main program loop is broken out of to arrive at this point. Thus ending the program.
		Sound.setVolume(100);
		Sound.buzz();
		server.close();
		motorA.close();
		motorB.close();
		motorC.close();
		System.exit(0);
	}
}
