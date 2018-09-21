package maven.vtmaven;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JOptionPane;

import me.vighnesh.api.virustotal.InvalidURLException;
import me.vighnesh.api.virustotal.VirusTotalAPI;
import me.vighnesh.api.virustotal.dao.URLScanReport;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;


public class main extends ListenerAdapter
{

    private static final VirusTotalAPI virusTotal = null;

	public static void main( String[] args ) throws Exception
    {
    	String arg1 = args[0];
    	String arg2 = args[1];					
    	//Allows one line launch from cmd C:/dirrectoryOfTheJar/java -jar VirusTotalBot.jar DiscordBotToken VirusTotalApiKey
    	
    	JDA jda = new JDABuilder(AccountType.BOT).setToken(arg1).buildBlocking();
    	System.out.println("------------Connected to Discord----------");
    		
        jda.addEventListener(new main());
        VirusTotalAPI virusTotal = VirusTotalAPI.configure(arg2);
        System.out.println("-----------------------Connected to VitrusTotal-----------------------------");
        
    }
    
    public void onMessageReceived(MessageReceivedEvent evt)//scans all messages sent in call. 
    {
    	User obj = evt.getAuthor();
    	
    	MessageChannel objMsgCh = evt.getChannel();
    	Message objMsg = evt.getMessage();
    	if(objMsg.getContentRaw().equalsIgnoreCase("ping"))//test case	
    	{
    			objMsgCh.sendMessage(obj.getAsMention() + "Pong! ").queue();
    	}
    	if(objMsg.getContentRaw().equalsIgnoreCase("help"))//manual search
    	{
    		
    		objMsgCh.sendMessage(obj.getAsMention() +  "\nVirusTotalBot is a security tool to audit URL's integrity. It passively sends all url's in the call to VirusTotal's API. The API scans the websites with 69 antivirus applications. If the API detects malware, it alerts everyone on the server and leaves a link to a report with the positve results.\nUSAGE: scan link/url \nVirusTotalBot does not require input to be used.").queue();
    		System.out.println("\nsending help output\n");
    		
    	}
    	
    	if(objMsg.getContentRaw().contains("scan"))//manual search and scan 
    	{
    		String website = objMsg.getContentRaw().toString().substring(5);
    		try {
				URL url = new URL(website);
				virusTotal.scanURL(url);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
  
    		URLScanReport urlReport = virusTotal.getURLReport(website);	
    		
    		objMsgCh.sendMessage(obj.getAsMention() +  "\nScan Date : " +urlReport.getScanDate()+ "\nResponse Code : " + urlReport.getResponseCode()+ "\nScan ID : " + urlReport.getScanId()+ "\nPositives : " + urlReport.getPositives()+ "\nTotal : " + urlReport.getTotal() + "\nLink To Results : "+ urlReport.getPermalink()).queue();
    		System.out.println("\nsending manual scan outputI\n");
    	}
    	
    	if(objMsg.getContentRaw().contains("http"))//search for http and https links
    	{

    		String content = objMsg.getContentRaw().toString() + " ";
    		String parsedUrl = content.substring(content.indexOf("http"));//parses URL from message 
    		String finalUrl = parsedUrl.substring(0, parsedUrl.indexOf(' '));
    		URL URLform = null;int i =0;
			try {
				URLform = new URL(finalUrl);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				System.out.println("----------------------catch1-----------------------------");
				e.printStackTrace();
			}
    		URLScanReport urlReport = virusTotal.getURLReport(finalUrl);//scans VirusTotal for old data
    		if(urlReport.getResponseCode()==0)
    		{
    			System.out.println("\n#########Scanning URL : " + finalUrl +"#########\n");
    			
    			try {
					virusTotal.scanURL(URLform);
					
					try {
						virusTotal.wait(5000);
						
					}
					catch (InterruptedException e) {
						// TODO Auto-generated catch block
						System.out.print("----------------------catchTIMER-----------------------------");
						e.printStackTrace();
					}
				} catch (InvalidURLException e) {
					// TODO Auto-generated catch block
					System.out.println("----------------------catch2-----------------------------");
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("----------------------catch3-----------------------------");
					e.printStackTrace();
					
				}
    			URLScanReport urlReport2 = virusTotal.getURLReport(finalUrl);
    			if(urlReport2.getPositives() > i)
        		{
    				//Remove @everyone if you or other are getting annoyed by the amount of discord mentions.
    				objMsgCh.sendMessage("@everyone\n ⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠MALWARE IS DETECTED ON THE LINK ABOVE⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠\n●▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬๑۩  ۩๑▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬●\n" + urlReport2.getPositives() + " Antivirus services found malware \nA futher report can be found at the link below \n " + urlReport2.getPermalink() + "\n●▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬●").queue();
        			System.out.println("\nsending SCAN output from posititve scan\n");
        		}
    			else
    			{
    				System.out.println("\nscanned and found negative results\n");
    			}
    		}
    		
    		
    		
    		
    		if(urlReport.getPositives() > i)
    		{
    			//Remove @everyone if you or other are getting annoyed by the amount of discord mentions. 
    			objMsgCh.sendMessage(   "@everyone\n ⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠MALWARE IS DETECTED ON THE LINK ABOVE⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠\n●▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬๑۩  ۩๑▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬●\n" + urlReport.getPositives() + " Antivirus services found malware \nA futher report can be found at the link below \n " + urlReport.getPermalink() + "\n●▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬●").queue();
    			System.out.println("\nsending METADATA output from posititve scan\n");
    		}
    		else {
    			System.out.println("returned negative resulte from METADATA");
    		}
    		

    		
    	
    	}
    	
    	
    }
    
}
