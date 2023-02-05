package com.magikcoco.discordbot.handlers;

import com.magikcoco.managers.DatabaseManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThreadHandler {

    //maps the threadID to another map containing info about it
    private static Map<String, HashMap<String, List<String>>> threadIdMap = new HashMap<>();

    public static void addThread(ThreadChannel threadChannel){
        HashMap<String, List<String>> threadInfo = new HashMap<>(); //hold the threadinfo
        List<String> memberIds= new ArrayList<>();
        for(Member member : threadChannel.getMembers()){
            memberIds.add(member.getId());
        }
        threadInfo.put("members", memberIds);
        threadIdMap.put(threadChannel.getId(), threadInfo);
        Map<String, Object> databaseMap = new HashMap<>();
        databaseMap.put(threadChannel.getId(), threadInfo);
        DatabaseManager.addDocument(databaseMap);
    }
}
