package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import controlador.Controlador;
import dominio.NFR;

public class PanelSprint  extends JPanel implements ActionListener{
	
	private JButton cancelarButton;
	private JButton newSprintButton;
	private JButton marcarAllButton;
	private VentanaPrincipal window;
	private Controlador controlador;
	private ModeloTablaRequisitos modelo;
	
	public PanelSprint(VentanaPrincipal w) {
		this.window=w;
		this.controlador=Controlador.getUnicaInstancia();
		LinkedList<NFR> lista = controlador.getNFRSprint();
		
		modelo = new ModeloTablaRequisitos();
		JTable tabla = new JTable(modelo);
		tabla.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(tabla);
		scrollPane.setPreferredSize(new Dimension(400, 300));
		tabla.setCellSelectionEnabled(false);
		tabla.setRowSelectionAllowed(true);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		TableColumn columna = tabla.getColumn("Código");
		TableColumn tc = tabla.getColumnModel().getColumn(3);
		tc.setCellEditor(tabla.getDefaultEditor(Boolean.class));
		tc.setCellRenderer(tabla.getDefaultRenderer(Boolean.class));
		columna.setMaxWidth(100);
		columna = tabla.getColumn("Riesgo");
		columna.setMaxWidth(75);
		columna = tabla.getColumn("Puntos Historia");
		columna.setMaxWidth(100);
		int x=0;
		for(NFR req : lista) {
			modelo.addFila(req);
		}
		
		marcarAllButton = new JButton("Marcar Todos");
		marcarAllButton.setMargin(new Insets(2, 100, 2, 99));
		marcarAllButton.addActionListener(this);
		
		newSprintButton = new JButton("Siguiente Sprint");
		newSprintButton.setMargin(new Insets(2, 100, 2, 99));
		newSprintButton.addActionListener(this);
		
		cancelarButton = new JButton("Cancelar");
		cancelarButton.setMargin(new Insets(2, 100, 2, 99));
		cancelarButton.addActionListener(this);
		
		
		
		JPanel panelCentral = new JPanel();
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
		panel1.setAlignmentX(LEFT_ALIGNMENT);
		panel2.setAlignmentX(LEFT_ALIGNMENT);
		panel3.setAlignmentX(CENTER_ALIGNMENT);
		
//		String colNames[] = { "Código", "Riesgo", "Puntos Historia", "Auditado" };
//		Object[][] data = {};
//		DefaultTableModel dtm;
//		setLocation(400, 100);
//		dtm = new DefaultTableModel(data, colNames);
//		JTable table = new JTable(dtm);
//		JScrollPane sp = new JScrollPane(table);
//		TableColumn tc = table.getColumnModel().getColumn(0);
//		tc.setCellEditor(table.getDefaultEditor(Boolean.class));
//		tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));
//		panel1.add(sp);
//		for (int x = 0; x < 5; x++) {
//			dtm.addRow(new Object[] { new Boolean(false), "Row " + (x + 1) + " Col 2", "Row " + (x + 1) + " Col 3" });
//		}
		
		panel1.add(scrollPane);
		panel2.add(newSprintButton);
		panel2.add(Box.createRigidArea(new Dimension(25, 25)));
		panel2.add(cancelarButton);
		panel3.add(marcarAllButton);
		
		panelCentral.add(Box.createRigidArea(new Dimension(20, 20)));
		panelCentral.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelCentral.add(panel1);
		panelCentral.add(panel3);
		panelCentral.add(panel2);
		
		JPanel pCentral = new JPanel();
		JPanel pNorte = new JPanel();
		JPanel pEste = new JPanel();
		JPanel pSur = new JPanel();
		JPanel pOeste = new JPanel();

		pCentral.setLayout(new BoxLayout(pCentral, BoxLayout.Y_AXIS));
		pCentral.setAlignmentX(Component.CENTER_ALIGNMENT);
		pCentral.add(panelCentral);

		JLabel rotuloSuperior = new JLabel("Requisitos del Proyecto", SwingConstants.CENTER);
		Font font = new Font("Calibri", Font.BOLD, 40);
		rotuloSuperior.setFont(font);
		pNorte.setAlignmentX(Component.CENTER_ALIGNMENT);
		pNorte.add(rotuloSuperior);
		pNorte.setBorder(BorderFactory.createLineBorder(Color.black));

		setLayout(new BorderLayout(10, 10));
		add(pNorte, BorderLayout.NORTH);

		JLabel imagen = new JLabel();
		imagen.setMaximumSize(new Dimension(80, 74));
		String nombre = "logo.png";
		String nombre_im = System.getProperty("user.dir") + "\\" + nombre;
		imagen.setIcon(new ImageIcon(nombre_im));
		pOeste.setLayout(new BoxLayout(pOeste, BoxLayout.Y_AXIS));
		pOeste.setAlignmentY(Component.LEFT_ALIGNMENT);
//		pOeste.add(imagen);
		pOeste.add(Box.createRigidArea(new Dimension(25, 200)));
		add(pOeste, BorderLayout.WEST);
		add(pCentral, BorderLayout.CENTER);

		pEste.add(Box.createRigidArea(new Dimension(100, 100)));
		add(pEste, BorderLayout.EAST);

		pSur.add(Box.createRigidArea(new Dimension(50, 50)));
		add(pSur, BorderLayout.SOUTH);
	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == marcarAllButton) {
			modelo.marcarTodos();
		} else if (e.getSource() == cancelarButton) {
			window.setPanelInicial();
		} else if (e.getSource() == newSprintButton) {
			
		}
		
		
	}
}
