## VirusTotalBot

VirusTotalBot is a java program which connects to Discord to audit URL's. It passivly scans all websites for malware. If malware is found, the bot alerts the call. The bot sends URL's to the [VirusTotal](https://www.virustotal.com/#/home/upload) API which scans the website against around 65 antivirus services. 

### Requirements 

-Windows

-Java

-Discord

### Setup
The application is made in java and is started.
1. Create a application on the [Discord Website](https://discordapp.com/developers/applications/).
2. Copy the client id from the applications page and paste it in the client id field on [this website]ttps://discordapi.com/permissions.html). Give the bot admin, then add it to the call of your choice. 
3. Click on the bot tab inside the Discord applications setting and copy the token. 
4. Paste the token in the batch file after the jar.
    ex java -jar VirusTotalBot.jar asdfasdfasdfasdfasdfasdfasdf
5. Create an account on [VirusTotal](https://www.virustotal.com/#/home/upload).
6. Copy the [API key](https://www.virustotal.com/#/settings/apikey) and paste it in the LAUNCHER.bat after the Discord token with a space in between.

    ex: java -jar VirusTotalBot.jar DiscordBotToken VirusTotalApiKey

Done!
Double click the LAUNCHER.bat to start. 
If working correctly, the bot should show up inside the discord call. type help or ping in the call to test

Link to the API's used in the creation of this bot 

https://github.com/DV8FromTheWorld/JDA

https://github.com/B-V-R/VirusTotal-public-and-private-API-2.0-implementation-in-pure-Java
