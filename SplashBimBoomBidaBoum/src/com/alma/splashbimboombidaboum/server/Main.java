package com.alma.splashbimboombidaboum.server;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;

/**
 * Le main du serveur. 
 * 
 * @author Éloi Filaudeau, Louis Boursier, Nicolas Hawa, Loïc Lasherme
 * @version 1.0
 */
public class Main {

	static {
		System.out.println("Hello, I am the Server main !");
	}
	public static void main(String[] args) {
		RoomReservationInterface server;
		
		try {
			server = new RoomReservation();
		} catch (RemoteException | MalformedURLException | AlreadyBoundException e) {
			e.printStackTrace();
		}
	}

}
