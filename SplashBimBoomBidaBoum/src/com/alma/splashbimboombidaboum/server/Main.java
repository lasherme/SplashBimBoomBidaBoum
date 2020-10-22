package com.alma.splashbimboombidaboum.server;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;

public class Main {

	public static void main(String[] args) {
		RoomReservationInterface server;
		
		System.out.println("Hello, I am the Server main !");
		try {
			server = new RoomReservation();
		} catch (RemoteException | MalformedURLException | AlreadyBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
