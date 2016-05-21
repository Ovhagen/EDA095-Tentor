package buddies;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.MulticastSocket;

public class BuddyListener extends Thread {

	private BuddyWindow bw;
	private DatagramSocket dgSocket;

	public BuddyListener(BuddyWindow bw, DatagramSocket dgSocket) {
		this.bw = bw;
		this.dgSocket = dgSocket;
	}

	@Override
	public void run() {
		try {
			while (true) {
				byte[] buf = new byte[65536];
				DatagramPacket dp = new DatagramPacket(buf, buf.length);
				dgSocket.receive(dp);
				if(dp.getLength() > 1){
					String nick = new String(dp.getData(), 0, dp.getLength(), "UTF-8");
					System.out.println("Received: " + nick);
					nick = nick + " (" + dp.getAddress().getHostName() + ")";
					bw.addBuddy(nick);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

}
