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
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumn;

import controlador.Controlador;
import dominio.NFR;
import dominio.Proyecto;

@SuppressWarnings("serial")
public class PanelSprint  extends JPanel implements ActionListener{
	
	private JButton cancelarButton;
	private JButton newSprintButton;
	private JButton marcarAllButton;
	private VentanaPrincipal window;
	private Controlador controlador;
	private ModeloTablaRequisitos modelo;
	private JTextField textoSprint;
	private JTable tabla;
	
	public PanelSprint(VentanaPrincipal w) {
		this.window=w;
		this.controlador=Controlador.getUnicaInstancia();
		
		JLabel rotuloSprint = new JLabel("Sprint actual: ", SwingConstants.CENTER);
		
		textoSprint = new JTextField();
		fixedSize(textoSprint, 40, 24);
		textoSprint.setEditable(false);		
		
		modelo = new ModeloTablaRequisitos();
		tabla = new JTable(modelo);
		tabla.setFillsViewportHeight(true);
		tabla.setRowHeight(tabla.getRowHeight() * 4);
		
		JScrollPane scrollPane = new JScrollPane(tabla);
		scrollPane.setPreferredSize(new Dimension(1100, 300));
		tabla.setCellSelectionEnabled(false);
		tabla.setRowSelectionAllowed(true);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		TableColumn tc = tabla.getColumnModel().getColumn(4);
		tc.setCellEditor(tabla.getDefaultEditor(Boolean.class));
		tc.setCellRenderer(tabla.getDefaultRenderer(Boolean.class));
		tc = tabla.getColumnModel().getColumn(1);
		tc.setCellRenderer(new MultiLineCellRenderer());
		tc = tabla.getColumnModel().getColumn(2);
		tc.setCellRenderer(new MultiLineCellRenderer());
		TableColumn columna = tabla.getColumn("Código");
		
		columna.setMinWidth(60);
		columna.setMaxWidth(60);
		columna = tabla.getColumn("P.H.");
		columna.setMinWidth(50);
		columna.setMaxWidth(50);
		columna = tabla.getColumn("Auditado");
		columna.setMinWidth(60);
		columna.setMaxWidth(60);
		columna = tabla.getColumn("Validación");
		columna.setMinWidth(300);
		columna.setMaxWidth(300);
		actualizarLista();
		
		marcarAllButton = new JButton("Marcar Todos");
		marcarAllButton.setMargin(new Insets(2, 60, 2, 60));
		marcarAllButton.addActionListener(this);
		
		newSprintButton = new JButton("Finalizar Sprint");
		newSprintButton.setMargin(new Insets(2, 60, 2, 60));
		newSprintButton.addActionListener(this);
		
		cancelarButton = new JButton("Volver");
		cancelarButton.setMargin(new Insets(2, 60, 2, 60));
		cancelarButton.addActionListener(this);
		
		
		
		JPanel panelCentral = new JPanel();
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();
		
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
		panel1.setAlignmentX(LEFT_ALIGNMENT);
		panel2.setAlignmentX(LEFT_ALIGNMENT);
		panel3.setAlignmentX(LEFT_ALIGNMENT);
		panel4.setAlignmentX(LEFT_ALIGNMENT);
		
		panel1.add(scrollPane);
		panel2.add(newSprintButton);
		panel2.add(Box.createRigidArea(new Dimension(25, 25)));
		panel2.add(cancelarButton);
		panel3.add(marcarAllButton);
		panel4.add(rotuloSprint);
		panel4.add(Box.createRigidArea(new Dimension(2, 2)));
		panel4.add(textoSprint);
		
		panelCentral.add(Box.createRigidArea(new Dimension(20, 20)));
		panelCentral.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelCentral.add(panel4);
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

		JLabel rotuloSuperior = new JLabel("Requisitos del Sprint", SwingConstants.CENTER);
		Font font = new Font("Calibri", Font.BOLD, 40);
		Color c= new Color(98, 2, 174);
		rotuloSuperior.setForeground(c);
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
	
	public void actualizarLista() {
		vaciarTabla();
		LinkedList<NFR> lista = controlador.getNFRSprint();
		Proyecto proy=controlador.getProyecto();
		for(NFR req : lista) {
			modelo.addFila(req);
		}
		textoSprint.setText(Integer.toString(proy.getNumSprint()));
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == marcarAllButton) {
			modelo.marcarTodos();
		} else if (e.getSource() == cancelarButton) {
			window.setPanelInicial();
		} else if (e.getSource() == newSprintButton) {
			nuevoSprint();
		}
		
		
	}
	
	private void fixedSize(JComponent c, int x, int y) {
		c.setMinimumSize(new Dimension(x, y));
		c.setMaximumSize(new Dimension(x, y));
		c.setPreferredSize(new Dimension(x, y));
	}
	
	public void nuevoSprint() {
		LinkedList<String> lista=new LinkedList<String>();
		for (int i = 0; i < modelo.getRowCount(); i++) {
			if(modelo.getIsAuditadoSeleccionado(i))
				lista.add(modelo.getCodigoSeleccionado(i));
			System.out.println(modelo.getCodigoSeleccionado(i));
		}
		controlador.confirmarSprint(lista);
		actualizarLista();
		
	}
	
	
	public void vaciarTabla() {
		while (modelo.getRowCount() > 0)
			modelo.removeRow(0);
	}

}
