package com.magikcoco.discordbot.listeners;

import com.magikcoco.managers.LoggingManager;
import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
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
        event.deferReply().queue();
        switch (event.getName()) {
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

    private void handleGame(SlashCommandInteractionEvent event){
        if(isThreadAllowed(event)){
            Thread t = new Thread(()->{
                try{
                    ThreadChannel channel = event
                            .getChannel()
                            .asTextChannel()
                            .createThreadChannel(Objects.requireNonNull(event.getOption("ThreadName")).getAsString())
                            .complete();
                    channel.addThreadMember(Objects.requireNonNull(event.getMember())).queue();
                    //TODO: set up the thread
                    event.getHook().setEphemeral(true).sendMessage("Game thread created!").queue();
                } catch (IllegalArgumentException e){
                    event.getHook()
                            .setEphemeral(true)
                            .sendMessage("The ThreadName parameter was null, blank, empty, or longer than 100 characters. Please try again!")
                            .queue();
                } catch (UnsupportedOperationException e){
                    event.getHook().setEphemeral(true).sendMessage("This command is unsupported in this location").queue();
                } catch (InsufficientPermissionException e){
                    event.getHook().setEphemeral(true).sendMessage("I don't have permission to do that").queue();
                } catch (NullPointerException e) {
                    event.getHook().setEphemeral(true).sendMessage("Something went wrong while making the thread").queue();
                }
            });
            t.start();
        } else {
            event.getHook().setEphemeral(true).sendMessage("This command is unsupported in this location").queue();
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

    private boolean isThreadAllowed(SlashCommandInteractionEvent event){
        try{
            event.getChannel().asTextChannel();
            return true;
        } catch(IllegalStateException e){
            return false;
        }
    }
}
