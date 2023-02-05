package com.magikcoco.main;

import com.magikcoco.managers.BotManager;
import com.magikcoco.managers.DatabaseManager;
import com.magikcoco.managers.LoggingManager;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        if(args.length > 0){
            int argCounter = 0;
            String absolutePath = "";
            String connectionString = "";
            String databaseName = "";
            for(String arg : args){
                if(arg.toLowerCase().startsWith("-t")){
                    argCounter++;
                    absolutePath = arg.substring(2);
                } else if(arg.toLowerCase().startsWith("-d")){
                    argCounter++;
                    connectionString = arg.substring(2);
                } else if(arg.toLowerCase().startsWith("-n")){
                    argCounter++;
                    databaseName = arg.substring(2);
                }
            }
            if(argCounter == 3){
                try {
                    DatabaseManager.start(connectionString.replace('"',' ').strip(),databaseName.replace('"',' ').strip());
                    Path filePath = Paths.get(absolutePath);
                    BotManager.start(new String(Files.readAllBytes(filePath)));
                } catch (InvalidPathException e) {
                    System.exit(9002);
                } catch (IOException e){
                    System.exit(9003);
                } catch (IllegalArgumentException e){
                    System.exit(9005);
                }
            } else {
                System.out.println("Use: -t\"absolute/path/to/plaintext/tokenfile\" -d\"mongoDB-connection-string\" -n\"databasename\"");
                System.exit(9001);
            }
        } else {
            System.out.println("Use: -t\"absolute/path/to/plaintext/tokenfile\" -d\"mongoDB-connection-string\" -n\"databasename\"");
            System.exit(9000);
        }
        //start a shell
        Thread t = new Thread(() -> {
            System.out.println("Shell starting...");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                LoggingManager.logError("Shell thread was interrupted");
            }
            System.out.println("Shell started!");
            Scanner sc = new Scanner(System.in);
            while(true){
                System.out.print(">> ");
                String command = sc.next();
                switch(command.toLowerCase().strip()){
                    case "exit":
                        LoggingManager.logInfo("Exit called from terminal");
                        BotManager.endConnection();
                        DatabaseManager.endConnection();
                        System.exit(0);
                        break;
                    case "help":
                        System.out.println("Available commands:\nhelp - print this menu\nexit - exits the program");
                        break;
                    default:
                        System.out.println("Unknown command:\nexit - exits the program");
                }
            }
        });
        t.start();
    }
}
