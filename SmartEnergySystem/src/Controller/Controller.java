package Controller;

import Model.*;
import Utilities.Resources;
import View.*;

import java.io.IOException;
import java.util.Scanner;

public class Controller {
    private Model model;
    private View view;

    public Controller(Model model, View view){
        this.model = model;
        this.view = view;
    }

    /**
     * Function that start the program and puts the system running
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        int option = -1;
        while (!exit) {
            this.view.showln(Resources.menuBanner);
            this.view.prompt("Menu","SmartEnergySystem");
            option = scanner.nextInt();

            switch (option) {
                case 0:
                    exit = true;
                    System.out.println("See you later!");
                    break;

            }

        }
    }
}
