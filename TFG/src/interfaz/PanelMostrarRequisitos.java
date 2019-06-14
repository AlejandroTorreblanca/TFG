package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import controlador.Controlador;
import dominio.NFR;

@SuppressWarnings("serial")
public class PanelMostrarRequisitos extends JPanel implements ActionListener, KeyListener {

	private VentanaPrincipal window;
	private Controlador controlador;
	private JTable tabla;
	private ModeloTablaMostrarRequisitos modelo;
	private JScrollPane scrollPane;
	private JButton confirmarButton;
	private JButton cancelarButton;

	public PanelMostrarRequisitos(VentanaPrincipal w) {

		this.window = w;
		controlador = Controlador.getUnicaInstancia();

		cancelarButton = new JButton("Volver");
		cancelarButton.setMargin(new Insets(2, 28, 2, 28));
		cancelarButton.addActionListener(this);

		JPanel panelCentral = new JPanel();
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		
		modelo = new ModeloTablaMostrarRequisitos();
		tabla = new JTable(modelo);
		tabla.setFillsViewportHeight(true);
		tabla.setRowHeight(tabla.getRowHeight() * 4);
		
		JScrollPane scrollPanel = new JScrollPane(tabla);
		scrollPanel.setPreferredSize(new Dimension(700, 400));
		tabla.setCellSelectionEnabled(false);
		tabla.setRowSelectionAllowed(true);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		TableColumn tc = tabla.getColumnModel().getColumn(4);
		tc = tabla.getColumnModel().getColumn(2);
		tc.setCellRenderer(new MultiLineCellRenderer());
		TableColumn columna = tabla.getColumn("Código");
		columna.setMinWidth(60);
		columna.setMaxWidth(60);
		columna = tabla.getColumn("Riesgo");
		columna.setMinWidth(50);
		columna.setMaxWidth(50);
		columna = tabla.getColumn("Import.");
		columna.setMinWidth(60);
		columna.setMaxWidth(60);
		columna = tabla.getColumn("Nº Auditado");
		columna.setMinWidth(60);
		columna.setMaxWidth(60);
		actualizarLista();
		
		
		JTree arbol = new JTree(controlador.getRequisitos());
		arbol.setCellRenderer(new DefaultTreeCellRenderer() {
			String nombre1 = "check.png";
			String nombre2 = "cross.png";
			String nombre3 = "red.png";
			String nombre4 = "verde.png";
			String nombre_im1 = System.getProperty("user.dir") + "\\" + nombre1;
			String nombre_im2 = System.getProperty("user.dir") + "\\" + nombre2;
			String nombre_im3 = System.getProperty("user.dir") + "\\" + nombre3;
			String nombre_im4 = System.getProperty("user.dir") + "\\" + nombre4;
			private Icon checkIcon = new ImageIcon(nombre_im1);
			private Icon crossIcon = new ImageIcon(nombre_im2);
			private Icon redIcon = new ImageIcon(nombre_im3);
			private Icon verdeIcon = new ImageIcon(nombre_im4);

			@Override
			public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
					boolean isLeaf, int row, boolean focused) {
				Component c = super.getTreeCellRendererComponent(tree, value, selected, expanded, isLeaf, row, focused);
				if (value instanceof DefaultMutableTreeNode) {
					Object userObject = ((DefaultMutableTreeNode) value).getUserObject();
					if (userObject instanceof NFR ) {
						NFR req = ((NFR) userObject);
						if (isLeaf && req.getnAudit() > 0) {
							setIcon(checkIcon);
						}
						else if (isLeaf && req.getnAudit() == 0) {
							setIcon(crossIcon);
						}
						else if (!isLeaf && req.getnAudit() == 0) {
							setIcon(redIcon);
						}
						else if (!isLeaf && req.getnAudit() > 0) {
							setIcon(verdeIcon);
						}
							
						
					}
				}
				return c;
			}
		});

		arbol.setRootVisible(false);
		scrollPane= new JScrollPane(arbol);
		scrollPane.setPreferredSize(new Dimension(250, 400));
		panel1.add(Box.createRigidArea(new Dimension(50, 15)));
		panel1.add(scrollPanel);
		panel1.add(Box.createRigidArea(new Dimension(50, 15)));
		panel1.add(scrollPane);

		panel2.add(Box.createRigidArea(new Dimension(50, 15)));
		panel2.add(cancelarButton);

		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
		panel2.setAlignmentX(LEFT_ALIGNMENT);
		
		
		panelCentral.add(Box.createRigidArea(new Dimension(20, 20)));
		panelCentral.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelCentral.add(panel1);
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
		Color c= new Color(98, 2, 174);
		rotuloSuperior.setForeground(c);
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


	public boolean isNumeric(String cadena) {
		try {
			Double.parseDouble(cadena);
			return true;
		} catch (NumberFormatException excepcion) {
			return false;
		}
	}
	
	public void actualizarLista() {
		vaciarTabla();
		LinkedList<NFR> lista = controlador.getNFRs();
		for(NFR req : lista) {
			modelo.addFila(req);
		}
	}
	
	public void vaciarTabla() {
		while (modelo.getRowCount() > 0)
			modelo.removeRow(0);
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == confirmarButton) {
		} else if (e.getSource() == cancelarButton) {
			window.setPanelInicial();
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}
}
