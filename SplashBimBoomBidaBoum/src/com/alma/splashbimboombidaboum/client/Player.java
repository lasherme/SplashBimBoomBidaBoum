package com.alma.splashbimboombidaboum.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.alma.splashbimboombidaboum.server.RoomInterface;
import com.alma.splashbimboombidaboum.server.RoomReservationInterface;
import com.alma.splashbimboombidaboum.utility.Address;

public class Player extends UnicastRemoteObject implements PlayerInterface, Address {
	private String name;
	private RoomReservationInterface server;
	private RoomInterface room;

	public Player(String name) throws MalformedURLException, RemoteException, NotBoundException {
		this.name = name;

		this.server = (RoomReservationInterface) Naming.lookup("//" + PREADRRESS + ":" + PORT + "/" + POSTADDRESS);
		System.out.println("Connection established with server : \n\t" + this.server.toString());
		this.room = server.getRoom(this);
		System.out.println("Room reserved : \n\t" + this.room.toString());

	}
}
