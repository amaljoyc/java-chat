package com.amaljoyc;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

	private static final String QUIT = "quit";
	private static final int PORT = 4321;
	private static final String HOST = "127.1";

	Socket socket;
	Scanner clientScanner;
	Scanner serverScanner;
	PrintStream serverPrinter;

	public static void main(String[] args) {
		System.out.println("Starting Chat Client...");
		new ChatClient().init();
	}

	private void init() {
		try {
			socket = new Socket(HOST, PORT);
			clientScanner = new Scanner(System.in);
			serverScanner = new Scanner(socket.getInputStream());
			serverPrinter = new PrintStream(socket.getOutputStream());

			String clientMessage = "";
			String serverMessage = "";
			while (true) {
				System.out.print("\nClient: ");
				clientMessage = clientScanner.nextLine();
				serverPrinter.println(clientMessage);
				if (clientMessage.equals(QUIT)) {
					break;
				}

				serverMessage = serverScanner.nextLine();
				System.out.print("Server: " + serverMessage);
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
		serverPrinter.close();
		serverScanner.close();
		clientScanner.close();
		socket.close();
	}
}
