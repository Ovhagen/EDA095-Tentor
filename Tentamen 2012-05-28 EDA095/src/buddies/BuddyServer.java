package buddies;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.Date;

public class BuddyServer extends Thread {

	private MulticastSocket ms;
	private DatagramSocket dgSocket;
	private InetAddress ia;
	private String nick;

	public BuddyServer(MulticastSocket ms, DatagramSocket dgSocket, String nick) {
		this.ms = ms;
		this.dgSocket = dgSocket;
		this.nick = nick;
	}

	@Override
	public void run() {
		while (true) {
			try {
				byte[] buf = new byte[65536];
				DatagramPacket dp = new DatagramPacket(buf, buf.length, ia, 4099);
				ms.receive(dp);

				String text = new String(dp.getData(), 0, dp.getLength(), "UTF-8");
				System.out.println("Received: " + text);
				if (text.equals("BUDDY?")) {
					byte[] sbuf = nick.getBytes();
					DatagramPacket sdp = new DatagramPacket(sbuf, sbuf.length, dp.getAddress(),
							dgSocket.getLocalPort());
					dgSocket.send(sdp);
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}

}
