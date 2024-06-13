package dados;

public class Domestico extends Robo {

	private int nivel;

	public Domestico(int id, String modelo, double valorDiario) {
		super(id, modelo, valorDiario);
    }

	@Override
	public double calculaLocacao(int dias) {
        return switch (nivel) {
            case 1 -> 10.00 * dias;
            case 2 -> 20.00 * dias;
            case 3 -> 50.00 * dias;
            default -> 0.00;
        };
	}
}
