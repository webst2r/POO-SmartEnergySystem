package Controller;

import Model.*;
import Exceptions.LinhaIncorretaException;
import Model.Parser;
import View.*;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class Controller {
    private Model model;
    private View view;

    public Controller(Model model, View view){
        this.model = model;
        this.view = view;
    }

    /** Function that start the program and puts the system running */
    public void run() throws LinhaIncorretaException, IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        Parser parser = new Parser();

        boolean exit = false;
        int option = -1;
        while (!exit) {
            this.view.showMainMenu();
            this.view.prompt("Menu","SmartEnergySystem");
            option = scanInteger(scanner);

            switch (option) {
                case 1:
                    createDevice();
                    break;
                case 2:
                    createHouse();
                    break;
                case 3:
                    createSupplier();
                    break;
                case 4:
                    //advanceToDate();
                    break;
                case 7:
                    // Carregar estado
                    view.show("Load from file: ");
                    String filename = scanner.nextLine();
                    model = parser.readBin("../data/" + filename);
                    view.showln(model.getHouses().size() + " houses read from " + filename);
                    view.showln(model.getDevices().size() + " devices read from " + filename);
                    view.pressKeyToContinue(scanner);
                    break;
                case 8:
                    // Guardar estado
                    view.show("Save to file: ");
                    String filepath = scanner.nextLine();
                    parser.saveBin("SmartEnergySystem/data/"+filepath,model);
                    view.showln("Successfuly saved on: " + filepath);
                    view.pressKeyToContinue(scanner);
                    break;
                case 9:
                    // Check houses on the system
                    checkHousesOnTheSystem();
                    view.pressKeyToContinue(scanner);
                    break;
                case 10:
                    // Logs
                    parser.parse(this.model);
                    view.pressKeyToContinue(scanner);
                    break;
                case 11:
                    // Exit
                    exit = true;
                    scanner.close();
                    view.showln("See you later!");
                    break;
                default:
                    view.showln("Por favor insira uma opcao valida.");
            }
        }
    }

    /**
     * Waits for an integer input from user, keeps asking again until the input is correct
     * @return the integer read
     */
    public int scanInteger(Scanner scanner){
        int input = 0;
        while(true) {
            try {
                input = Integer.parseInt(scanner.nextLine());
                break;
            }catch (NumberFormatException e) {
                view.showln("Oops, wrong input... Please try again");
                continue;
            }
        }
        return input;
    }

    /**
     * Waits for a Double from user, keeps asking again until the input is correct
     * @return the double
     */

    public double scanDouble(Scanner scanner){
        double input = 0.0;
        while(true) {
            try {
                input = Double.parseDouble(scanner.nextLine());
                break;
            }catch (NumberFormatException e) {
                view.showln("Oops, wrong input... Please try again");
                continue;
            }
        }
        return input;
    }


    public void checkHousesOnTheSystem(){
        int sum = 0;
        if(model.getHouses().size() == 0) view.showln("There are no houses on the system yet.");
        else {
            for (SmartHouse h : model.getHouses()) {
                sum++;
                // view.showln(h);
            }
        }
        view.showln("Number of houses: " + sum);
    }

    public void createDevice(){
        view.showCreateDeviceMenu();
        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
        int option = scanInteger(scanner);

        switch(option) {
            case 1:
                // SmartBulb:Neutral,7,0.24
                // SmartBulb:<Tonalidade>,<Diametro>,<Consumo>
                view.showSmartBulbMenu();
                int tone = scanInteger(scanner);
                view.show("Diameter: ");
                int diameter = scanInteger(scanner);
                view.show("Consumption: ");
                double consumption = scanDouble(scanner);

                SmartBulb bulb = new SmartBulb();
                bulb.setTone(tone);
                bulb.setDiameter(diameter);
                bulb.setConsumption(consumption);

                this.model.add(bulb);
                break;
            case 2:
                // SmartCamera: (1366x768), 63, 4.74
                // <Resolucao>,<Tamanho>,<Consumo>

                view.showln("Insert the resolution(x,y).");
                view.show("x:");
                int resolutionX = scanInteger(scanner);
                view.show("y:");
                int resolutionY = scanInteger(scanner);
                view.show("Insert the file size: ");
                double fileSize = scanDouble(scanner);
                view.show("Insert the consumption: ");
                double consumptionC = scanDouble(scanner);

                SmartCamera camera = new SmartCamera();
                camera.setResolutionX(resolutionX);
                camera.setResolutionY(resolutionY);
                camera.setFileSize(fileSize);
                camera.setConsumption(consumptionC);

                this.model.add(camera);
                break;
            case 3:
                // SmartSpeaker:30,RTP Antena 1 98.3 FM,JBL,5.53
                // SmartSpeaker:<Volume>,<CanalRadio>,<Marca>,<Consumo>

                view.show("Volume: ");
                double volume = scanDouble(scanner);
                view.show("Radio station: ");
                String radio = scanner.nextLine();
                view.show("Brand: ");
                String brand = scanner.nextLine();
                view.show("Consumption: ");
                double consumptionS = scanDouble(scanner);


                SmartSpeaker speaker = new SmartSpeaker();
                speaker.setVolume(volume);
                speaker.setChannel(radio);
                speaker.setBrand(brand);
                speaker.setConsumption(consumptionS);

                this.model.add(speaker);

                break;
            default:
                break;
        }
    }


    public void createHouse(){
        // Casa:Vicente de Carvalho Castro,365597405,Iberdrola
        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);

        try {
            view.showCreateMenu("Create a SmartHouse");
            view.show("Owner name: ");
            String owner = scanner.nextLine();
            view.show("NIF: ");
            int NIF = scanInteger(scanner);
            view.show("Energy supplier: ");
            String supplier = scanner.nextLine();
            SmartHouse house = new SmartHouse(owner,NIF,supplier);
            this.model.add(house);
        } catch (InputMismatchException e){
            e.printStackTrace();
        }
    }

    public void createSupplier(){
        // Fornecedor:MEO Energia
        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
        view.showCreateMenu("Create an Energy Supplier");
        view.show("Name: ");
        String supplierName = scanner.nextLine();
        view.show("Daily energy cost: ");
        double energyCost = scanDouble(scanner);

        Supplier supplier = new Supplier(supplierName,energyCost);
        this.model.add(supplier);
    }
}
