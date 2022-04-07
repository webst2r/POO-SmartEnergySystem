public class SmartSpeaker extends SmartDevice{
    private String canal;
    private String marca;
    private double volume;
    private double consumoDiario;


    public SmartSpeaker () {
        super();
        this.canal = "";
        this.marca = "";
        this.volume = 0.0;
        this.consumoDiario = 0.0;
    }

    public SmartSpeaker (String id, Modo modo, String canal, String marca, double volume) {
        super (id, modo);
        this.canal = canal;
        this.marca = marca;
        setVolume(volume);
    }

    public SmartSpeaker (SmartSpeaker ss) {
        super (ss.getID(), ss.getModo());
        this.canal = ss.getCanal();
        this.marca = ss.getMarca();
        this.volume = ss.getVolume();
    }

    public String getCanal() { return this.canal;}
    public String getMarca() { return this.marca;}
    public double getVolume() { return this.volume;}

    public void setVolume(double volume) { this.volume = volume;}


    public SmartSpeaker clone() {
        return new SmartSpeaker(this);
    }
}
