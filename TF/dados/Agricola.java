package dados;

public class Agricola extends Robo {

	private double area;

	private String uso;

	public Agricola(int id, String modelo, double valorDiario, double area) {
		super(id, modelo, valorDiario);
		this.area = area;

	}

	@Override
	public double calculaLocacao(int dias) {
		return 10.00 * area * dias;
	}
}
