package com.getusroi.paas.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
public class ScriptService  {
	 static final Logger logger = LoggerFactory.getLogger(PAASGenericHelper.class);

	private static String USERNAME ="root"; // username for remote host
	 private static String PASSWORD ="bizruntime@123"; // password of the remote host
	 private static String host = "54.86.181.80"; // remote host address
	 private static int port=22;
	
	public  void callScript(String conainerName,String ip) throws InterruptedException, IOException {
		  logger.debug(".callScript method of ScriptService");
		  ProcessBuilder pb = new ProcessBuilder("/root/network.sh", conainerName, ip);
		   Process p = pb.start();
		   BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		   String line = null;
		   while ((line = reader.readLine()) != null)
		   {
		      logger.debug(line);
		   } 
}//end of method callScript
	 
	    
	
	 public  List<String> executeFile(String conainerName,String ip){
		 logger.debug(".executeFile method of ScriptService");
	     List<String> result = new ArrayList<String>();
	     try
	     {   /**
	         * Create a new Jsch object
	         * This object will execute shell commands or scripts on server
	         */
	         JSch jsch = new JSch();

	         /*
	         * Open a new session, with your username, host and port
	         * Set the password and call connect.
	         * session.connect() opens a new connection to remote SSH server.
	         * Once the connection is established, you can initiate a new channel.
	         * this channel is needed to connect to remotely execution program
	         */
	         Session session = jsch.getSession(USERNAME, host, port);
	         session.setConfig("StrictHostKeyChecking", "no");
	         session.setPassword(PASSWORD);
	         session.connect();

	         //create the excution channel over the session
	         ChannelExec channelExec = (ChannelExec)session.openChannel("exec");

	         // Gets an InputStream for this channel. All data arriving in as messages from the remote side can be read from this stream.
	         InputStream in = channelExec.getInputStream();

	         // Set the command that you want to execute
	         // In our case its the remote shell script
	         String  command="/root/network.sh"+conainerName+ip;
	         channelExec.setCommand("sh "+command);

	         // Execute the command
	         channelExec.connect();

	         // Read the output from the input stream we set above
	         BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	         String line;
	         
	         //Read each line from the buffered reader and add it to result list
	         // You can also simple print the result here 
	         while ((line = reader.readLine()) != null)
	         {
	             result.add(line);
	         }

	         //retrieve the exit status of the remote command corresponding to this channel
	         int exitStatus = channelExec.getExitStatus();

	         //Safely disconnect channel and disconnect session. If not done then it may cause resource leak
	         channelExec.disconnect();
	         session.disconnect();

	         if(exitStatus < 0){
	            logger.debug("Done, but exit status not set!");
	         }
	         else if(exitStatus > 0){
	        	 logger.debug("Done, but with error!");
	         }
	         else{
	        	 logger.debug("Done!");
	         }

	     }
	     catch(Exception e)
	     {
	         System.err.println("Error: " + e);
	     }
	     return result;
	 }//end of method executeFile

}
