package dominio;

import java.util.LinkedList;

public class ArbolNFR {


	private LinkedList<NFR> hijos;

	public ArbolNFR() {
		this.hijos = new LinkedList<NFR>();
	}
	
	public void actualizarRiesgo() {
		for (NFR nfr : hijos) {
			nfr.actualizarRiesgo();
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
	
	
}
