package Controller;

import Model.*;
import Exceptions.LinhaIncorretaException;
import Model.Parser;
import View.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

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
        LocalDateTime start = LocalDateTime.now();
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
                    LocalDateTime newDate = scanDate(scanner);
                    handleSimulation(start,newDate,scanner);
                    start = newDate;
                    view.pressKeyToContinue(scanner);
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
                    // See houses on the system
                    List<List<SmartHouse>> pages = getPages(this.model.getHouses(), 5);

                    if(this.model.getHouses().size() > 0){
                        housePagination(pages,scanner);
                    } else {
                        view.showln("There are no houses in the system.");
                        view.pressKeyToContinue(scanner);
                    }
                    break;
                case 10:
                    // Supplier info
                    List<Supplier> listSuppliers = this.model.getSuppliers();
                    view.showSupplierInfoMenu(listSuppliers);
                    String chosenSupplier = scanSupplier(scanner);
                    handleSupplierOperations(chosenSupplier, scanner);
                    view.pressKeyToContinue(scanner);
                    break;
                case 11:
                    // Logs
                    parser.parse(this.model);
                    view.showln("Successfully processed logs.");
                    view.pressKeyToContinue(scanner);
                    break;
                case 12:
                    // Exit
                    exit = true;
                    scanner.close();
                    view.showln("See you later!");
                    break;
                default:
                    view.showln("Please select a valid option.");
            }
        }
    }

    public void handleSimulation(LocalDateTime start, LocalDateTime end,Scanner scanner){
        int days = (int) ChronoUnit.DAYS.between(start,end);
        List<Invoice> invoices = new ArrayList<>();
        for(SmartHouse house : this.model.getHouses()){
            Supplier supplier = this.model.getSupplier(house.getSupplier());
            int NIF = house.getOwnerNIF();
            String owner = house.getOwnerName();

            double totalConsumption = house.getTotalDailyConsumption() * days;
            double totalCost = supplier.determineCostPerDay(house) * days;
            Invoice invoice = new Invoice(start,end,NIF,owner,supplier.getSupplierID(),totalConsumption,totalCost);

            this.model.addInvoiceToHouse(NIF,invoice);
            invoices.add(invoice);
        }
        List<List<Invoice>> pages = getPages(invoices, 5);
        invoicePagination(pages,scanner);
    }

    public void invoicePagination(List<List<Invoice>> pages, Scanner scanner){
        int page = 1, total = pages.size(), totalInvoices = pages.size() * 5;
        view.showInvoicePagination(page,pages.get(0),total);
        char op = 'A';
        while(( op = scanChar(scanner)) != 'E'){
            switch (op) {
                case 'A':
                    if(page < total){
                        page++;
                        view.showInvoicePagination(page,pages.get(page-1),total);
                    } else view.show("You can't reach that page.\nAction:");
                    break;
                case 'B':
                    if(page > 1){
                        page--;
                        view.showInvoicePagination(page,pages.get(page-1),total);
                    } else view.show("You can't reach that page.\nAction:");
                    break;
                case 'J':
                    view.show("Jump to page:");
                    int scannedValue = scanInteger(scanner);

                    if(scannedValue >= 1 && scannedValue <= total){
                        page = scannedValue;
                        view.showInvoicePagination(page,pages.get(page-1),total);
                    } else view.show("You can't reach that page.\nAction:");
                    break;
            }
        }
    }


    public void housePagination(List<List<SmartHouse>> pages, Scanner scanner){
        int page = 1, total = pages.size(), totalHouses = pages.size() * 5;
        view.showHousePagination(page,pages.get(0),total);
        char op = 'A';
        while(( op = scanChar(scanner)) != 'E'){
            switch (op) {
                case 'A':
                    if(page < total){
                        page++;
                        view.showHousePagination(page,pages.get(page-1),total);
                    } else view.show("You can't reach that page.\nAction:");
                    break;
                case 'B':
                    if(page > 1){
                        page--;
                        view.showHousePagination(page,pages.get(page-1),total);
                    } else view.show("You can't reach that page.\nAction:");
                    break;
                case 'J':
                    view.show("Jump to page:");
                    int scannedValue = scanInteger(scanner);

                    if(scannedValue >= 1 && scannedValue <= total){
                        page = scannedValue;
                        view.showHousePagination(page,pages.get(page-1),total);
                    } else view.show("You can't reach that page.\nAction:");
                    break;
                case 'S':
                    view.show("House number:");
                    int houseNumber = scanInteger(scanner);

                    int a = (5 * (page -1)) + 1, b = (5 * (page -1)) + 5;
                    if(houseNumber >= a && houseNumber <= b){
                        // pagina 3 -> 15 casas
                        // casa 12 é a segunda da pagina 3
                        int numberInsidePage = houseNumber - a;
                        SmartHouse chosenHouse = pages.get(page -1).get(numberInsidePage);
                        view.showHouseOperationsMenu(chosenHouse.getOwnerName());
                        int opt = scanInteger(scanner);
                        handleHouseOperations(opt, chosenHouse.getOwnerNIF(),scanner);
                        view.showHousePagination(page,pages.get(page-1),total);
                    } else {
                        view.showln("Please select a house from this page.");
                        view.showHousePagination(page,pages.get(page-1),total);
                    }
                    break;
                case 'F':
                    view.show("NIF:");
                    int houseNIF = scanInteger(scanner);
                    if(model.houseExists(houseNIF)){
                        view.showHouseOperationsMenu(model.getHouse(houseNIF).getOwnerName());
                        int o = scanInteger(scanner);
                        handleHouseOperations(o, houseNIF,scanner);
                        view.showHousePagination(page,pages.get(page-1),total);
                    } else {
                        view.show("Invalid NIF. Couldn't select house.");
                        view.pressKeyToContinue(scanner);
                        view.showHousePagination(page,pages.get(page-1),total);
                    }
                    break;
            }
        }
    }

    public void handleSupplierOperations(String option, Scanner scanner){
        List<SmartHouse> clients = null;
        Supplier supplier = this.model.getSupplier(option);
        view.showSupplierInfoOptions();

        int opt = scanInteger(scanner);
        while(opt != 3){
            switch (opt){
                case 1:
                    clients = this.model.getClients(supplier.getSupplierID());
                    view.showSupplierClients(supplier,clients);
                    view.showSupplierInfoOptions();
                    break;
                case 2:
                    view.showln("Nothing to see here.");
                    view.showSupplierInfoOptions();
                    break;
            }
            opt = scanInteger(scanner);
        }
    }

    public void handleHouseOperations(int option,int nif,Scanner scanner){

        while(option != 4){
            switch (option){
                case 1:
                    // Check devices
                    view.showHouseDevices(model.getHouseDevices(nif));
                    view.pressKeyToContinue(scanner);
                    view.showHouseOperationsMenu(model.getHouse(nif).getOwnerName());
                    break;
                case 2:
                    List<String> rooms = model.getRooms(nif);
                    view.displayRooms(rooms);
                    view.show("Select: ");
                    String room = scanner.nextLine();
                    while(!rooms.contains(room)){
                        view.show("Please insert a valid room.\nSelect:");
                        room = scanner.nextLine();
                    }
                    if(!model.turnOffRoom(room, nif)) {
                        view.showln("One or more devices were turned ON today therefore couldn't be turned off.");
                    } else {
                        view.showln("Successfuly turned off all the devices of " + room);
                    }
                    view.pressKeyToContinue(scanner);
                    view.showHouseOperationsMenu(model.getHouse(nif).getOwnerName());
                    break;
                case 3:
                    List<Invoice> invoiceList = this.model.getInvoices(nif);
                    if(invoiceList.size() > 0) {
                        List<List<Invoice>> pages = getPages(invoiceList, 5);
                        invoicePagination(pages,scanner);
                    } else view.showln("This house does not have bills yet.");
                    view.pressKeyToContinue(scanner);
                    view.showHouseOperationsMenu(model.getHouse(nif).getOwnerName());
                    break;
            }
            option = scanInteger(scanner);
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

    public char scanChar(Scanner scanner){
        String input = null;
        char inputC = 'A';
        while(true) {
            input = scanner.nextLine();
            if(!input.equals("")) {
                inputC = input.charAt(0);
                break;
            } else {
                view.showln("Please insert a VALID letter");
                continue;
            }
        }
        return inputC;
    }

    public String scanSupplier(Scanner scanner){
        String supplier = null;
        view.show("Choose supplier: ");
        supplier = scanner.nextLine();
        while(!this.model.supplierExists(supplier)){
            view.showln("Please insert a valid Energy supplier");
            supplier = scanner.nextLine();
        }
        return supplier;
    }

    public LocalDateTime scanDate(Scanner scanner){
        String date = null;
        view.show("Date(dd/MM/YYYY):");
        date = scanner.nextLine();
        String dateRegEx="^\\d{2}/\\d{2}/\\d{4}$";
        while(!date.matches(dateRegEx)){
            view.show("Wrong format... Try again\nDate:");
            date = scanner.nextLine();
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime newDate = LocalDate.parse(date, formatter).atStartOfDay();

        return newDate;
    }




    public static <T> List<List<T>> getPages(Collection<T> c, Integer pageSize) {
        if (c == null)
            return Collections.emptyList();
        List<T> list = new ArrayList<T>(c);
        if (pageSize == null || pageSize <= 0 || pageSize > list.size())
            pageSize = list.size();
        int numPages = (int) Math.ceil((double)list.size() / (double)pageSize);
        List<List<T>> pages = new ArrayList<List<T>>(numPages);
        for (int pageNum = 0; pageNum < numPages;)
            pages.add(list.subList(pageNum * pageSize, Math.min(++pageNum * pageSize, list.size())));
        return pages;
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
            List<String> availableSuppliers = this.model.getSupplierNames();
            view.showChooseSupplierMenu(availableSuppliers);
            String supplier = scanSupplier(scanner);

            if(!this.model.houseExists(NIF)){
                SmartHouse house = new SmartHouse(owner,NIF,supplier);
                this.model.add(house);
                this.model.addClient(supplier,house);
            } else view.showln("A house associated with this NIF already exists...");
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

        if(!model.supplierExists(supplierName)){
            Supplier supplier = new Supplier(supplierName,energyCost);
            this.model.add(supplier);
        } else view.showln("Couldn't create supplier. Reason: A supplier with this name already exists.");

    }
}
