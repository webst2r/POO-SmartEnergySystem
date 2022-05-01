package Controller;

import Model.*;
import Utilities.LinhaIncorretaException;
import Utilities.Parser;
import Utilities.Resources;
import View.*;

import java.io.IOException;
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
                    // Create House
                    break;
                case 7:

                    for(SmartHouse h : model.getHouses()){
                        int sum = 0;
                        for(String divisao : h.getRoomList())
                            sum++;
                        view.showln("NUM DIV: " + sum + "");
                    }


                case 8:
                    // logs
                    Parser parser = new Parser();
                    parser.parse(this.model);
                    break;
                case 9:
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
        int option = scanner.nextInt();

        switch(option) {
            case 1:
                // Smart Bulb
                view.showln("Insert the tone (WARM - 2, NEUTRAL - 1, COLD - 0). ");
                int tone = scanner.nextInt();
                view.show("Diameter:");
                int diameter = scanner.nextInt();
                view.show("Consumption:");
                double consumption = scanner.nextDouble();

                SmartBulb bulb = new SmartBulb();
                bulb.setTone(tone);
                bulb.setDiameter(diameter);
                bulb.setConsumption(consumption);

                this.model.add(bulb);

                break;
            case 2:
                // Smart Camera
                break;
            case 3:
                // Smart Speaker
                break;
        }
    }





}
