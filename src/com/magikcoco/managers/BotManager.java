package com.magikcoco.managers;

import com.magikcoco.discordbot.listeners.ComponentInteractionListener;
import com.magikcoco.discordbot.listeners.SlashCommandListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class BotManager {

    @SuppressWarnings("InstantiationOfUtilityClass")
    private static final BotManager INSTANCE = new BotManager();
    private static boolean isStarted;
    private static JDA jda;

    private BotManager(){
        isStarted = false;
    }

    /**
     * @param token the login token used to login to the bot account
     * @return this BotManager
     */
    public static BotManager start(String token){
        //TODO: handle login failure case
        if(!isStarted){
            jda = JDABuilder.createDefault(token) //token passed to method
                    .setActivity(Activity.playing("Tabletop Games")) //activity displayed under bot in server
                    .build();
            addSlashCommands();
            addListeners();
        }
        return INSTANCE;
    }

    public static BotManager endConnection(){
        jda.shutdownNow();
        return INSTANCE;
    }

    private static void addSlashCommands(){
        jda.updateCommands().addCommands(
                Commands.slash("ping", "Pong!")
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MESSAGE_SEND)),
                Commands.slash("game", "Create a thread to set up and play a board game")
                        .addOption(OptionType.STRING, "threadname", "The name of the thread that will be created", true, false)
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.CREATE_PUBLIC_THREADS)),
                Commands.slash("closethread", "Deletes this game thread")
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MESSAGE_SEND))
        ).queue();
        LoggingManager.logInfo("Added Slash Commands to the bot");
    }

    private static void addListeners(){
        jda.addEventListener(new SlashCommandListener());
        jda.addEventListener(new ComponentInteractionListener());
    }
}
