public class SmartBulb extends SmartDevice{
    public static final int WARM = 2;
    public static final int NEUTRAL = 1;
    public static final int COLD = 0;
    private int tonalidade;
    private double dimensao;
    private double consumoDiario;


    /* Construtores */

    // Omissão
    public SmartBulb () {
        super();
        this.tonalidade = NEUTRAL;
    }


    // Parametrizado
    public SmartBulb (String id, Modo modo, int tone) {
        super(id, modo);
        this.tonalidade = NEUTRAL;
    }

    // Por cópia

    public SmartBulb (SmartBulb smartBulb) {
        super(smartBulb.getID(), smartBulb.getModo());
        this.setTonalidade(smartBulb.getTonalidade());
    }

    /* Getters & Setters */

    public int getTonalidade() { return this.tonalidade;}

    public void setTonalidade(int tonalidade) { this.tonalidade = tonalidade;}


    /* Clone */

    public SmartBulb clone () {
        return new SmartBulb(this);
    }

    /* Equals */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SmartBulb smartBulb = (SmartBulb) o;
        return (this.tonalidade == smartBulb.tonalidade && this.getID().equals(smartBulb.getID()));
    }
}
