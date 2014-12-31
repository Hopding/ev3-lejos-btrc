package com.AJD1.bluetoothrc;

import java.io.IOException;
import java.net.ServerSocket;

import lejos.hardware.Button;

//To be used in BluetoothRCServer class to monitor the state of 
//the escape button on the EV3 menu (down or up).
public class IsEscapeDownChecker extends Thread {
	ServerSocket socket;
	
	//Inside the constructor a ServerSocket is handed down.
	//This ServerSocket is the one that will be used in BluetoothRCServer
	//to receive commands from the PC.
	public IsEscapeDownChecker(ServerSocket serversocket) {
		socket = serversocket;
	}
	
	public void run() {
		while (true) {
			//If escape button is down the try/catch block is entered
			//and IsEscapeDownChecker attempts to close the ServerSocket.
			if (Button.ESCAPE.isDown()) {
				try {
					socket.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
	}

}
