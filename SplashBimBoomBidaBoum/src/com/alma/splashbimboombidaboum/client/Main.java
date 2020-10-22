package com.alma.splashbimboombidaboum.client;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Main {
	
	public static void main(String[] args) {
		PlayerInterface player;
		
		System.out.println("Hello, I am the Client main !");
		try {
			player = new Player("toto");
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}

}
