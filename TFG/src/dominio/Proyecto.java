package dominio;

public class Proyecto {

	private double duracionSprint;
	private double duracionPHistoria;
	private double horasTrabajoDía;

	public Proyecto(double duracionSprint, double duracionPHistoria, double horasTrabajoDía) {
		this.duracionSprint = duracionSprint;
		this.duracionPHistoria = duracionPHistoria;
		this.horasTrabajoDía = horasTrabajoDía;
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
