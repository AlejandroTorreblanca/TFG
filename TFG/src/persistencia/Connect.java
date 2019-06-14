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
	private String rutaArchivo;
	private Proyecto proyecto;
	private ArbolNFR arbol;
	private int iniciado;

	private Connect() {
		rutaArchivo = System.getProperty("user.dir") + "\\" + "lista_req.xlsx";

		try {
//			FileInputStream archivoInput = new FileInputStream(new File(rutaArchivo));
//			Workbook libro = new XSSFWorkbook(archivoInput);
//			Sheet hoja1 = libro.getSheetAt(0);
//			Sheet hoja2 = libro.getSheetAt(1);
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
		boolean ini = false;
		if (iniciado == 0)
			try {
				FileInputStream archivoInput = new FileInputStream(new File(rutaArchivo));
				Workbook libro = new XSSFWorkbook(archivoInput);
				Sheet hoja2 = libro.getSheetAt(1);
				DataFormatter dataFormatter = new DataFormatter();

				String str = dataFormatter.formatCellValue(hoja2.getRow(3).getCell(1));
				if (str.compareTo("0") != 0 && !str.isEmpty()) {
					archivoInput.close();
					libro.close();
					ini = true;
					this.iniciado = 1;
				} else
					this.iniciado = -1;

				archivoInput.close();
				libro.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else if (iniciado == 1)
			ini = true;
		return ini;
	}

	public Proyecto getProyecto() {
		if (this.proyecto == null)
			try {
				FileInputStream archivoInput = new FileInputStream(new File(rutaArchivo));
				Workbook libro = new XSSFWorkbook(archivoInput);
				Sheet hoja2 = libro.getSheetAt(1);
				DataFormatter formatter = new DataFormatter();
				double duracionSprint = Double.parseDouble(formatter.formatCellValue(hoja2.getRow(0).getCell(1)));
				double duracionPH = Double.parseDouble(formatter.formatCellValue(hoja2.getRow(1).getCell(1)));
				double horas = Double.parseDouble(formatter.formatCellValue(hoja2.getRow(2).getCell(1)));
				int sprint = Integer.parseInt(formatter.formatCellValue(hoja2.getRow(3).getCell(1)));
				archivoInput.close();
				libro.close();
				this.proyecto = new Proyecto(duracionSprint, duracionPH, horas, sprint);
				return this.proyecto;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else
			return this.proyecto;
		return null;
	}

	public ArbolNFR leerArbol() {
		if (this.arbol == null) {
			try {
				FileInputStream archivoInput = new FileInputStream(new File(rutaArchivo));
				Workbook libro = new XSSFWorkbook(archivoInput);
				Sheet hoja1 = libro.getSheetAt(0);
				ArbolNFR arbol = new ArbolNFR();
				TreeSet<NFR> lista = new TreeSet<>();
				HashMap<NFR, String> hijos = new HashMap<NFR, String>();
				DataFormatter dataFormatter = new DataFormatter();
				String[] array = new String[12];
				int n = 0;
				for (Row row : hoja1) {
					if (n != 0) {
						if (dataFormatter.formatCellValue(row.getCell(0)) == "")
							break;
						for (int i = 0; i < 12; i++) {
							array[i] = dataFormatter.formatCellValue(row.getCell(i));
						}
						NFR req = new NFR(array[0], array[1], array[2], array[3], Integer.parseInt(array[4]), array[5],
								array[6], Integer.parseInt(array[7]), countDepend(array[8]),
								Integer.parseInt(array[10]), Integer.parseInt(array[11]));
						if (array[9].compareTo(";") != 0) {
							String[] str = array[9].split(";");
							for (String padre : str) {
								hijos.put(req, padre);
//							System.out.println(padre +" padre de "+ array[0]);
							}
						} else {
							arbol.addHijoRaiz(req);
//						System.out.println("raiz padre de "+ array[0]);
						}
					} else
						n++;
				}

				while (!hijos.isEmpty()) {
					@SuppressWarnings("unchecked")
					HashMap<NFR, String> hijosaux = (HashMap<NFR, String>) hijos.clone();
					for (Map.Entry<NFR, String> entry : hijosaux.entrySet()) {
						if (arbol.addNodo(entry.getValue(), entry.getKey())) {
							hijos.remove(entry.getKey());
						}
					}
				}
				libro.close();
				archivoInput.close();
				for (NFR req : lista)
					arbol.addHijoRaiz(req);
				this.arbol = arbol;
				return arbol;

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else
			return this.arbol;
		return null;

	}

	public void reiniciarProyecto() {
		try {
			FileInputStream archivoInput = new FileInputStream(new File(rutaArchivo));
			Workbook libro = new XSSFWorkbook(archivoInput);
			Sheet hoja1 = libro.getSheetAt(0);
			Sheet hoja2 = libro.getSheetAt(1);
			FileOutputStream archivo = new FileOutputStream(new File(rutaArchivo));
			DataFormatter dataFormatter = new DataFormatter();
			int n = 0;
			for (Row row : hoja1) {
				if (n != 0) {
					if (dataFormatter.formatCellValue(row.getCell(0)) == "")
						break;
					Cell celda = row.getCell(10);
					celda.setCellValue(0);
					celda = row.getCell(11);
					celda.setCellValue(0);
				}
				n++;
			}

			Cell celda = hoja2.getRow(3).getCell(1);
			celda.setCellValue(0);

			libro.write(archivo);
			archivoInput.close();
			archivo.close();
			libro.close();
			this.iniciado = -1;
			this.proyecto.setNumSprint(0);
			arbol.reiniciarRequisitos();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setNumSprint(int n) {
		
		try {
			FileInputStream archivoInput = new FileInputStream(new File(rutaArchivo));
			Workbook libro = new XSSFWorkbook(archivoInput);
			Sheet hoja2 = libro.getSheetAt(1);
			FileOutputStream archivo = new FileOutputStream(new File(rutaArchivo));
			Cell celda = hoja2.getRow(3).getCell(1);
			celda.setCellValue(n);
			libro.write(archivo);
			archivo.close();
			archivoInput.close();
			libro.close();
			this.proyecto.setNumSprint(n);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void marcarAuditados(LinkedList<String> lista) {
		LinkedList<String> copia=(LinkedList<String>) lista.clone();
		DataFormatter dataFormatter = new DataFormatter();
		Proyecto proyecto = getProyecto();
		int cicloActual = proyecto.getNumSprint();
		try {
			FileInputStream archivoInput = new FileInputStream(new File(rutaArchivo));
			Workbook libro = new XSSFWorkbook(archivoInput);
			Sheet hoja1 = libro.getSheetAt(0);
			FileOutputStream archivo = new FileOutputStream(new File(rutaArchivo));
			for (Row row : hoja1) {
				String codigo = dataFormatter.formatCellValue(row.getCell(0));
				if (codigo == "")
					break;
				else {
					String straux = "";
					for (String str : lista) {
						if (str.compareTo(codigo) == 0) {
							Cell celda = row.getCell(11);
							celda.setCellValue(cicloActual);
							celda = row.getCell(10);
							int nAudit = Integer.parseInt(dataFormatter.formatCellValue(celda));
							celda.setCellValue(nAudit + 1);
							straux = str;
							break;
						}
					}
					lista.remove(straux);
				}

			}
			libro.write(archivo);
			archivo.close();
			archivoInput.close();
			libro.close();
			this.iniciado=1;
			arbol.marcarAuditados(copia);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int countDepend(String str) {
		int posicion, contador = 0;
		char caracter = ';';
		posicion = str.indexOf(caracter);
		while (posicion != -1) {
			contador++;
			posicion = str.indexOf(caracter, posicion + 1);
		}
		return contador;
	}

}
