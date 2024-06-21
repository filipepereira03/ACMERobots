package dados;

public class Agricola extends Robo {

	private double area;

	private String uso;

	public Agricola(int id, String modelo, double area) {
		super(id, modelo, 0);
		this.area = area;

	}

	@Override
	public double calculaLocacao(int dias) {
		double valorDiario = 10.00 * area;
		return valorDiario * dias;
	}

	public String toString() {
		return "Robo Agrícola " + super.toString();
	}
}
