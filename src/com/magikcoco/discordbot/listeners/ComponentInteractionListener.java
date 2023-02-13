package com.magikcoco.discordbot.listeners;

import com.magikcoco.managers.DatabaseManager;
import com.magikcoco.managers.LoggingManager;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

public class ComponentInteractionListener extends ListenerAdapter {

    public ComponentInteractionListener(){
        LoggingManager.logInfo("ComponentInteractionListener is created");
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        switch(event.getComponentId()){
            case "shizune-chargen":
                event.reply("This is now a chargen thread, please select a game next")
                        .addActionRow(
                                StringSelectMenu.create("shizune-chargen-choosegame")
                                        .addOption("House Games Revised", "House Games Revised", "Build a character for house games")
                                        .build()
                        )
                        .queue();
                break;
            case "shizune-ttrpg":
                event.reply("This is now a ttrpg thread, please select a game next")
                        .addActionRow(
                                StringSelectMenu.create("shizune-ttrpg-choosegame")
                                        .addOption("House Games Revised", "House Games Revised", "Play a game of house games revised")
                                        .build()
                        )
                        .queue();
                break;
            case "shizune-bg":
                event.reply("This is now a board game thread, please select a game next")
                        .addActionRow(
                                StringSelectMenu.create("shizune-bg-choosegame")
                                        .addOption("Leaving Earth", "Leaving Earth", "Play a game of leaving earth")
                                        .build()
                        )
                        .queue();
                break;
        }
    }

    @Override
    public void onStringSelectInteraction(StringSelectInteractionEvent event) {
        switch(event.getComponentId()){
            case "shizune-chargen-choosegame":
                event.reply("This is now a chargen thread for "+event.getValues().get(0)).queue();
                DatabaseManager.appendToDocumentInActiveThreads(event.getChannel().asThreadChannel().getId(), "thread-type","chargen");
                DatabaseManager.appendToDocumentInActiveThreads(event.getChannel().asThreadChannel().getId(), "thread-game",event.getValues().get(0));
                break;
            case "shizune-ttrpg-choosegame":
                break;
            case "shizune-bg-choosegame":
                break;
        }
    }
}
