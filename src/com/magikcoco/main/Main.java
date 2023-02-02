package com.magikcoco.main;

import com.magikcoco.managers.BotManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    //TODO: basic shell for exit, help, information commands
    //TODO: multithread functionality

    public static void main(String[] args) {
        if(args.length > 0){
            int argCounter = 0;
            String absolutePath = "";
            String connectionString = "";
            for(String arg : args){
                if(arg.toLowerCase().startsWith("-t")){
                    argCounter++;
                    absolutePath = arg.substring(2);
                } else if(arg.toLowerCase().startsWith("-d")){
                    argCounter++;
                    connectionString = arg.substring(2);
                }
            }
            if(argCounter == 2){
                //TODO: query database on startup to load information
                //TODO: read tokenfile and pass it to a BotManager
                try {
                    Path filePath = Paths.get(absolutePath);
                    BotManager.start(new String(Files.readAllBytes(filePath)));
                } catch (InvalidPathException e) {
                    System.exit(9002);
                } catch (IOException e){
                    System.exit(9003);
                }
            } else {
                System.out.println("Use: -t\"absolute/path/to/plaintext/tokenfile\" -d\"mongoDB-connection-string\"");
                System.exit(9001);
            }
        } else {
            System.out.println("Use: -t\"absolute/path/to/plaintext/tokenfile\" -d\"mongoDB-connection-string\"");
            System.exit(9000);
        }
    }
}
