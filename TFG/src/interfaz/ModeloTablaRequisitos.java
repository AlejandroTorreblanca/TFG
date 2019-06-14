package interfaz;

import javax.swing.table.DefaultTableModel;

import dominio.NFR;

@SuppressWarnings("serial")
public class ModeloTablaRequisitos extends DefaultTableModel {
	private String[] columnNames = { "Código","Descripción", "Validación","P.H.", "Auditado"};

	@Override
	public boolean isCellEditable(int row, int column){
		if(column==4)
			return true;
		return false;
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	public String getColumnName(int column) {
	    return columnNames[column];
	}
	
	public void marcarTodos() {
		boolean marca = (boolean) this.getValueAt(0,4);
		for (int j = 0; j < this.getRowCount(); j++) {
			this.setValueAt(new Boolean(!marca), j, 4);
		}
	    
	}
	
	public void addFila(NFR req){
		Object [] nuevafila=new Object[5];
		nuevafila[0]=req.getCodigo();
		nuevafila[1]=req.getDescrip();
		nuevafila[2]=req.getValidacion();
		nuevafila[3]=req.getPuntosHistoria();
		nuevafila[4]= new Boolean(false);
		addRow(nuevafila);
	}
	
	public String getCodigoSeleccionado(int row){
		return (String)getValueAt(row, 0);	
	}
	
	public String getPuntosDescripSeleccionado(int row){
		return (String)getValueAt(row, 1);
	}
	
	public String getValidacionSeleccionado(int row){
		return (String)getValueAt(row, 2);	
	}
	
	public String getPuntosHistoriaSeleccionado(int row){
		return (String)getValueAt(row, 3);
	}
	
	public boolean getIsAuditadoSeleccionado(int row){
		return (boolean) getValueAt(row, 4);
	}
	
}
