package Controller;

import Model.*;
import Utilities.LinhaIncorretaException;
import Utilities.Parser;
import View.*;

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
    public void run() throws LinhaIncorretaException {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        int option = -1;
        while (!exit) {
            this.view.showMainMenu();
            this.view.prompt("Menu","SmartEnergySystem");
            option = scanner.nextInt();

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
                    for(SmartHouse h : model.getHouses()){
                        view.showln(h);
                        /*
                        int sum = 0;
                        for(String divisao : h.getRoomList())
                            sum++;
                        view.showln("NUM DIV: " + sum + "");

                         */
                    }
                case 10:
                    // logs
                    Parser parser = new Parser();
                    parser.parse(this.model);
                    break;
                case 11:
                    // Exit
                    exit = true;
                    view.showln("See you later!");
                    break;
                default:
                    view.showln("Por favor insira uma opcao valida.");
            }

        }
    }

    public void createDevice(){
        view.showCreateDeviceMenu();
        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
        int option = Integer.parseInt(scanner.nextLine());

        switch(option) {
            case 1:
                // SmartBulb:Neutral,7,0.24
                // SmartBulb:<Tonalidade>,<Diametro>,<Consumo>
                try {
                    view.show("Insert the tone (WARM - 2, NEUTRAL - 1, COLD - 0): ");
                    int tone = Integer.parseInt(scanner.nextLine());
                    view.show("Diameter: ");
                    int diameter = Integer.parseInt(scanner.nextLine());
                    view.show("Consumption: ");
                    double consumption = Double.parseDouble(scanner.nextLine());

                    SmartBulb bulb = new SmartBulb();
                    bulb.setTone(tone);
                    bulb.setDiameter(diameter);
                    bulb.setConsumption(consumption);

                    this.model.add(bulb);
                } catch (NumberFormatException e){
                    view.showln("Formato de input errado.");
                }
                break;
            case 2:
                // SmartCamera: (1366x768), 63, 4.74
                // <Resolucao>,<Tamanho>,<Consumo>
                try {
                    view.showln("Insert the resolution(x,y).");
                    view.show("x:");
                    int resolutionX = Integer.parseInt(scanner.nextLine());
                    view.show("y:");
                    int resolutionY = Integer.parseInt(scanner.nextLine());
                    view.show("Insert the file size: ");
                    double fileSize = Double.parseDouble(scanner.nextLine());
                    view.show("Insert the consumption: ");
                    double consumptionC = Double.parseDouble(scanner.nextLine());

                    SmartCamera camera = new SmartCamera();
                    camera.setResolutionX(resolutionX);
                    camera.setResolutionY(resolutionY);
                    camera.setFileSize(fileSize);
                    camera.setConsumption(consumptionC);

                    this.model.add(camera);
                } catch (NumberFormatException e){
                    e.printStackTrace();
                }
                break;
            case 3:
                // SmartSpeaker:30,RTP Antena 1 98.3 FM,JBL,5.53
                // SmartSpeaker:<Volume>,<CanalRadio>,<Marca>,<Consumo>
                try{
                    view.show("Volume: ");
                    double volume = Double.parseDouble(scanner.nextLine());
                    view.show("Radio station: ");
                    String radio = scanner.nextLine();
                    view.show("\nBrand: ");
                    String brand = scanner.nextLine();
                    view.show("Consumption: ");
                    double consumptionS = Double.parseDouble(scanner.nextLine());


                    SmartSpeaker speaker = new SmartSpeaker();
                    speaker.setVolume(volume);
                    speaker.setChannel(radio);
                    speaker.setBrand(brand);
                    speaker.setConsumption(consumptionS);

                    this.model.add(speaker);
                } catch (NumberFormatException e){
                    e.printStackTrace();
                }

                break;
        }
    }


    public void createHouse(){
        // Casa:Vicente de Carvalho Castro,365597405,Iberdrola
        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);

        try {
            view.show("Owner name: ");
            String owner = scanner.nextLine();
            view.show("NIF: ");
            int NIF = Integer.parseInt(scanner.nextLine());
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
        view.show("Name: ");
        String supplierName = scanner.nextLine();
        view.show("Daily energy cost: ");
        double energyCost = scanner.nextDouble();

        Supplier supplier = new Supplier(supplierName,energyCost);
        this.model.add(supplier);
    }
}
