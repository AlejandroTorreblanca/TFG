package controlador;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import javax.swing.tree.DefaultMutableTreeNode;

import dominio.ArbolNFR;
import dominio.NFR;
import dominio.Proyecto;
import persistencia.Connect;

public class Controlador {

	private static Controlador unicaInstancia;
	private final Connect con;

	private Controlador() {
		this.con = Connect.getUnicaInstancia();
		
	}

	public static Controlador getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new Controlador();
		return unicaInstancia;
	}
	
	public DefaultMutableTreeNode getRequisitos() {
		ArbolNFR arbol = con.leerArbol();
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
		for(NFR req : arbol.getHijos()) {
			root.add(crearArbol(req));
		}
		return root;
	}
	
	public DefaultMutableTreeNode crearArbol(NFR req) {
		LinkedList<NFR> lista = req.getHijos();
		if(lista.isEmpty()) {
			return new DefaultMutableTreeNode(req.getCodigo());
		}
		else
		{
			DefaultMutableTreeNode root = new DefaultMutableTreeNode(req.getCodigo());
			for(NFR nfr : lista) {
				root.add(crearArbol(nfr));
			}
			return root;
		}
	}
	public boolean isProyectoIniciado() {
		return con.isIniciado();
	}
	
	@SuppressWarnings("unchecked")
	public LinkedList<NFR> getListaOrdenadaNFR(){
		ArbolNFR arbol =con.leerArbol();
		arbol.actualizarRiesgo();
		LinkedList<NFR> lista=arbol.getAllHijos();
		Collections.sort(lista);
		return lista;
	}
	
	public LinkedList<NFR> getNFRSprint(){
		LinkedList<NFR> lista = new LinkedList<NFR>();
		int n=0, i=0;
		Proyecto proy = con.getProyecto();
		LinkedList<NFR> listaNFR = this.getListaOrdenadaNFR();
		while (n<proy.getDuracionPHistoria()*proy.getDuracionSprint() && i<listaNFR.size()) {
			lista.add(listaNFR.get(i));
			n+=listaNFR.get(i).getPuntosHistoria();
			i++;
		}
		
		return lista;
	}
	
	public double getDuracSprint() {
		return con.getProyecto().getDuracionSprint();
	}
	
	public double getDuracPH() {
		return con.getProyecto().getDuracionPHistoria();
	}
	
	
}
