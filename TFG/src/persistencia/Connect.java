package persistencia;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import controlador.Controlador;
import dominio.ArbolNFR;
import dominio.NFR;
import dominio.Proyecto;
import interfaz.PanelMensaje;

public class Connect {
	
	private static Connect unicaInstancia;
	private Workbook libro;
	private Sheet hoja1;
	private Sheet hoja2;
	private FileInputStream archivoInput;
	private boolean proyectoIniciado;

	private Connect() {
		String rutaArchivo = System.getProperty("user.dir") + "\\" + "lista_req.xlsx";
		String nombreArchivo = "Inventario.xlsx";
 
		try {
            archivoInput = new FileInputStream(new File(rutaArchivo));
            libro = new XSSFWorkbook(archivoInput);
            hoja1 = libro.getSheetAt(0);
            hoja2 = libro.getSheetAt(1);
//            DataFormatter formatter = new DataFormatter();
//            for (Row row : hoja2) {
//            	if(formatter.formatCellValue(row.getCell(0))=="")
//            		break;
//                for (Cell cell : row) {
//                    String contenidoCelda = formatter.formatCellValue(cell);
//                    System.out.print( contenidoCelda +" | ");
//                }
//                System.out.println();
//                
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}

	public static Connect getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new Connect();
		return unicaInstancia;
	}
	
	public boolean isIniciado() {
		DataFormatter dataFormatter = new DataFormatter();
		for (Row row : hoja1) {
			String str=dataFormatter.formatCellValue(row.getCell(10));
			if(str.compareTo("0")!=0 && str.compareTo("nº Auditado")!=0 && !str.isEmpty()) {
				return true;
			}
		}
		return false;
	}
	
	public Proyecto getProyecto() {
		DataFormatter formatter = new DataFormatter();
		double duracionSprint= Double.parseDouble(formatter.formatCellValue(hoja2.getRow(0).getCell(1)));
		double duracionPH= Double.parseDouble(formatter.formatCellValue(hoja2.getRow(1).getCell(1)));
		double horas= Double.parseDouble(formatter.formatCellValue(hoja2.getRow(2).getCell(1)));
		return new Proyecto(duracionSprint, duracionPH, horas);
	}
	
	public ArbolNFR leerArbol() {
		ArbolNFR arbol=new ArbolNFR();
		TreeSet<NFR> lista = new TreeSet<>();
		HashMap<NFR,String> hijos= new HashMap<NFR,String>();
		DataFormatter dataFormatter = new DataFormatter();
		String[] array= new String[12];
		int n=0;
		for (Row row : hoja1) {
			if (n != 0) {
				if (dataFormatter.formatCellValue(row.getCell(0)) == "")
					break;
				for (int i = 0; i < 12; i++) {
					array[i] = dataFormatter.formatCellValue(row.getCell(i));
				}
				NFR req = new NFR(array[0], array[1], array[2], array[3], Integer.parseInt(array[4]), array[5],
						array[6], Integer.parseInt(array[7]), countDepend(array[8]), Integer.parseInt(array[10]),
						Integer.parseInt(array[11]));
				if (array[9].compareTo(";")!=0) {
					String[] str = array[9].split(";");
					for (String padre : str) {
						hijos.put(req, padre);
//						System.out.println(padre +" padre de "+ array[0]);
					}
				} else {
					arbol.addHijoRaiz(req);
//					System.out.println("raiz padre de "+ array[0]);
				}
			}
			else
				n++;
		}

		while (!hijos.isEmpty()) {
			@SuppressWarnings("unchecked")
			HashMap<NFR, String> hijosaux =(HashMap<NFR, String>) hijos.clone();
			for (Map.Entry<NFR, String> entry : hijosaux.entrySet()) {
				if (arbol.addNodo(entry.getValue(), entry.getKey())) {
					hijos.remove(entry.getKey());
				}
			}
		}
		for(NFR req :lista)
			arbol.addHijoRaiz(req);
		return arbol;
		
	}

	
	
	public int countDepend(String str) {
		int posicion, contador = 0;
		char caracter=';';
        posicion = str.indexOf(caracter);
        while (posicion != -1) { 
            contador++;           
            posicion = str.indexOf(caracter, posicion + 1);
        }
        return contador;
	}
	/*
	public void f() {
		try {
			int columnas = 100;
			Row [] c = new Row[columnas];
			Cell [] f = new Cell[columnas];
			c[0] = hoja.createRow(0);
			f[2] = c[0].createCell(2);
			SimpleDateFormat d = new SimpleDateFormat("dd/MM/yyyy");
			f[2].setCellValue("Proyectos con movimientos entre las fechas:  " + d.format(w.getFechaChooser1().getDate()) + " - " + d.format(w.getFechaChooser2().getDate()) + ".");
    		
			
			c[2] = hoja.createRow(2);
			for (int i = 0; i < columnas; i++) {
				f[i] = c[2].createCell(i);
				hoja.autoSizeColumn(i);
			}
			
			f[0].setCellValue("n");
			f[1].setCellValue("Código");
			f[2].setCellValue("Nombre");
			f[3].setCellValue("Tipo");
			f[4].setCellValue("Estatus");
			f[5].setCellValue("Cliente");
			f[6].setCellValue("Presup. €");
			f[7].setCellValue("Costes €");
			f[8].setCellValue("Margen €");
			f[9].setCellValue("Margen %");
			
			for (int i = 3; i < w.getModelo().getRowCount()+3; i++) {
				c[i] = hoja.createRow(i);

				f[0] = c[i].createCell(0);
				f[0].setCellValue(w.getModelo().getNumeroSeleccionado(i - 3));

				f[1] = c[i].createCell(1);
				f[1].setCellValue(w.getModelo().getCodSeleccionado(i - 3));
				
				f[2] = c[i].createCell(2);
				f[2].setCellValue(w.getModelo().getNombreSeleccionado(i - 3));
				
				f[3] = c[i].createCell(3);
				f[3].setCellValue(w.getModelo().getTipoSeleccionado(i - 3));
				
				f[4] = c[i].createCell(4);
				f[4].setCellValue(w.getModelo().getEstatusSeleccionada(i - 3));
				
				f[5] = c[i].createCell(5);
				f[5].setCellValue(w.getModelo().getClienteSeleccionado(i - 3));
				
				f[6] = c[i].createCell(6);
				f[6].setCellValue(Float.parseFloat(w.getModelo().getPresupSeleccionado(i - 3).replace(".", "").replace(",", ".")));
				
				f[7] = c[i].createCell(7);
				f[7].setCellValue(Float.parseFloat(w.getModelo().getCostesSeleccionado(i - 3).replace(".", "").replace(",", ".")));
				
				f[8] = c[i].createCell(8);
				f[8].setCellValue(Float.parseFloat(w.getModelo().getMargenSeleccionado(i - 3).replace(".", "").replace(",", ".")));
				
				f[9] = c[i].createCell(9);
				f[9].setCellValue(Float.parseFloat(w.getModelo().getMargenPSeleccionado(i - 3).replace(".", "").replace(",", ".")));

			}
			for (int i = 0; i < columnas; i++) {
				hoja.autoSizeColumn(i);
			}
			
			try {
				libro.write(archivo);
				archivo.close();
				libro.close();
		        Desktop.getDesktop().open(new File(rutaArchivo));
			} catch (IOException e) {
				new PanelMensaje("Error en la creación del documento Excel.\n"+e, "Error en los datos", "error");
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			new PanelMensaje("Error al crear el Excel, asegurese de que el archivo no está siendo utilizado.", "Error", "error");
			e.printStackTrace();
		}
		
		
		
	}*/
}





















