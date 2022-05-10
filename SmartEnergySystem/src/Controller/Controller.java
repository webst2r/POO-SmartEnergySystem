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

    /** Function that starts the program and puts the system running */
    public void run() throws LinhaIncorretaException, IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        Parser parser = new Parser();
        LocalDateTime start = LocalDateTime.now();
        List<LocalDateTime> dates = new ArrayList<>();
        dates.add(start);
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
                    LocalDateTime newDate = scanDate(start, scanner);
                    dates.add(newDate);
                    handleSimulation(start,newDate,scanner);
                    start = newDate;

                    // as soon as the simulation is done, we process all the requests before we start a new simulation
                    List<Request> requestList = this.model.getRequests();
                    view.showln(requestList.size() + " requests being processed will start having effect in the next simulation round.");
                    for(Request r : requestList){
                        String requestType = r.getType();
                        if(requestType.equals("CS")){
                            this.model.changeSupplier(r.getNif(),this.model.getSupplier(r.getNewSupplier()));
                        } else if(requestType.equals("TON")){
                            this.model.TurnON(r.getNif(),r.getDevices());
                        } else if(requestType.equals("TOFF")){
                            this.model.TurnOFF(r.getNif(),r.getDevices(),start);
                        } else if(requestType.equals("CSV")){
                            this.model.changeSupplierValues(r.getOldSupplier(),r.getTax(), r.getBaseValue());
                        }
                    }

                    this.model.clearRequests();
                    view.pressKeyToContinue(scanner);
                    break;
                case 5:
                    // See houses on the system
                    List<List<SmartHouse>> pages = getPages(this.model.getHouses(), 5);

                    if(this.model.getHouses().size() > 0){
                        housePagination(pages,scanner);
                    } else {
                        view.showln("There are no houses yet.");
                        view.pressKeyToContinue(scanner);
                    }
                    break;
                case 6:
                    // Supplier info
                    List<Supplier> listSuppliers = this.model.getSuppliers();
                    if(listSuppliers.size() > 0){
                        view.showSupplierInfoMenu(listSuppliers);
                        String chosenSupplier = scanSupplier(scanner);
                        handleSupplierOperations(chosenSupplier, scanner);
                    } else {
                        view.showln("There are no suppliers yet.");
                        view.pressKeyToContinue(scanner);
                    }
                    break;
                case 7:
                    // Stats
                    view.showStatsMenu();
                    handleStatsOperations(scanner,dates);

                    break;
                case 8:
                    // Carregar estado
                    view.show("Load from file: ");
                    String filename = scanner.nextLine();
                    model = parser.readBin("../data/" + filename);
                    view.showln(model.getHouses().size() + " houses read from " + filename);
                    view.showln(model.getDevices().size() + " devices read from " + filename);
                    view.pressKeyToContinue(scanner);
                    break;
                case 9:
                    // Guardar estado
                    view.show("Save to file: ");
                    String filepath = scanner.nextLine();
                    parser.saveBin("SmartEnergySystem/data/"+filepath,model);
                    view.showln("Successfuly saved on: " + filepath);
                    view.pressKeyToContinue(scanner);
                    break;
                case 10:
                    // Logs
                    parser.parse(this.model);
                    view.showln("Successfully processed logs.");
                    view.pressKeyToContinue(scanner);
                    break;
                case 11:
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

    /**
     * Function that handles the simulation (advance to a new date, generate invoices)
     * @param start
     * @param end
     * @param scanner
     */

    public void handleSimulation(LocalDateTime start, LocalDateTime end,Scanner scanner){
        int days = (int) ChronoUnit.DAYS.between(start,end);
        double maxCost = 0.0;
        SmartHouse maxCostHouse = null;
        List<Invoice> invoices = new ArrayList<>();
        for(SmartHouse house : this.model.getHouses()){
            Supplier supplier = this.model.getSupplier(house.getSupplier());
            int NIF = house.getOwnerNIF();
            String owner = house.getOwnerName();

            double totalConsumption = house.getTotalDailyConsumption() * days;
            double totalCost = supplier.determineCostPerDay(house) * days;
            Invoice invoice = new Invoice(start,end,NIF,owner,supplier.getSupplierID(),totalConsumption,totalCost);

            this.model.addInvoiceToHouse(NIF,invoice);
            this.model.addInvoiceToSupplier(supplier.getSupplierID(),invoice);
            invoices.add(invoice);

            if(totalCost > maxCost){
                maxCost = totalCost;
                maxCostHouse = house;
            }
        }


        List<List<Invoice>> pages = getPages(invoices, 5);
        invoicePagination(pages,scanner);
        view.showln("\u001B[34m" + maxCostHouse.getOwnerName() + "\u001B[0m" + "'s house had the most costs" + "(" + "\u001B[32m" + maxCost + "\u001B[0m" + "€)" + " from " + start + " to " + end);
    }

    /**
     * Functions that handles the pagination of the invoices shown at the end of a simulation round
     * @param pages
     * @param scanner
     */

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

    /**
     * Functions that handles the pagination of the houses
     * @param pages
     * @param scanner
     */

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


    public void handleStatsOperations(Scanner scanner, List<LocalDateTime> dates){
        int option = scanInteger(scanner);
        LocalDateTime previousEnd = null, start = null, end = null;

        while (option != 4){
            switch (option){
                case 1:
                    // Supplier com maior volume de faturacao
                    Supplier best = this.model.getSupplierMostTurnOver();
                    if(best != null){
                        view.showln("🔋" + "Supplier with most turnover volume is " + "\u001B[32m" + best.getSupplierID() + "\u001B[0m" + " with " + best.getInvoices().size() + " invoices issued.");
                    } else {
                        view.showln("This operation cannot be performed as there have been no simulations yet.");
                    }
                    view.pressKeyToContinue(scanner);
                    view.showStatsMenu();
                    break;
                case 2:
                    // listar as facturas emitidas por um comercializador
                    view.showSupplierInfoMenu(this.model.getSuppliers());
                    String supplier = scanSupplier(scanner);
                    List<Invoice> invoiceList = this.model.getInvoicesIssuedBySupplier(supplier);
                    List<List<Invoice>> pages = getPages(invoiceList, 5);
                    invoicePagination(pages,scanner);
                    view.pressKeyToContinue(scanner);
                    view.showStatsMenu();
                    break;
                case 3:
                    view.showDateOptions(dates);
                    // Option1:   10-05 -> 30-05
                    // Option2:   30-05 -> 30-08
                    // Option3:   30-08 -> 20-09
                    // Option4:   EXIT

                    // N_DATES = 4

                    int chosenPeriod = scanInteger(scanner);
                    if(chosenPeriod != dates.size()){
                        if(previousEnd == null){
                            start = dates.get(chosenPeriod -1);
                            end = dates.get(chosenPeriod);
                            previousEnd = end;
                        } else {
                            start = previousEnd;
                            end = dates.get(chosenPeriod);
                            previousEnd = end;
                        }

                        List<String> topConsumers = this.model.getConsumersPeriod(start,end);
                        view.show("Top (N):");
                        int n = scanInteger(scanner);
                        while(n > topConsumers.size()){
                            view.showln("Please try a lower number.");
                            n = scanInteger(scanner);
                        }

                        for(int i = 0; i < n; i++){
                            String consumer = topConsumers.get(i);
                            view.showln((i+1) + ": " + consumer);
                        }
                        view.pressKeyToContinue(scanner);
                        view.showStatsMenu();
                    }
                    view.showStatsMenu();
                    break;
            }
            option = scanInteger(scanner);
        }
    }

    /**
     * Function that handles Energy Supplier operations
     * @param option
     * @param scanner
     */

    public void handleSupplierOperations(String option, Scanner scanner){
        Supplier supplier = this.model.getSupplier(option);
        view.showSupplierInfoOptions();

        int opt = scanInteger(scanner);
        while(opt != 3){
            switch (opt){
                case 1:
                    List<String> clients = this.model.getClientsNames(supplier.getSupplierID());
                    view.showSupplierClients(supplier.getSupplierID(),clients);
                    view.showSupplierInfoOptions();
                    break;
                case 2:
                    // change Base value of Energy Daily Cost
                    view.show("Insert new Base Value:");
                    double baseValue = scanDouble(scanner);
                    view.show("Insert new Tax(%):");
                    double tax = scanDouble(scanner);
                    tax /= 100;

                    Request changeSupplierValuesRequest = new Request(supplier.getSupplierID(),tax,baseValue);
                    this.model.addRequest(changeSupplierValuesRequest);

                    view.showSupplierInfoOptions();
                    break;
            }
            opt = scanInteger(scanner);
        }
    }

    /**
     * Functions that handles house operations
     * @param option
     * @param nif
     * @param scanner
     */
    public void handleHouseOperations(int option,int nif,Scanner scanner){

        while(option != 7){
            switch (option){
                case 1:
                    // Check devices
                    view.showHouseDevices(model.getHouse(nif));
                    view.pressKeyToContinue(scanner);
                    view.showHouseOperationsMenu(model.getHouse(nif).getOwnerName());
                    break;
                case 2:
                    List<Invoice> invoiceList = this.model.getInvoices(nif);
                    if(invoiceList.size() > 0) {
                        List<List<Invoice>> pages = getPages(invoiceList, 5);
                        invoicePagination(pages,scanner);
                    } else view.showln("This house does not have bills yet.");
                    view.pressKeyToContinue(scanner);
                    view.showHouseOperationsMenu(model.getHouse(nif).getOwnerName());
                    break;
                case 3:
                    // request a change of energy supplier
                    view.showSupplierInfoMenu(this.model.getSuppliers());

                    Supplier oldSupplier = this.model.getSupplier(this.model.getHouse(nif).getSupplier());
                    String newSup = scanSupplier(scanner);
                    Supplier newSupplier = this.model.getSupplier(newSup);

                    view.showChangeSupplierContract(oldSupplier.getSupplierID(),newSupplier.getSupplierID());
                    view.pressKeyToContinue(scanner);
                    view.showHouseOperationsMenu(model.getHouse(nif).getOwnerName());

                    Request changeSupplierRequest = new Request(nif,oldSupplier.getSupplierID(),newSupplier.getSupplierID());
                    this.model.addRequest(changeSupplierRequest);
                    break;
                case 4:
                    // Turn ON DEVICE
                    Map<String, List<SmartDevice>> turnedOffDevices = this.model.getDevicesTurnedOff(nif);
                    SmartDevice device = view.showTurnOnDevice(this.model.getHouse(nif),turnedOffDevices,scanner);

                    view.pressKeyToContinue(scanner);
                    view.showHouseOperationsMenu(model.getHouse(nif).getOwnerName());

                    List<SmartDevice> devicesToTurnOn = new ArrayList<>();
                    devicesToTurnOn.add(device);
                    Request turnOnRequest = new Request("TON",nif,devicesToTurnOn);
                    this.model.addRequest(turnOnRequest);
                    break;
                case 5:
                    // turn OFF DEVICE
                    Map<String, List<SmartDevice>> turnedOnDevices = this.model.getDevicesTurnedOn(nif);
                    SmartDevice chosenDevice = view.showTurnOffDevice(this.model.getHouse(nif),turnedOnDevices,scanner);

                    view.pressKeyToContinue(scanner);
                    view.showHouseOperationsMenu(model.getHouse(nif).getOwnerName());

                    List<SmartDevice> devicesToTurnOff = new ArrayList<>();
                    devicesToTurnOff.add(chosenDevice);
                    Request turnOffRequest = new Request("TOFF",nif,devicesToTurnOff);
                    this.model.addRequest(turnOffRequest);
                    break;
                case 6:
                    List<String> rooms = model.getRooms(nif);
                    view.displayRooms(rooms);
                    view.show("Select: ");
                    String room = scanner.nextLine();
                    while(!rooms.contains(room)){
                        view.show("Please insert a valid room.\nSelect:");
                        room = scanner.nextLine();
                    }

                    view.pressKeyToContinue(scanner);
                    view.showHouseOperationsMenu(model.getHouse(nif).getOwnerName());

                    List<SmartDevice> roomDevices = model.getRoomDevices(nif,room);
                    Request turnOffRoomRequest = new Request("TOFF",nif,roomDevices);
                    this.model.addRequest(turnOffRoomRequest);
                    break;
            }
            option = scanInteger(scanner);
        }
    }

    /**
     * Waits for an integer input from user, keeps asking until the input is correct
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
     * Waits for a Double from user, keeps asking  until the input is correct
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

    /**
     * Waits for a Char from user, keeps asking until the input is correct
     * @return the char
     */
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
    /**
     * Waits for a valid Supplier input from user, keeps asking until the input is correct
     * @return the double
     */

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
    /**
     * Waits for a valid Date input from user, keeps asking until the input is correct
     * @return the double
     */

    public LocalDateTime scanDate(LocalDateTime currentDate, Scanner scanner){
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

        while(!isAfter(newDate, currentDate)){
            view.show("Please advance at least a day... Try again\nDate:");
            date = scanner.nextLine();
            newDate = LocalDate.parse(date, formatter).atStartOfDay();
        }

        return newDate;
    }

    public boolean isAfter(LocalDateTime d1, LocalDateTime d2){
        if(d1.getYear() < d2.getYear()) return false;
        else if(d1.getYear() > d2.getYear()) return true;
        else if(d1.getDayOfYear() > d2.getDayOfYear()){
            return true;
        } else return false;
    }

    /**
     * Functions that generates the Pages used for pagination/displaying purposes
     */

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

    /**
     * Functions that creates a Device as per requested by user & adds it to the model
     */

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


    /**
     * Function that creates a House as per requested by user & adds it to the model
     */

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
            } else view.showln("Couldn't create house. Reason: A house associated with this NIF already exists...");
        } catch (InputMismatchException e){
            e.printStackTrace();
        }
    }

    /**
     * Functions that creates a Supplier as per requested by user & adds it to the model
     */

    public void createSupplier(){
        // Fornecedor:MEO Energia
        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
        view.showCreateMenu("Create an Energy Supplier");
        view.show("Name: ");
        String supplierName = scanner.nextLine();
        view.show("Daily energy cost: ");
        double energyCost = scanDouble(scanner);
        view.show("Tax amount: ");
        double tax = scanDouble(scanner);

        if(!model.supplierExists(supplierName)){
            Supplier supplier = new Supplier(supplierName,energyCost,tax);
            this.model.add(supplier);
        } else view.showln("Couldn't create supplier. Reason: A supplier with this name already exists.");
    }
}