import java.time.LocalDateTime;

public class TestSmartDevice {
    public static void main(String[] args) {
        LocalDateTime date = LocalDateTime.of(2022,4, 10, 23, 53, 40);
        SmartDevice device = new SmartDevice("1",true,date);

    }
}
