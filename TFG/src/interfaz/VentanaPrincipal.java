package interfaz;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Arrays;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class VentanaPrincipal extends JFrame {

	private static VentanaPrincipal window;
	private PanelInicial panelInicial;
	private JPanel  panelCentral;
	private CardLayout card;
	private PanelMostrarRequisitos panelRequisitos;
	private PanelSprint panelSprint;
	
	
	private void inicializarPaneles() {
		
		panelInicial = new PanelInicial(this);
		panelCentral = new JPanel();
		card=new CardLayout();
		panelCentral.setLayout(card);
		panelRequisitos= new PanelMostrarRequisitos(this);
		panelSprint= new PanelSprint(this);
		
		panelCentral.add(panelInicial, "Inicial");
		panelCentral.add(panelRequisitos, "Requisitos");
		panelCentral.add(panelSprint, "Sprint");
		
		new BoxLayout(this, BoxLayout.Y_AXIS);
		add(Box.createRigidArea(new Dimension(100, 100)));
		add(panelCentral);
		
	}
	
	public void setPanelInicial(){
		card.show(panelCentral, "Inicial");
		panelInicial.actualizarPanel();
		validate();	
	}
	
	public void setPanelMostrarRequisitos(){
		card.show(panelCentral, "Requisitos");
		panelRequisitos.actualizarLista();
		validate();	
	}
	
	public void setPanelSprint(){
		card.show(panelCentral, "Sprint");
		panelSprint.actualizarLista();
		validate();	
	}
	

	public VentanaPrincipal() {	
		addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                
            }
        });
		inicializarPaneles();
		Toolkit miPantalla = Toolkit.getDefaultToolkit();
		Dimension tamPantalla = miPantalla.getScreenSize();
		int alturaPantalla = tamPantalla.height;
		int anchoPantalla = tamPantalla.width;
		setSize(anchoPantalla, alturaPantalla-40 );
		setLocation(0, 0);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("SCRUM+Audit");
		Image miIcono = miPantalla.getImage("logo.png");
		setIconImage(miIcono);
		setVisible(true);
		validate();
		setResizable(true);
		
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					window = new VentanaPrincipal();
					window.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

}
