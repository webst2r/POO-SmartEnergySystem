package View;

import Model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        menu.addOption("Houses");
        menu.addOption("Suppliers");
        menu.addOption("Stats");
        menu.addOption("Load");
        menu.addOption("Save");
        menu.addOption("Logs");
        menu.addOption("Exit ❌");
        menu.showMainMenu();
    }

    public void showCreateHouse(){
        clearScreen();
        Menu menu = new Menu();
        menu.setTitle("Create a SmartHouse");
        menu.addOption("Create a room");
        menu.addOption("Exit");
        menu.show(true);
    }

    public void showFreeDevices(String room, List<SmartDevice> freeDevices){
        clearScreen();
        Menu menu = new Menu();
        menu.setTitle("Choose a free device to add to " + room);
        for(SmartDevice d : freeDevices){
            menu.addOption(d.getClass().getSimpleName());
        }
        menu.addOption("Exit");
        menu.show(true);
    }



    public void showCreateDeviceMenu(){
        clearScreen();
        Menu menu = new Menu();
        menu.setTitle("Create a Smart Device");
        menu.addOption("Smart Bulb");
        menu.addOption("Smart Camera");
        menu.addOption("Smart Speaker");
        menu.addOption("Exit");
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

    public void showSupplierInfoMenu(List<Supplier> suppliers){
        System.out.println("-----------------------------");
        System.out.println("Available Suppliers:");
        for(Supplier sup : suppliers){
           System.out.println("🔋" + sup.getSupplierID());
        }
    }

    public void showSupplierInfoOptions(){
        clearScreen();
        Menu menu = new Menu();
        menu.setTitle("Select option");
        menu.addOption("Clients");
        menu.addOption("Change values");
        menu.addOption("Exit");
        menu.show(true);
    }

    public void showStatsMenu(){
        clearScreen();
        Menu menu = new Menu();
        menu.setTitle("[STATS] Select option:");
        menu.addOption("Supplier with the highest turnover volume");
        menu.addOption("Invoices issued by a supplier");
        menu.addOption("Largest energy consumers during a period");
        menu.addOption("Exit");
        menu.show(true);
    }

    public void showDateOptions(List<LocalDateTime> dates) {
        clearScreen();
        Menu menu = new Menu();
        menu.setTitle("[PERIOD] Select option:");
        for(int i = 0; i < dates.size() - 1; i++){
            StringBuilder option = new StringBuilder();
            option.append(dates.get(i)).append( " -> ").append(dates.get(i+1));
            menu.addOption(option.toString());
        }
        menu.addOption("Exit");
        menu.show(true);
    }


    public void showSupplierClients(String supplier, List<String> clients){
        for(String client : clients){
            System.out.println("\uD83D\uDC68 " + client);
        }
        System.out.println("Supplier " + "\u001B[31m" + supplier + "\u001B[0m" + " has " + "\u001B[32m" + clients.size() + "\u001B[0m" + " clients");
    }

    public void showChangeSupplierContract(String old, String newS){
        System.out.println("\uD83D\uDCDD Registered the request for changing from Energy Supplier " + "\u001B[31m" + old + "\u001B[0m" + " to "+ "\u001B[32m" + newS + "\u001B[0m");
        System.out.println("This contractual change will have effect when the next simulation period is opened.");
    }

    public void showInvoicesIssuedBySupplier(String supplier, List<Invoice> invoices){
        System.out.println("\uD83D\uDCDD Invoices issued by Energy Supplier " + "\u001B[32m" + supplier + "\u001B[0m");
        for(Invoice i : invoices){
            System.out.println(i.toString());
        }
    }


    public void showInvoicePagination(int page, List<Invoice> invoices, int total){

        for(Invoice invoice : invoices){
            System.out.println(invoice);
        }

        System.out.println("\n——————————————————Page " + page + " of " + total + "——————————————————");
        System.out.println("A      -> Advance");
        System.out.println("B      -> Go back");
        System.out.println("J      -> Jump to page");
        System.out.println("E      -> Exit");
        System.out.print("Action:");
    }


    public void showHousePagination(int page, List<SmartHouse> content, int total){
        int i = (5 * (page -1)) + 1;
        for(SmartHouse house : content){
            System.out.println("—————————————————————————— House " + i + " ——————————————————————————");
            System.out.println(house);
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
        menu.addOption("See devices");
        menu.addOption("See bills");
        menu.addOption("Request a change of energy supplier");
        menu.addOption("Turn ON device");
        menu.addOption("Turn OFF device");
        menu.addOption("Turn OFF room");
        menu.addOption("Exit");
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


    public void showHouseDevices(SmartHouse house){
        Map<String,List<SmartDevice>> devices = house.getRoomsNDevices();
        int i = 1;
        String state = null;
        for(String s : devices.keySet()){
            System.out.println("\nRoom: " + "\u001B[34m" + s + "\u001B[0m");
            List<SmartDevice> room = house.getRoomDevices(s);
            for(SmartDevice device : room){
                String smartDevice = device.getClass().getSimpleName();
                if(smartDevice.startsWith("SmartBulb")){
                    if(device.getOn()){
                        state = "ON";
                        System.out.println("\uD83D\uDCA1" + smartDevice + i + " state: " + "\u001B[32m" + state + "\u001B[0m");
                    } else {
                        state = "OFF";
                        System.out.println("\uD83D\uDCA1" + smartDevice + i + " state: " + "\u001B[31m" + state + "\u001B[0m");
                    }
                } else if(smartDevice.startsWith("SmartSpeaker")){
                    if(device.getOn()){
                        state = "ON";
                        System.out.println("\uD83D\uDCA1" + smartDevice + i + " state: " + "\u001B[32m" + state + "\u001B[0m");
                    } else {
                        state = "OFF";
                        System.out.println("\uD83D\uDCA1" + smartDevice + i + " state: " + "\u001B[31m" + state + "\u001B[0m");
                    }
                } else {
                    if(device.getOn()){
                        state = "ON";
                        System.out.println("\uD83D\uDCA1" + smartDevice + i + " state: " + "\u001B[32m" + state + "\u001B[0m");
                    } else {
                        state = "OFF";
                        System.out.println("\uD83D\uDCA1" + smartDevice + i + " state: " + "\u001B[31m" + state + "\u001B[0m");
                    }
                }
                i++;
            }
        }
    }


    public SmartDevice showTurnOnDevice(SmartHouse house,Map<String, List<SmartDevice>> turnedOffDevices, Scanner scanner) {
        Menu m = new Menu();
        m.setTitle("Choose one of the devices below:");
        List<SmartDevice> options = new ArrayList<>();
        for(String r : turnedOffDevices.keySet()){
            for(SmartDevice d : house.getRoomDevicesOFF(r)){
                String smartDevice = d.getClass().getSimpleName();
                options.add(d);
                m.addOption(smartDevice + "\u001B[31m " + "(" + r + ")" + "\u001B[0m");
            }
        }
        m.show(true);

        int opt;
        while(true) {
            try {
                opt = Integer.parseInt(scanner.nextLine());
                break;
            }catch (NumberFormatException e) {
                System.out.println("Oops, wrong input... Please try again");
                continue;
            }
        }
        SmartDevice chosenDevice = options.get(opt-1);
        System.out.println("\u001B[34m" + chosenDevice.getClass().getSimpleName() + "\u001B[0m" + " in " + "\u001B[31m" + house.getRoomOfDevice(chosenDevice) + "\u001B[0m" + " will be turned " + "\u001B[32m"+ "ON" + "\u001B[0m");

        return chosenDevice;
    }

    public SmartDevice showTurnOffDevice(SmartHouse house,Map<String, List<SmartDevice>> turnedOnDevices, Scanner scanner) {
        Menu m = new Menu();
        m.setTitle("Choose one of the devices below:");
        List<SmartDevice> options = new ArrayList<>();
        for(String r : turnedOnDevices.keySet()){
            for(SmartDevice d : house.getRoomDevicesON(r)){
                String smartDevice = d.getClass().getSimpleName();
                options.add(d);
                m.addOption(smartDevice + "\u001B[31m " + "(" + r + ")" + "\u001B[0m");
            }
        }
        m.show(true);

        int opt;
        while(true) {
            try {
                opt = Integer.parseInt(scanner.nextLine());
                break;
            }catch (NumberFormatException e) {
                System.out.println("Oops, wrong input... Please try again");
                continue;
            }
        }
        SmartDevice chosenDevice = options.get(opt-1);
        System.out.println("\u001B[34m" + chosenDevice.getClass().getSimpleName() + "\u001B[0m" + " in " + "\u001B[31m" + house.getRoomOfDevice(chosenDevice) + "\u001B[0m" + " will be turned " + "\u001B[31m" + "OFF" + "\u001B[0m");

        return chosenDevice;
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

