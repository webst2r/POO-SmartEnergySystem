import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TestSmartDevice {
    public static void main(String[] args) {
        LocalDateTime date = LocalDateTime.of(2022,4, 10, 23, 53, 40);
        Set<SmartDevice> devices = new HashSet<>();
        Set<String> rooms = new HashSet<>();
        Map<String,Set<SmartDevice>> roomsNDevices = new HashMap<>();


        // Criar os devices
        for(int i = 1; i <= 10; i++){
            SmartDevice device = new SmartDevice("i",true,date.plusDays(1));
            devices.add(device);
        }

        // Criar os quartos
        for(int i = 1; i <= 5; i++){
            String room = "Room" + i;
            rooms.add(room);
            roomsNDevices.put(room,devices);
        }


        SmartHouse smartHouse = new SmartHouse("webster","12345",devices,rooms,roomsNDevices);

        // Statistics
        System.out.println("There are " + roomsNDevices.size() + " rooms");

        for (String room : rooms)
            System.out.println(room + " has " + roomsNDevices.get(room).size() + " devices.");





    }
}
