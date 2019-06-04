package dominio;

public class Proyecto {

	private double duracionSprint;
	private double duracionPHistoria;
	private double horasTrabajoDía;
	private int numSprint;

	public Proyecto(double duracionSprint, double duracionPHistoria, double horasTrabajoDía, int sprint) {
		this.duracionSprint = duracionSprint;
		this.duracionPHistoria = duracionPHistoria;
		this.horasTrabajoDía = horasTrabajoDía;
		this.numSprint=sprint;
		
	}

	
	public int getNumSprint() {
		return numSprint;
	}


	public void setNumSprint(int numSprint) {
		this.numSprint = numSprint;
	}


	public double getHorasTrabajoDía() {
		return horasTrabajoDía;
	}

	public void setHorasTrabajoDía(double horasTrabajoDía) {
		this.horasTrabajoDía = horasTrabajoDía;
	}
	
	public double getDuracionSprint() {
		return duracionSprint;
	}

	public void setDuracionSprint(double duracionSprint) {
		this.duracionSprint = duracionSprint;
	}

	public double getDuracionPHistoria() {
		return duracionPHistoria;
	}

	public void setDuracionPHistoria(double duracionPHistoria) {
		this.duracionPHistoria = duracionPHistoria;
	}
}
