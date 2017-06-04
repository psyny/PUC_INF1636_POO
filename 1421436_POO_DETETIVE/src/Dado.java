import java.util.Random;

public class Dado{
	
	int valor = 0;
	
	public Dado()
	{
		Random rnd = new Random();
		this.valor = rnd.nextInt(5) + 1;
	}
	
	public int getValor()
	{
		return valor;
	}
}