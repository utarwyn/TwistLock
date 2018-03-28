package twistlock.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientTest {
	public static void main(String args[]) throws Exception {
		DatagramSocket ds = new DatagramSocket();
		BufferedReader entreeClavier = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			String message = entreeClavier.readLine();

			DatagramPacket envoi = new DatagramPacket(message.getBytes(), message.length(), InetAddress.getByName("localhost"), 2684);
			ds.send(envoi);
			DatagramPacket msg = new DatagramPacket(new byte[1024], 1024);
			ds.receive(msg);

			System.out.println(new String(msg.getData()).trim());
		}
	}
}