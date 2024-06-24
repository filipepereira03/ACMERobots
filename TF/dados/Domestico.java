package dados;

public class Domestico extends Robo {

	private int nivel;

	public Domestico(int id, String modelo, int nivel) {
		super(id, modelo, 0);
        this.nivel = nivel;
    }

	@Override
	public double calculaLocacao(int dias) {
        double valorDiario;
        switch (nivel) {
            case 1:
                valorDiario = 10.00;
                break;
            case 2:
                valorDiario = 20.00;
                break;
            case 3:
                valorDiario = 50.00;
                break;
            default:
                valorDiario = 0.00;
                break;
        }
        return valorDiario * dias;
    }

    public String toString() {
        return super.toString() + " - Doméstico - Nível: " + nivel;
    }
}
