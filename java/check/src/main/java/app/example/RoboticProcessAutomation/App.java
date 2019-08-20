package app.example.RoboticProcessAutomation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.client.ClientProtocolException;
import app.example.RoboticProcessAutomation.QRadar;
import org.json.simple.parser.ParseException;


/**
 * 
 * @author Rahul Reddy Ravipally
 *
 */

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

		scanner.close();

		final QRadar q = new QRadar(name, password, host_qradar);

		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				try {
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

			}

		};

		Timer timer = new Timer();
		long delay = 0;
		long intevalPeriod = 1 * 30000;

		timer.scheduleAtFixedRate(task, delay, intevalPeriod);

	} // end of main
}
