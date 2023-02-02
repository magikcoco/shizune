package com.magikcoco.main;

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
                System.out.println(absolutePath+connectionString);
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
