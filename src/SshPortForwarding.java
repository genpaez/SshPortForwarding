import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.Properties;

import com.jcraft.jsch.*;


public class SshPortForwarding {   //// PorForwarding y ejecución vía PipedInput. Pendiente recuperar Output
	
	private static String username = "opegpae1";

	private static JSch jSch = new JSch();
	private static int forwardedPort;
	private static PrintStream out;
	static PipedOutputStream pin;
	static PipedInputStream pout;
	static String myCommand, user;
	 
	
	 public static void sesionA(){
		 
		 UserInterface ui = new UserInterface();
		 
		 try {
			Session sessionA = jSch.getSession(username, hostA, 22);  
			Properties config = new Properties(); 
	        config.put("StrictHostKeyChecking", "no");
	        sessionA.setConfig(config);
	        sessionA.setPassword(passwordA);
	        sessionA.connect();
	        
	        
	        if(sessionA.isConnected()) {
	        	System.out.println("Connected host A!");
	        	forwardedPort = 2222;
	        	sessionA.setPortForwardingL(forwardedPort, hostB, 22);		
	        }
			
		} catch (JSchException e) {
			e.printStackTrace();
		}
	 }
	 
	 public static void sesionB(){
		

			try {
				Session sessionB = jSch.getSession(username, "localhost", forwardedPort);

			Properties config = new Properties(); 
	        config.put("StrictHostKeyChecking", "no");
	        sessionB.setConfig(config);
	        sessionB.setPassword(passwordB);
			sessionB.connect();
				
		      if(sessionB.isConnected()) {
		         System.out.println("Connected host B!");
		         
		         Channel channel = sessionB.openChannel("shell");
		        // channel.setInputStream(System.in);
		       //  channel.setOutputStream(System.out);
		         
		            
		         
		         InputStream en = new PipedInputStream();
		         try {
					pin = new PipedOutputStream((PipedInputStream) en);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		         channel.setOutputStream(System.out);
		         channel.setInputStream(en);
		         channel.connect();
		         
		        /*    if(channel.isConnected()) { // escribe pero no ejecuta.
		            	
		            	try {
							out = new PrintStream(channel.getOutputStream());
							out.write("ping 5\n".getBytes());
							out.close();
					        out.flush();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
		            	
		            }*/
		            
			  }
		    } catch (JSchException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        
	 }
	 
	 

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		sesionA();
		sesionB();	

	}
	
	
	
	


	  
		public static void verPuerto(){
			
			try {		
				myCommand = "show port description 1/2/1 \n";
				pin.write(myCommand.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public static void verBGP() {
			
			try {
				myCommand = "show router 500151940 bgp summary neighbor 10.20.30.2 \n";
				pin.write(myCommand.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				try {
					Thread.sleep(500);
					myCommand = "x"; // Avoid buffer tail of command response
					pin.write(myCommand.getBytes());	
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public static void verInterfaz() {

			try {
				
				myCommand = "show service id 500151940 interface 10.20.30.1 \n";
				pin.write(myCommand.getBytes());

			} catch (IOException e) {
				e.printStackTrace();
			}

		   // readChannelOutput(channel);
		}

}
