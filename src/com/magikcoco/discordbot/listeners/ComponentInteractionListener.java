package com.magikcoco.discordbot.listeners;

import com.magikcoco.game.data.LeavingEarth;
import com.magikcoco.game.engine.GameEngine;
import com.magikcoco.managers.DatabaseManager;
import com.magikcoco.managers.GameManager;
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
                DatabaseManager.appendToDocumentInActiveThreads(event.getChannel().asThreadChannel().getId(), "thread-type","chargen");
                break;
            case "shizune-ttrpg":
                event.reply("This is now a ttrpg thread, please select a game next")
                        .addActionRow(
                                StringSelectMenu.create("shizune-ttrpg-choosegame")
                                        .addOption("House Games Revised", "House Games Revised", "Play a game of house games revised")
                                        .build()
                        )
                        .queue();
                DatabaseManager.appendToDocumentInActiveThreads(event.getChannel().asThreadChannel().getId(), "thread-type","ttrpg");
                break;
            case "shizune-bg":
                event.reply("This is now a board game thread, please select a game next")
                        .addActionRow(
                                StringSelectMenu.create("shizune-bg-choosegame")
                                        .addOption("Leaving Earth", "Leaving Earth", "Play a game of leaving earth")
                                        .build()
                        )
                        .queue();
                DatabaseManager.appendToDocumentInActiveThreads(event.getChannel().asThreadChannel().getId(), "thread-type","bg");
                break;
        }
    }

    @Override
    public void onStringSelectInteraction(StringSelectInteractionEvent event) {
        switch(event.getComponentId()){
            case "shizune-chargen-choosegame":
                event.reply("This is now a chargen thread for "+event.getValues().get(0)).queue();
                DatabaseManager.appendToDocumentInActiveThreads(event.getChannel().asThreadChannel().getId(), "thread-game",event.getValues().get(0));
                break;
            case "shizune-ttrpg-choosegame":
                event.reply("This is now a TTRPG thread for "+event.getValues().get(0)).queue();
                DatabaseManager.appendToDocumentInActiveThreads(event.getChannel().asThreadChannel().getId(), "thread-game",event.getValues().get(0));
                gameEngineStart(event.getValues().get(0), event.getChannel().asThreadChannel().getId());
                break;
            case "shizune-bg-choosegame":
                event.reply("This is now a Board Game thread for "+event.getValues().get(0)).queue();
                DatabaseManager.appendToDocumentInActiveThreads(event.getChannel().asThreadChannel().getId(), "thread-game",event.getValues().get(0));
                gameEngineStart(event.getValues().get(0), event.getChannel().asThreadChannel().getId());
                break;
        }
    }

    private void gameEngineStart(String game, String threadId){
        switch(game){
            case "House Games Revised":
                GameManager.startGameInThread(threadId, new GameEngine(new int[]{5000, 5000, 5000}));
                break;
            case "Leaving Earth":
                GameManager.startGameInThread(threadId, new GameEngine(new int[]{LeavingEarth.Xdim, LeavingEarth.Ydim}));
                break;
        }
    }
}
