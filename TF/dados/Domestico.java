package dados;

public class Domestico extends Robo {

	private int nivel;

	public Domestico(int id, String modelo, double valorDiario) {
		super(id, modelo, valorDiario);
		this.nivel = nivel;
	}

	@Override
	public double calculaLocacao(int dias) {
		switch (nivel) {
		case 1:
			return 10.00 * dias;
		case 2:
			return 20.00 * dias;
		case 3:
			return 50.00 * dias;
		default:
			return 0.00;
		}
	}
}
