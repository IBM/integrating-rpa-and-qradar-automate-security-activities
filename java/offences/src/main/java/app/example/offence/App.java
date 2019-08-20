package app.example.offence;

import java.io.IOException;
import java.util.Scanner;

import com.cloudbees.syslog.Facility;
import com.cloudbees.syslog.MessageFormat;
import com.cloudbees.syslog.Severity;
import com.cloudbees.syslog.sender.UdpSyslogMessageSender;

/**
 * 
 * @author Rahul Reddy Ravipally
 *
 */

public class App 
{
    public static void main( String[] args ) throws IOException
    {
    	Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the QRadar Hostname/IP Address ");
		String hostname = scanner.nextLine();
		
		// Initialise sender
		UdpSyslogMessageSender messageSender = new UdpSyslogMessageSender();
		messageSender.setDefaultMessageHostname("myhostname"); // some syslog cloud services may use this field to transmit a secret key
		messageSender.setDefaultAppName("myapp");
		messageSender.setDefaultFacility(Facility.USER);
		messageSender.setDefaultSeverity(Severity.INFORMATIONAL);
		messageSender.setSyslogServerHostname(hostname);
		messageSender.setSyslogServerPort(514);
		messageSender.setMessageFormat(MessageFormat.RFC_3164); // optional, default is RFC 3164

		// Send the offence
		messageSender.sendMessage("CARNUMBER: \"KA00MA1234\" SPEED: \"180KMPH\" VIOLATION: \"SPEEDING\"");
		System.out.println("Sending speed violation");
		System.out.println("Event sent successfully");

    }
}
