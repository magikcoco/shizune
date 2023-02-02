package com.magikcoco.managers;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggingManager {

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    @SuppressWarnings("InstantiationOfUtilityClass")
    private final static LoggingManager INSTANCE = new LoggingManager();
    private static FileHandler fileHandler;

    private LoggingManager(){
        try{
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH;mm;ss@dd-MM-yyyy");
            Date date = new Date();
            File logFile = new File("log-"+timeFormat.format(date)+".log");
            boolean result = logFile.createNewFile();
            fileHandler = new FileHandler(logFile.getAbsolutePath());
            SimpleFormatter simpleFormatter = new SimpleFormatter();
            fileHandler.setFormatter(simpleFormatter);
            LOGGER.addHandler(fileHandler);
            LOGGER.setUseParentHandlers(false);
            if(result){
                LOGGER.log(Level.INFO, "New log file was made");
            } else {
                LOGGER.log(Level.WARNING, "No new log file was made");
            }
        } catch (IOException e){
            e.printStackTrace();
            System.exit(9004);
        }
    }

    /**
     * @return this LoggingManager
     */
    public static LoggingManager getInstance(){
        return INSTANCE;
    }

    /**
     * @param message the informational message that goes into the outputted log file
     * @return this LoggingManager
     */
    public static LoggingManager logInfo(String message){
        LOGGER.log(Level.INFO, message);
        return INSTANCE;
    }

    /**
     * @param message the warning message that goes into the outputted log file
     * @return this LoggingManager
     */
    public static LoggingManager logWarning(String message){
        LOGGER.log(Level.WARNING, message);
        return INSTANCE;
    }

    /**
     * @param message the error message that goes into the outputted log file
     * @return this LoggingManager
     */
    public static LoggingManager logError(String message){
        LOGGER.log(Level.SEVERE, message);
        return INSTANCE;
    }
}
