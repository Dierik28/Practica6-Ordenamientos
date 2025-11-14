package Ordenamientos;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Archivo {
    private String[][] datos;

    public String[][] getDatos() {
        return datos;
    }

    public void leerArchivoCSV() {
        ArrayList<String[]> datosLeidos = new ArrayList<>();

        try (InputStream inputStream = getClass().getResourceAsStream("/Info/weatherHistory.csv");
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {

            String linea;
            while ((linea = br.readLine()) != null) {
                datosLeidos.add(linea.split(","));
            }
            datos = datosLeidos.toArray(new String[0][0]);
            System.out.println("Se ha cargado el archivo");
            System.out.println("Filas: " + datos.length + ", Columnas: " + (datos.length > 0 ? datos[0].length : 0));
            return;

        } catch (Exception e) {
            System.out.println("No se pudo cargar el archivo: " + e.getMessage());
        }
    }
}