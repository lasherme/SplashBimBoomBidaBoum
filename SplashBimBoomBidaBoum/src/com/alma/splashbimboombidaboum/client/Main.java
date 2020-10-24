package com.alma.splashbimboombidaboum.client;

import com.alma.splashbimboombidaboum.utility.RandomString;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) throws AlreadyBoundException {
		Player player;
		System.out.println("Hello, I am the Client main !");
		try {
			player = new Player(RandomString.generate(5));
			player.setIsReady();
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}
}
