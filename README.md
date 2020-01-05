![Moody Logo](/UIChatBot/src/com/ui/MoodyBotXS.png)
# Moody Bot
Discord chat bot with mood identifier written in Java.


## Project Description
Moody Bot Application is a Discord chat bot Java Application that includes two 2 applications. First is the Bot Applications, second is the UI controller to configure bot’s settings. When both applications are running at the same time they are connected via TCP.

**Moody Bot Application** is a console app that configures the bot and connects to Discord via web socket using JDA library. The app will be listening for the events that will be coming from the chat and perform different action if the command matches one of the existed commands. All the commands are manually configured in a separate class that extends ListenerAdapter (JDA class). **UI App** gives you the ability to configure bot and see statistics that is saved to the database (MySql).

To analyse messages, I used a free and open source [Stanford Natural Language Processing](https://stanfordnlp.github.io/CoreNLP/) library. Stanford CoreNLP provides a set of human language technology tools. It can give the base forms of words, their parts of speech, whether they are names of companies, people, etc. 

## Chat Commands Overview
- **~info** – general information about the bot that shows all the available commands and their short description. Very helpful for new members.
- **~mood [message]** – identifies sentiments using provided message and sends a random response message with the mood identified.
- **~chat statistics** – shows the overall chat mood and value representation
- **~my statistics** – personal mood statistics, send a private message with a response
- **~UpdateActivity** – updates watching activity from the latest bot configuration
- **~HellMode?** – shows current Hell Mode state (on/off)
- **~HellMode [on/off]** – turn on/off Hell Mode (admin only)

##   
*More project details can be fount in the Documentation folder as a [PDF file document](/Documentation/MoodyBotDocumentation-EricaMoisei.pdf) and a [Power Point Presentation](/Documentation/MoodyBot-Presentation.pptx)*.
