package interfaz;

import javax.swing.table.DefaultTableModel;

import dominio.NFR;

@SuppressWarnings("serial")
public class ModeloTablaRequisitos extends DefaultTableModel {
	private String[] columnNames = { "Código","Riesgo","Puntos Historia", "Auditado"};

	@Override
	public boolean isCellEditable(int row, int column){
		if(column==3)
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
		boolean marca = (boolean) this.getValueAt(0, 3);
		for (int j = 0; j < this.getRowCount(); j++) {
			this.setValueAt(new Boolean(!marca), j, 3);
		}
	    
	}
	
	public void addFila(NFR req){
		Object [] nuevafila=new Object[4];
		nuevafila[0]=req.getCodigo();
		nuevafila[1]=req.getRiesgo();
		nuevafila[2]=req.getPuntosHistoria();
		nuevafila[3]= new Boolean(false);
		addRow(nuevafila);
	}
	
	public String getCodigoSeleccionado(int row){
		return (String)getValueAt(row, 0);	
	}
	
	public String getRiesgoSeleccionado(int row){
		return (String)getValueAt(row, 1);	
	}
	
	public String getPuntosHistoriaSeleccionado(int row){
		return (String)getValueAt(row, 2);
	}
	
}
