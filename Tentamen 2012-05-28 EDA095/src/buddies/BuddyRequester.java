package buddies;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Date;

public class BuddyRequester extends Thread {

	private MulticastSocket ms;
	private BuddyWindow bw;

	public BuddyRequester(MulticastSocket ms, BuddyWindow bw) {
		this.bw = bw;
		this.ms = ms;

	}

	@Override
	public void run() {
		while (true) {
			bw.clear();
			System.out.println("Sending question");
			try {
				String message = "BUDDY?";
				byte[] buf = message.getBytes();
				InetAddress ia = InetAddress.getByName("experiment.mcast.net");
				DatagramPacket dp = new DatagramPacket(buf, buf.length, ia, 4099);
				ms.send(dp);

				sleep(6000);

			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}

		}
	}
}
