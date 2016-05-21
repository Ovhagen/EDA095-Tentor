package buddies;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class BuddyList {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Error. No arguments");
			System.exit(1);
		}

		MulticastSocket ms = null;
		DatagramSocket dgSocket = null;
		BuddyWindow bw = new BuddyWindow();

		try {
			ms = new MulticastSocket(4099);
			InetAddress ia = InetAddress.getByName("experiment.mcast.net");
			ms.setTimeToLive(1);
			ms.joinGroup(ia);
			dgSocket = new DatagramSocket(30000);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		new BuddyRequester(ms, bw).start();
		new BuddyServer(ms, dgSocket, args[0]).start();
		new BuddyListener(bw, dgSocket).start();

	}

}
