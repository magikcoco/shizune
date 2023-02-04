package com.magikcoco.discordbot.listeners;

import com.magikcoco.managers.LoggingManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SlashCommandListener extends ListenerAdapter {

    //TODO: create a slash command /game which creates a new thread, and adds the initiator to the new thread as a member
    //TODO: create a method which determines what context a command has been given in
    //TODO: create a method which determines if it is possible to make a thread in the current location

    public SlashCommandListener(){
        LoggingManager.logInfo("SlashCommandListener has been created");
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        switch (event.getName()) {
            case "ping":
                handlePing(event);
                break;
            default:
                handleDefaultCase(event);
                break;
        }
    }

    /**
     * handles the ping command
     * @param event a slashcommandinteraction event
     */
    private void handlePing(SlashCommandInteractionEvent event){
        event.getHook().setEphemeral(true).sendMessage("Pong!").queue();
    }

    /**
     * handles an unrecognized command
     * @param event a slashcommandinteraction event
     */
    private void handleDefaultCase(SlashCommandInteractionEvent event){
        event.getHook().setEphemeral(true).sendMessage("I did not recognize that command").queue();
    }
}
