package View;

import Model.Menu;

public class View {

    public void showln(String msg){
        System.out.println(msg);
    }

    public void show(String msg){
        System.out.print(msg);
    }

    public void showln(String[] args){
        for(int i = 0; i < args.length; i++)
            System.out.println(args[i]);
    }

    public void showln(Object arg){
        System.out.println(arg);
    }

    public void prompt(String name, String module){
        System.out.print("["+ name +"@" + module + "]$ ");
    }


    public void showMainMenu(){
        clearScreen();
        Menu menu = new Menu();
        menu.setTitle("SmartEnergySystem");
        menu.addOption("Create Smart Device");
        menu.addOption("Create Smart House");
        menu.addOption("Create Energy Supplier");
        menu.addOption("Advance to date");
        menu.addOption("House with most costs");
        menu.addOption("Supplier with the most volume");
        menu.addOption("Load");
        menu.addOption("Save");
        menu.addOption("Houses on the system");
        menu.addOption("Logs");
        menu.addOption("Exit ❌");
        menu.showMainMenu();
    }

    public void showCreateDeviceMenu(){
        clearScreen();
        Menu menu = new Menu();
        menu.setTitle("Create a Smart Device");
        menu.addOption("Smart Bulb");
        menu.addOption("Smart Camera");
        menu.addOption("Smart Speaker");
        menu.show(true);
    }



    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }


}
