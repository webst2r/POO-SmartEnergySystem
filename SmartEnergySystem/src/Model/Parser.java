package Model;

import Exceptions.LinhaIncorretaException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private String path;

    public Parser(){
        this.path = "SmartEnergySystem/data/logs.txt";
    }


    /**
     * Função que permite guardar o estado do modelo num ficheiro binário
     */
    public void saveBin(String nomeFicheiro,Model model) throws IOException {
        FileOutputStream bf = new FileOutputStream(nomeFicheiro);
        ObjectOutputStream oos = new ObjectOutputStream(bf);
        oos.writeObject(model);
        oos.flush();
        oos.close();
    }

    /**
     * Função que permite ler um ficheiro binário com um estado da aplicação
     */
    public Model readBin(String nomeFich) throws IOException, ClassNotFoundException{
        FileInputStream bf = new FileInputStream(nomeFich);
        ObjectInputStream ois = new ObjectInputStream(bf);
        Model m = (Model) ois.readObject();
        ois.close();
        return m;
    }

    public void parse(Model model) throws LinhaIncorretaException {
        List<String> linhas = lerFicheiro(getPath());
        SmartHouse ultimaCasa = null;
        String ultimaDivisao = null;
        List<SmartDevice> dispositivos = new ArrayList<>();

        for (String linha : linhas) {
            String[] entitie = linha.split(":");
            String[] args = entitie[1].split(",");;

            switch (entitie[0]){
                case "Fornecedor":
                    Supplier supplier = buildSupplier(args);
                    if(supplier != null) model.add(supplier);
                    else System.out.println("Invalid supplier!");
                    break;
                case "Casa":
                    SmartHouse h = buildSmartHouse(args);
                    if(ultimaCasa == null){
                        ultimaCasa = h;
                    }
                    else if (h != null) {
                        if(ultimaDivisao != null){
                            ultimaCasa.addRoom(ultimaDivisao,dispositivos);
                            dispositivos.clear();
                        }
                        model.add(ultimaCasa);
                        ultimaCasa = h;
                    } else System.out.println("Invalid house!");
                    break;
                case "Divisao":
                    if(dispositivos.size() > 0){
                        ultimaCasa.addRoom(ultimaDivisao,dispositivos);
                        dispositivos.clear();
                    }
                    ultimaDivisao = args[0];

                    break;
                case "SmartBulb":
                    SmartBulb bulb = buildSmartBulb(args);
                    if(bulb != null){
                        model.add(bulb);
                        dispositivos.add(bulb);
                    }
                    else System.out.println("Invalid BULB!");
                    break;
                case "SmartCamera":
                    SmartCamera camera = buildSmartCamera(args);
                    if(camera != null) {
                        model.add(camera);
                        dispositivos.add(camera);
                    }
                    else System.out.println("Invalid CAMERA!");
                    break;
                case "SmartSpeaker":
                    SmartSpeaker speaker = buildSmartSpeaker(args);
                    if(speaker != null) {
                        model.add(speaker);
                        dispositivos.add(speaker);
                    }
                    else System.out.println("Invalid SPEAKER!");
                    break;
            }
        }
        ultimaCasa.addRoom(ultimaDivisao,dispositivos);
        model.add(ultimaCasa);
    }


    public static List<String> lerFicheiro(String nomeFich) {
        List<String> lines;
        try { lines = Files.readAllLines(Paths.get(nomeFich), StandardCharsets.UTF_8); }
        catch(IOException exc) { lines = new ArrayList<>(); }
        return lines;
    }


    /* Build SmartHouse*/
    public static SmartHouse buildSmartHouse(String[] args){
        //Vicente de Carvalho Castro,365597405,Iberdrola
        String owner, supplier;
        int NIF;

        try{
            owner = args[0];
            NIF = Integer.parseInt(args[1]);
            supplier = args[2];
        }

        catch (InputMismatchException | NumberFormatException e){
            return null;
        }

        return new SmartHouse(owner,NIF,supplier);
    }



    /* Function that builds a smartBulb from an array of strings
     */
    public static SmartBulb buildSmartBulb(String[] args){
        //Warm,15,5.05
        int tone = 1, diameter;
        double consumption;


        try{
            switch (args[0]){
                case "Warm":
                    tone = 2;
                    break;
                case "Neutral":
                    tone = 1;
                    break;
                case "Cold":
                    tone = 0;
                    break;
            }
            diameter = Integer.parseInt(args[1]);
            consumption = Double.parseDouble(args[2]);
        }

        catch (InputMismatchException | NumberFormatException e){
            return null;
        }

        return new SmartBulb(tone,diameter,consumption);
    }


    public static SmartSpeaker buildSmartSpeaker(String[] args){
        String channel, brand;
        double volume, consumption;

        try{
            volume = Double.parseDouble(args[0]);
            channel = args[1];
            brand = args[2];
            consumption = Double.parseDouble(args[3]);
        }

        catch (InputMismatchException | NumberFormatException e){
            return null;
        }

        return new SmartSpeaker(channel,brand,volume,consumption);
    }


    public static SmartCamera buildSmartCamera(String[] args){
        int resolutionX = 0, resolutionY = 0;
        double fileSize, consumption;

        try{
            Pattern p = Pattern.compile("(\\d+)x(\\d+)");
            Matcher m = p.matcher(args[0]);
            int i = 0;
            if(m.find()) {
                resolutionX = (Integer.parseInt(m.group(1)));
                resolutionY = (Integer.parseInt(m.group(2)));
            }

            fileSize = Double.parseDouble(args[1]);
            consumption = Double.parseDouble(args[2]);
        }

        catch (InputMismatchException | NumberFormatException e){
            return null;
        }


        return new SmartCamera(resolutionX,resolutionY,fileSize,consumption);
    }

    public static Supplier buildSupplier(String[] args){
        String ID;
        try{
            ID = args[0];
        }
        catch (InputMismatchException | NumberFormatException e){
            return null;
        }

        return new Supplier(ID);
    }


    public String getPath() { return path; }
}
