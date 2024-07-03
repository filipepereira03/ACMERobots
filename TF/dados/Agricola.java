package dados;

public class Agricola extends Robo {

    private double area;

    private String uso;

    public Agricola(int id, String modelo, double area, String uso) {
        super(id, modelo, 0);
        this.area = area;
        this.uso = uso;

    }

    @Override
    public double calculaLocacao(int dias) {
        double valorDiario = 10.00 * area;
        return valorDiario * dias;
    }

    public String toString() {
        return super.toString() + " - Agrícola - Área: " + area + " - Uso: " + uso;
    }
}
