package com.amaljoyc;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ChatServer {

	private static final String QUIT = "quit";
	private static final int PORT = 4321;

	ServerSocket server;
	Socket socket;
	Scanner clientScanner;
	Scanner serverScanner;
	PrintStream clientPrinter;

	public static void main(String[] args) {
		System.out.println("Starting Chat Server...");
		new ChatServer().init();
	}

	private void init() {
		try {
			server = new ServerSocket(PORT);
			socket = server.accept();
			clientScanner = new Scanner(socket.getInputStream());
			serverScanner = new Scanner(System.in);
			clientPrinter = new PrintStream(socket.getOutputStream());

			String clientMessage = "";
			String serverMessage = "";
			while (true) {
				clientMessage = clientScanner.nextLine();
				System.out.print("Client: " + clientMessage);
				if (clientMessage.equals(QUIT)) {
					break;
				}

				System.out.print("\nServer: ");
				serverMessage = serverScanner.nextLine();
				clientPrinter.println(serverMessage);
				if (serverMessage.equals(QUIT)) {
					break;
				}
			}
			close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	private void close() throws IOException {
		clientPrinter.close();
		serverScanner.close();
		clientScanner.close();
		socket.close();
		server.close();
	}
}
