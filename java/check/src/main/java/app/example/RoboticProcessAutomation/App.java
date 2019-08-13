package app.example.RoboticProcessAutomation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.client.ClientProtocolException;
import app.example.RoboticProcessAutomation.QRadar;
import org.json.simple.parser.ParseException;







public class App 
{
	public static void main(String[] args) {

		final ArrayList<String> ar = new ArrayList<String>();
		Scanner scanner = new Scanner(System.in);

		System.out.println("Enter your QRadar username:");
		final String name = scanner.nextLine();
		System.out.println("Enter your QRadar password:");
		final String password = scanner.nextLine();
		System.out.println("Enter your QRadar hostname/IP Address:");
		final String host_qradar = scanner.nextLine();

//		System.out.println("Enter your Resilient email:");
//		final String email = scanner.nextLine();
//		System.out.println("Enter your Resilient password:");
//		final String resilient_password = scanner.nextLine();
//		System.out.println("Enter your Resilient hostname/IP Address:");
//		final String host_resilient = scanner.nextLine();

		scanner.close();

		final QRadar q = new QRadar(name, password, host_qradar);

		TimerTask task = new TimerTask() {

			@Override
			public void run() {
//				ArrayList<String> s = null;
				try {
//					s = q.send_offences(ar);
					q.send_offences(ar);
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

//				if (!s.isEmpty()) {
//
//					for (int i = 0; i < s.size(); i++) {
//						ar.add(s.get(i));
//						System.out.println("Successfully sent offence id " + s.get(i) + " to resilient");
//						System.out.println("Offence ID's that are sent to resilient : " + ar);
//					}
//				} else {
//					System.out.println("Waiting for new offences");
//				}

			}

		};

		Timer timer = new Timer();
		long delay = 0;
		long intevalPeriod = 1 * 30000;

		timer.scheduleAtFixedRate(task, delay, intevalPeriod);

	} // end of main
}
