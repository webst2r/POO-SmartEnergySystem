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
        while (!exit) {
            this.view.showln(Resources.menuBanner);
            this.view.prompt("Menu","SmartEnergySystem");
            String commandline = scanner.nextLine();
            String[] input = commandline.split(" ");
            if (input.length > 0) {
                switch (input[0]) {
                    case "Exit":
                        if (input.length == 1) exit = false;
                        break;
                }
            }
        }
    }
}
