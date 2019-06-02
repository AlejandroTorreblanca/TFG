package dominio;

import java.util.ArrayList;
import java.util.LinkedList;

public class NFR implements Comparable{

	private String codigo;
	private String descrip;
	private String tipo;
	private String categoria;
	private int importancia;
	private String validacion;
	private String argumentos;
	private int puntosHistoria;
	private int ciclos;
	private LinkedList<NFR> hijos;
	private int dependencias;
	private int nAudit;
	private double riesgo;
	
	public NFR() {
		this.codigo="raiz";
		this.hijos= new LinkedList<NFR>();
	}

	public NFR(String codigo, String tipo, String categoria, String descrip, int importancia, String validacion,
			String argumentos, int pHistoria, int dependencias, LinkedList<NFR> hijos, int nAudit, int ciclos) {
		this.codigo = codigo;
		this.descrip = descrip;
		this.tipo = tipo;
		this.categoria = categoria;
		this.importancia = importancia;
		this.validacion = validacion;
		this.argumentos = argumentos;
		this.puntosHistoria = pHistoria;
		this.hijos = hijos;
		this.dependencias = dependencias;
		this.nAudit = nAudit;
		this.ciclos = ciclos;
	}

	public NFR(String codigo, String descrip, String tipo, String categoria, int importancia, String validacion,
			String argumentos, int pHisotria,int dep, int nAudit, int ciclos) {
		this.codigo = codigo;
		this.descrip = descrip;
		this.tipo = tipo;
		this.categoria = categoria;
		this.importancia = importancia;
		this.validacion = validacion;
		this.argumentos = argumentos;
		this.puntosHistoria = pHisotria;
		this.nAudit = nAudit;
		this.dependencias=dep;
		this.ciclos=ciclos;
		this.hijos=new LinkedList<NFR>();
	}
	
	public boolean esHoja() {
		if(this.hijos.isEmpty() || this.hijos==null)
			return true;
		return false;
	}
	
	public double calcularRiesgo() {
		double riesgo=0.5*this.importancia;
		riesgo+=0.2*(5*this.hijos.size());
		riesgo+=0.2*(2.5*this.dependencias);
		riesgo+=0.1*this.ciclos;
		return riesgo/(this.nAudit+1);

	}
	
	public double actualizarRiesgo() {
		if(this.esHoja() || this.nAudit==0) {
			this.riesgo=calcularRiesgo();
			return this.riesgo;
		}
		else {
			for(NFR req : this.hijos) {
				this.riesgo += (this.importancia/10)*req.actualizarRiesgo(); 
			}
			return this.riesgo;
		}
	}

	public int getCiclos() {
		return ciclos;
	}

	public void setCiclos(int ciclos) {
		this.ciclos = ciclos;
	}

	public int getPuntosHistoria() {
		return puntosHistoria;
	}

	public void setPuntosHistoria(int puntosHistoria) {
		this.puntosHistoria = puntosHistoria;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public int getnAudit() {
		return nAudit;
	}

	public void setnAudit(int nAudit) {
		this.nAudit = nAudit;
	}

	public String getDescrip() {
		return descrip;
	}

	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public int getImportancia() {
		return importancia;
	}

	public void setImportancia(int importancia) {
		this.importancia = importancia;
	}

	public String getValidacion() {
		return validacion;
	}

	public void setValidacion(String validacion) {
		this.validacion = validacion;
	}

	public String getArgumentos() {
		return argumentos;
	}

	public void setArgumentos(String argumentos) {
		this.argumentos = argumentos;
	}

	public LinkedList<NFR> getHijos() {
		return hijos;
	}
	
	public LinkedList<NFR> getAllHijos() {
		@SuppressWarnings("unchecked")
		LinkedList<NFR> lista= (LinkedList<NFR>) hijos.clone();
		for(NFR req : this.hijos) {
			lista.addAll(req.getAllHijos());
		}
		
		return lista;
	}

	public void setHijos(LinkedList<NFR> hijos) {
		this.hijos = hijos;
	}

	public boolean addHijo(String padre,NFR n) {
		if(padre.compareTo(this.codigo)==0) {
			this.hijos.add(n);
			return true;
		}
		else {
			for(NFR req : hijos) {
				return req.addHijo(padre, n);
			}
		}
		return false;
	}

	public int getDependencias() {
		return dependencias;
	}
	
	public double getRiesgo() {
		return this.riesgo;
	}

	public void sumarDep(int n) {
		this.dependencias += n;
	}

	public void setDependencia(int dep) {
		this.dependencias = dep;
	}

	@Override
	public int compareTo(Object o) {
		NFR req = (NFR)o;
		if(req.getRiesgo()==this.riesgo)
			return 0;
		else if(req.getRiesgo()>this.riesgo)
			return 1;
		else 
			return -1;
	}

}
