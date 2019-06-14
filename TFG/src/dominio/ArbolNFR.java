package dominio;

import java.util.LinkedList;

import controlador.Controlador;

public class ArbolNFR {


	private LinkedList<NFR> hijos;

	public ArbolNFR() {
		this.hijos = new LinkedList<NFR>();
	}
	
	public void actualizarRiesgo() {
		int sprintActual=Controlador.getUnicaInstancia().getProyecto().getNumSprint();
		for (NFR nfr : hijos) {
			nfr.actualizarRiesgo(sprintActual);
		}
	}

	public void addHijoRaiz(NFR req) {
		hijos.add(req);
	}
	
	public boolean addNodo(String padre, NFR req) {
		for (NFR nfr : hijos) {
			if( nfr.addHijo(padre, req))
				return true;
		}
		return false;
	}
	
	public LinkedList<NFR> getAllHijos() {
		@SuppressWarnings("unchecked")
		LinkedList<NFR> lista= (LinkedList<NFR>) hijos.clone();
		for(NFR req : this.hijos) {
			lista.addAll(req.getAllHijos());
		}
		
		return lista;
	}
	
	public LinkedList<NFR> getHijos() {
		return hijos;
	}

	public void setHijos(LinkedList<NFR> hijos) {
		this.hijos = hijos;
	}
	
	public void marcarAuditados(LinkedList<String> lista) {
		for(NFR req : this.hijos) {
			for(String nombre: lista)
				req.marcarAuditoria(nombre);
		}
	}
	
	public void reiniciarRequisitos() {
		for(NFR req : this.hijos) {
			req.reiniciar();
		}
	}
	
}
