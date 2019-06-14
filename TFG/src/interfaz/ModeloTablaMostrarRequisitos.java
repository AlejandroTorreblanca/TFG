package interfaz;

import javax.swing.table.DefaultTableModel;

import dominio.NFR;

@SuppressWarnings("serial")
public class ModeloTablaMostrarRequisitos extends DefaultTableModel {
	private String[] columnNames = { "Código","Riesgo","Descripción","Import.", "Nº Auditado"};

	@Override
	public boolean isCellEditable(int row, int column){
		return false;
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	public String getColumnName(int column) {
	    return columnNames[column];
	}
	
	public void addFila(NFR req){
		Object [] nuevafila=new Object[5];
		nuevafila[0]=req.getCodigo();
		nuevafila[1]=req.getRiesgo();
		nuevafila[2]=req.getDescrip();
		nuevafila[3]=req.getImportancia();
		nuevafila[4]=req.getnAudit();
		addRow(nuevafila);
	}
	
	public String getCodigoSeleccionado(int row){
		return (String)getValueAt(row, 0);	
	}
	
	public String getRiesgoSeleccionado(int row){
		return (String)getValueAt(row, 1);
	}
	
	public String getDescripSeleccionado(int row){
		return (String)getValueAt(row, 2);	
	}
	
	public String getImportSeleccionado(int row){
		return (String)getValueAt(row, 3);	
	}
	
	public String getAuditadoSeleccionado(int row){
		return (String)getValueAt(row, 4);
	}
	
	
}

