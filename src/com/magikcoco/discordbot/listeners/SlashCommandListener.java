package com.magikcoco.discordbot.listeners;

import com.magikcoco.discordbot.handlers.ThreadHandler;
import com.magikcoco.managers.LoggingManager;
import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import net.dv8tion.jda.api.entities.channel.unions.GuildMessageChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.util.Objects;


public class SlashCommandListener extends ListenerAdapter {

    public SlashCommandListener(){
        LoggingManager.logInfo("SlashCommandListener has been created");
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        event.deferReply(true).queue();
        switch (event.getName()) {
            case "closethread":
                handleCloseThread(event);
                break;
            case "game":
                handleGame(event);
                break;
            case "ping":
                handlePing(event);
                break;
            default:
                handleDefaultCase(event);
                break;
        }
    }

    /**
     * handles the closethread slash command, which deletes the current active thread
     * @param event the SlashCommandInteractionEvent that triggered this interaction
     */
    private void handleCloseThread(SlashCommandInteractionEvent event) {
        if(isThread(event)){
            String name = event.getChannel().getName();
            Thread t = new Thread(()->{
                try{
                    event.getHook().sendMessage("Attempting to delete the thread...").complete();
                    GuildMessageChannelUnion parentChannel = event.getChannel().asThreadChannel().getParentMessageChannel();
                    ThreadHandler.removeThread(event.getChannel().asThreadChannel());
                    event.getChannel().asThreadChannel().delete().complete();
                    parentChannel.sendMessage("The thread formerly known as "+name+" is no more").queue();
                } catch (InsufficientPermissionException e) {
                    LoggingManager.logError("I couldn't delete a thread with id: " + event.getChannel().getId());
                    event.getChannel().asThreadChannel().sendMessage("I don't have permission to delete this thread").queue();
                }
            });
            t.start();
        } else {
            event.getHook().sendMessage("I can't do that here").queue();
        }
    }

    /**
     * handles the game slash command, which creates a new thread with the given name
     * @param event the SlashCommandInteractionEvent that triggered this interaction
     */
    private void handleGame(SlashCommandInteractionEvent event){
        if(isThreadAllowed(event)){
            Thread t = new Thread(()->{
                try{
                    ThreadChannel channel = event
                            .getChannel()
                            .asTextChannel()
                            .createThreadChannel(Objects.requireNonNull(event.getOption("threadname")).getAsString())
                            .complete();
                    ThreadHandler.addThread(channel);
                    //TODO: set up the thread
                    channel.sendMessage("I am a placeholder message for a button interaction").queue();
                    event.getHook().sendMessage("Game thread created!").queue();
                } catch (IllegalArgumentException e){
                    event.getHook()
                            .sendMessage("The ThreadName parameter was null, blank, empty, or longer than 100 characters. Please try again!")
                            .queue();
                } catch (UnsupportedOperationException e){
                    event.getHook().sendMessage("This command is unsupported in this location").queue();
                } catch (InsufficientPermissionException e){
                    event.getHook().sendMessage("I don't have permission to do that").queue();
                } catch (NullPointerException e) {
                    event.getHook().sendMessage("Something went wrong while making the thread").queue();
                }
            });
            t.start();
        } else {
            event.getHook().sendMessage("This command is unsupported in this location").queue();
        }
    }

    /**
     * handles the ping command
     * @param event a slashcommandinteraction event
     */
    private void handlePing(SlashCommandInteractionEvent event){
        event.getHook().sendMessage("Pong!").queue();
    }

    /**
     * handles an unrecognized command
     * @param event a slashcommandinteraction event
     */
    private void handleDefaultCase(SlashCommandInteractionEvent event){
        event.getHook().sendMessage("I did not recognize that command").queue();
    }

    /**
     * @param event a slashcommandinteraction event
     * @return true if the channel the event was made in is a text channel, false otherwise
     */
    private boolean isThreadAllowed(SlashCommandInteractionEvent event){
        try{
            event.getChannel().asTextChannel();
            return true;
        } catch(IllegalStateException e){
            return false;
        }
    }


    /**
     * @param event a slashcommandinteraction event
     * @return true if the current channel is a thread, false otherwise
     */
    private boolean isThread(SlashCommandInteractionEvent event){
        try{
            event.getChannel().asThreadChannel();
            return true;
        } catch(IllegalStateException e){
            return false;
        }
    }
}
