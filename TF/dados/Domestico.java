package dados;

public class Domestico extends Robo {

	private int nivel;

	public Domestico(int id, String modelo, int nivel) {
		super(id, modelo, 0);
    }

	@Override
	public double calculaLocacao(int dias) {
        double valorDiario = switch (nivel) {
            case 1 -> 10.00 * dias;
            case 2 -> 20.00 * dias;
            case 3 -> 50.00 * dias;
            default -> 0.00;
        };
        return valorDiario * dias;
    }

    public String toString() {
        return "Robo Doméstico " + super.toString();
    }
}
