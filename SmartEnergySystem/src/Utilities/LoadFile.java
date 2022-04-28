package Utilities;

import Model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class LoadFile {

    /* Function that loads the data of a file to the system */
    public static Model loadData(String file) throws ClassNotFoundException {
        Model model = new Model();
        List<String> lines = LoadFile.readFile(file);

        for(String line : lines){
            LoadFile.createData(line,model);
        }
        return model;
    }

    /* Auxiliary function that passes all lines of a file to a list of strings*/
    public static List<String> readFile(String file) {
        List<String> lines = new ArrayList<>();
        String line;

        try {
            BufferedReader inFile = new BufferedReader(new FileReader(file));
            while ((line = inFile.readLine()) != null) lines.add(line);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return lines;
    }

    /* Auxiliary function that create the objects of the system */
    public static void createData(String line, Model model) throws ClassNotFoundException {
        String[] device = line.split(":");
        String[] args = device[1].split(",");;

        switch(device[0]){
            case "SmartBulb":
                SmartBulb smartBulb = LoadFile.buildSmartBulb(args);
                if(smartBulb != null) model.add(smartBulb);
                else System.out.print("Invalid SmartBulb");
                break;

            case "SmartSpeaker":
                SmartSpeaker smartSpeaker = LoadFile.buildSmartSpeaker(args);
                if(smartSpeaker != null) model.add(smartSpeaker);
                else System.out.print("Invalid SmartSpeaker");
                break;

            case "SmartCamera":
                SmartCamera smartCamera = LoadFile.buildSmartCamera(args);
                if(smartCamera != null) model.add(smartCamera);
                else System.out.print("Invalid SmartCamera");
                break;

            case "Supplier":
                Supplier supplier = LoadFile.buildSupplier(args);
                if(supplier != null) model.add(supplier);
                else System.out.print("Invalid Supplier");
                break;
            default:
                break;
        }
    }

    /* Function that builds a smartBulb from an array of strings
     */
    public static SmartBulb buildSmartBulb(String[] args){
        String ID;
        boolean on;
        int tone;
        LocalDateTime timeOfTurningOn;
        double dimension, consumption, installationCost;


        try{
            ID = args[0];
            on = Boolean.parseBoolean(args[1]);
            timeOfTurningOn = LocalDateTime.parse(args[2]);
            tone = Integer.parseInt(args[3]);
            dimension = Double.parseDouble(args[4]);
            consumption = Double.parseDouble(args[5]);
            installationCost = Double.parseDouble(args[6]);
        }

        catch (InputMismatchException | NumberFormatException e){
            return null;
        }

        return new SmartBulb(ID,on,timeOfTurningOn,tone,dimension,consumption,installationCost);
    }

    /* Function that builds a smartSpeaker from an array of strings */
    public static SmartSpeaker buildSmartSpeaker(String[] args){
        String ID, channel, brand;
        double volume, consumption, installationCost;
        LocalDateTime timeOfTurningOn;
        boolean on;

        try{
            ID = args[0];
            on = Boolean.parseBoolean(args[1]);
            timeOfTurningOn = LocalDateTime.parse(args[2]);
            channel = args[3];
            brand = args[4];
            volume = Double.parseDouble(args[5]);
            consumption = Double.parseDouble(args[6]);
            installationCost = Double.parseDouble(args[7]);
        }

        catch (InputMismatchException | NumberFormatException e){
            return null;
        }

        return new SmartSpeaker(ID,on,timeOfTurningOn,channel,brand,volume,consumption,installationCost);
    }

    /* Function that builds a smartCamera from an array of strings */
    public static SmartCamera buildSmartCamera(String[] args){
        String ID;
        boolean on;
        LocalDateTime timeOfTurningOn;
        int resolutionX, resolutionY;
        double fileSize, consumption,installationCost;

        try{
            ID = args[0];
            on = Boolean.parseBoolean(args[1]);
            timeOfTurningOn = LocalDateTime.parse(args[2]);
            resolutionX = Integer.parseInt(args[3]);
            resolutionY = Integer.parseInt(args[4]);
            fileSize = Double.parseDouble(args[5]);
            consumption = Double.parseDouble(args[6]);
            installationCost = Double.parseDouble(args[7]);
        }

        catch (InputMismatchException | NumberFormatException e){
            return null;
        }

        return new SmartCamera(ID,on,timeOfTurningOn,resolutionX,resolutionY,fileSize,consumption,installationCost);
    }

    /* Function that builds a smartCamera from an array of strings */
    public static Supplier buildSupplier(String[] args){
        String ID;
        double energyDailyCost;

        try{
            ID = args[0];
            energyDailyCost = Double.parseDouble(args[1]);
        }

        catch (InputMismatchException | NumberFormatException e){
            return null;
        }

        return new Supplier(ID,energyDailyCost);
    }



}
