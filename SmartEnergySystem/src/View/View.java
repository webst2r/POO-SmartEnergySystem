package View;

import Model.Menu;
import Model.SmartDevice;
import Model.SmartHouse;

import java.util.List;
import java.util.Scanner;

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
        menu.addOption("Cancel");
        menu.show(true);
    }

    public void showSmartBulbMenu(){
        clearScreen();
        Menu menu = new Menu();
        menu.setTitle("Please choose the tone");
        menu.addOption("WARM");
        menu.addOption("NEUTRAL");
        menu.addOption("COLD");
        menu.show(true);
    }

    public void showCreateMenu(String title){
        clearScreen();
        System.out.println("\u001B[36m" + title + "\u001B[0m");
        System.out.println("-----------------------------");
    }

    public void showChooseSupplierMenu(List<String> suppliers){
        clearScreen();
        System.out.println("-----------------------------");
        System.out.println("Available Energy Suppliers:");
        for(String s : suppliers){
            System.out.println("🔋" + s);
        }
    }

    public void showPagination(int page, List<SmartHouse> content, int total){
        int i = (5 * (page -1)) + 1;
        for(SmartHouse house : content){
            System.out.println("—————————————————————————— House " + i + " ——————————————————————————");
            System.out.println("\uD83C\uDFE0 House owner: " + house.getOwnerName());
            System.out.println("NIF: " + house.getOwnerNIF());
            i++;
        }

        System.out.println("\n——————————————————Page " + page + " of " + total + "——————————————————");
        System.out.println("A      -> Advance");
        System.out.println("B      -> Go back");
        System.out.println("J      -> Jump to page");
        System.out.println("S      -> Select house");
        System.out.println("F      -> Find house by NIF");
        System.out.println("E      -> Exit");
        System.out.print("Action:");
    }

    public void showHouseOperationsMenu(String owner){
        clearScreen();
        Menu menu = new Menu();
        menu.setTitle(owner + "'s SmartHouse Options");
        menu.addOption("Check devices");
        menu.addOption("Turn off room");
        menu.addOption("Cancel");
        menu.show(true);
    }

    public void displayRooms(List<String> rooms){
        clearScreen();
        System.out.println("-----------------------------");
        System.out.println("Rooms");
        for(String r : rooms){
            System.out.println("\uD83D\uDECF️" + r);
        }

    }


    public void showHouseDevices(List<SmartDevice> devices){
        int i = 1;
        String state = null;

        for(SmartDevice device : devices){
            if(device.getOn()){
                state = "ON";
            } else state = "OFF";
            System.out.println("\uD83D\uDCBB" + "Device" + i + " state: " + state);
            i++;
        }
    }
    /**
     * Displays a message asking the user to press enter in order to continue
     */
    public static void pressKeyToContinue(Scanner scanner){
        System.out.println("\n\n--- press enter to continue ---");
        scanner.nextLine();
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }


}
