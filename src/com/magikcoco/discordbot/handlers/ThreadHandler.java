package com.magikcoco.discordbot.handlers;

import com.magikcoco.managers.DatabaseManager;
import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import java.util.HashMap;

public class ThreadHandler {


    /**
     * adds a thread to a mongoDB database
     * @param threadChannel the thread to add to the database
     */
    public static void addThread(ThreadChannel threadChannel){
        HashMap<String, Object> threadInfo = new HashMap<>();
        threadInfo.put("thread-id", threadChannel.getId());
        DatabaseManager.addDocumentToActiveThreads(threadInfo);
    }

    /**
     * removes a thread from the database
     * @param threadChannel the thread to remove from the database
     */
    public static void removeThread(ThreadChannel threadChannel){
        HashMap<String, Object> threadInfo = new HashMap<>();
        threadInfo.put("thread-id", threadChannel.getId());
        DatabaseManager.removeDocumentFromActiveThreads(threadInfo);
    }
}
