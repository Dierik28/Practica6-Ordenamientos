package Interfaz;

import Ordenamientos.Ordenamiento;
import Ordenamientos.Archivo;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class OrdenamientosController {

    private Ordenamiento ordenamiento = new Ordenamiento();
    private Archivo archivo = new Archivo();
    private String[][] datos;

    private ComboBox<String> selectorColumnas;
    private TableView<String[]> tablaDatos;
    private BarChart<String, Number> graficaTiempos;
    private TextArea areaResultados;
    private ProgressBar barraProgreso;

    private List<CheckBox> checkboxesMetodos;

    public void iniciar(Stage stage) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #2c3e50, #34495e);");

        root.setTop(crearEncabezado());
        root.setLeft(crearPanelControl());
        root.setCenter(crearPanelCentral());
        root.setBottom(crearPiePagina());

        Scene scene = new Scene(root, 1200, 800);

        stage.setTitle("Algoritmos de ordenamiento");
        stage.setScene(scene);
        stage.show();
    }

    private VBox crearEncabezado() {
        Label titulo = new Label("Algoritmos de ordenamiento");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titulo.setTextFill(Color.WHITE);

        Label subtitulo = new Label("Compara el rendimiento de diferentes métodos de ordenamiento");
        subtitulo.setFont(Font.font("Arial", 14));
        subtitulo.setTextFill(Color.LIGHTGRAY);

        VBox encabezado = new VBox(10, titulo, subtitulo);
        encabezado.setAlignment(Pos.CENTER);
        encabezado.setPadding(new Insets(20));
        encabezado.setStyle("-fx-background-color: #34495e; -fx-border-color: #3498db; -fx-border-width: 0 0 2 0;");

        return encabezado;
    }

    private VBox crearPanelControl() {
        VBox panelControl = new VBox(15);
        panelControl.setPadding(new Insets(20));
        panelControl.setPrefWidth(300);
        panelControl.setStyle("-fx-background-color: #ecf0f1; -fx-border-color: #bdc3c7; -fx-border-width: 0 2 0 0;");

        Label lblDatos = new Label("GESTIÓN DE DATOS");
        lblDatos.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        lblDatos.setTextFill(Color.DARKSLATEBLUE);

        Button btnCargar = crearBotonEstilizado(" Cargar Conjunto de Datos", "#3498db");
        btnCargar.setOnAction(e -> cargarDataset());

        Label lblColumna = new Label("Columna a ordenar:");
        lblColumna.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        selectorColumnas = new ComboBox<>();
        selectorColumnas.setPrefWidth(250);

        Label lblMetodos = new Label("Métodos de ordenamiento");
        lblMetodos.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        lblMetodos.setTextFill(Color.DARKSLATEBLUE);

        VBox contenedorMetodos = crearPanelMetodos();

        Button btnEjecutar = crearBotonEstilizado("Aplicar", "#27ae60");
        btnEjecutar.setOnAction(e -> ejecutarComparacion());

        Button btnLimpiar = crearBotonEstilizado("Borrar resultados", "#e74c3c");
        btnLimpiar.setOnAction(e -> limpiarResultados());

        Button btnSalir = crearBotonEstilizado("Salir", "#7f8c8d");
        btnSalir.setOnAction(e -> System.exit(0));

        barraProgreso = new ProgressBar(0);
        barraProgreso.setPrefWidth(250);
        barraProgreso.setVisible(false);

        panelControl.getChildren().addAll(
                lblDatos, btnCargar, lblColumna, selectorColumnas,
                new Separator(), lblMetodos, contenedorMetodos,
                new Separator(), btnEjecutar, btnLimpiar, barraProgreso, btnSalir
        );

        return panelControl;
    }

    private VBox crearPanelMetodos() {
        checkboxesMetodos = new ArrayList<>();

        String[] metodos = {
                "QuickSort",
                "MergeSort",
                "ShellSort",
                "Selección Directa",
                "Radix Sort",
                "Sort",
                "Parallel Sort",
        };

        VBox contenedor = new VBox(8);

        for (String metodo : metodos) {
            CheckBox cb = new CheckBox(metodo);
            cb.setStyle("-fx-font-weight: bold;");
            checkboxesMetodos.add(cb);
            contenedor.getChildren().add(cb);
        }

        checkboxesMetodos.get(0).setSelected(true);
        checkboxesMetodos.get(1).setSelected(true);
        checkboxesMetodos.get(6).setSelected(true);

        return contenedor;
    }

    private TabPane crearPanelCentral() {
        TabPane tabPane = new TabPane();

        Tab tabDatos = new Tab("Datos");
        tabDatos.setContent(crearTablaDatos());
        tabDatos.setClosable(false);

        Tab tabResultados = new Tab("Resultados");
        tabResultados.setContent(crearPanelResultados());
        tabResultados.setClosable(false);

        Tab tabGrafica = new Tab("Graficas");
        tabGrafica.setContent(crearGrafica());
        tabGrafica.setClosable(false);

        tabPane.getTabs().addAll(tabDatos, tabResultados, tabGrafica);

        return tabPane;
    }

    private ScrollPane crearTablaDatos() {
        tablaDatos = new TableView<>();
        tablaDatos.setPlaceholder(new Label("Cargue los datos"));

        ScrollPane scrollPane = new ScrollPane(tablaDatos);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        return scrollPane;
    }

    private BorderPane crearPanelResultados() {
        areaResultados = new TextArea();
        areaResultados.setEditable(false);
        areaResultados.setFont(Font.font("Consolas", 12));
        areaResultados.setStyle("-fx-control-inner-background: #1e272e; -fx-text-fill: #ffffff;");

        BorderPane panel = new BorderPane();
        panel.setCenter(areaResultados);
        panel.setPadding(new Insets(10));

        return panel;
    }

    private BorderPane crearGrafica() {
        CategoryAxis ejeX = new CategoryAxis();
        ejeX.setLabel("Métodos de Ordenamiento");

        NumberAxis ejeY = new NumberAxis();
        ejeY.setLabel("Tiempo (nanosegundos)");

        graficaTiempos = new BarChart<>(ejeX, ejeY);
        graficaTiempos.setTitle("Comparativa de tiempos");
        graficaTiempos.setLegendVisible(true);
        graficaTiempos.setAnimated(false);

        BorderPane panel = new BorderPane();
        panel.setCenter(graficaTiempos);
        panel.setPadding(new Insets(10));

        return panel;
    }

    private HBox crearPiePagina() {
        Label estado = new Label("Listo para comenzar");
        estado.setTextFill(Color.WHITE);
        estado.setPadding(new Insets(10));

        HBox piePagina = new HBox(estado);
        piePagina.setStyle("-fx-background-color: #2c3e50;");
        piePagina.setAlignment(Pos.CENTER_LEFT);

        return piePagina;
    }

    private Button crearBotonEstilizado(String texto, String color) {
        Button boton = new Button(texto);
        boton.setPrefWidth(250);
        boton.setStyle(String.format(
                "-fx-background-color: %s; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12px; -fx-background-radius: 5;",
                color
        ));

        boton.setOnMouseEntered(e -> boton.setStyle(String.format(
                "-fx-background-color: derive(%s, 20%%); -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12px; -fx-background-radius: 5;",
                color
        )));
        boton.setOnMouseExited(e -> boton.setStyle(String.format(
                "-fx-background-color: %s; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12px; -fx-background-radius: 5;",
                color
        )));

        return boton;
    }

    private void cargarDataset() {
        archivo.leerArchivoCSV();
        datos = archivo.getDatos();

        if (datos == null || datos.length == 0) {
            mostrarAlerta("Error", "No se pudo cargar el archivo CSV.");
            return;
        }

        selectorColumnas.getItems().clear();
        String[] encabezados = datos[0];

        for (int i = 0; i < encabezados.length; i++) {
            encabezados[i] = traducirEncabezado(encabezados[i]);
        }

        selectorColumnas.getItems().addAll(encabezados);
        selectorColumnas.getItems().add("Todas las columnas numéricas");
        selectorColumnas.setValue(encabezados[0]);

        mostrarDatosEnTabla();

        areaResultados.setText("Conjunto de datos cargado exitosamente.\n" +
                "Filas: " + (datos.length - 1) + "\n" +
                "Columnas: " + encabezados.length + "\n\n" +
                "Seleccione los métodos de ordenamiento y haga clic en 'Aplicar'");
    }

    private String traducirEncabezado(String encabezado) {
        switch (encabezado) {
            case "Formatted Date": return "Fecha";
            case "Summary": return "Resumen";
            case "Precip Type": return "Tipo de precipitacion";
            case "Temperature (C)": return "Temperatura (°C)";
            case "Apparent Temperature (C)": return "Temperatura Aparente (°C)";
            case "Humidity": return "Humedad";
            case "Wind Speed (km/h)": return "Velocidad del Viento (km/h)";
            case "Wind Bearing (degrees)": return "Dirección del Viento (grados)";
            case "Visibility (km)": return "Visibilidad (km)";
            case "Loud Cover": return "Cobertura de Nubes";
            case "Pressure (millibars)": return "Presión (milibares)";
            case "Daily Summary": return "Resumen Diario";
            default: return encabezado;
        }
    }

    private void mostrarDatosEnTabla() {
        tablaDatos.getColumns().clear();

        if (datos == null || datos.length < 2) return;

        String[] encabezados = datos[0];
        for (int i = 0; i < encabezados.length; i++) {
            final int colIndex = i;
            String encabezadoTraducido = traducirEncabezado(encabezados[i]);
            TableColumn<String[], String> columna = new TableColumn<>(encabezadoTraducido);
            columna.setCellValueFactory(param ->
                    new javafx.beans.property.SimpleStringProperty(param.getValue()[colIndex]));
            columna.setPrefWidth(120);
            tablaDatos.getColumns().add(columna);
        }

        ObservableList<String[]> items = FXCollections.observableArrayList();
        for (int i = 1; i < datos.length && i < 1000; i++) {
            items.add(datos[i]);
        }
        tablaDatos.setItems(items);
    }

    private void ejecutarComparacion() {
        if (datos == null || datos.length < 2) {
            mostrarAlerta("Advertencia", "Primero cargue un conjunto de datos.");
            return;
        }

        String columnaSeleccionada = selectorColumnas.getValue();
        if (columnaSeleccionada == null) {
            mostrarAlerta("Advertencia", "Seleccione una columna para ordenar.");
            return;
        }

        List<String> metodosSeleccionados = obtenerMetodosSeleccionados();
        if (metodosSeleccionados.isEmpty()) {
            mostrarAlerta("Advertencia", "Seleccione al menos un método de ordenamiento.");
            return;
        }

        barraProgreso.setVisible(true);
        barraProgreso.setProgress(0);

        new Thread(() -> {
            List<Resultado> resultados = new ArrayList<>();

            if (columnaSeleccionada.equals("Todas las columnas numéricas")) {
                List<Integer> columnasNumericas = obtenerColumnasNumericas();
                double progresoPorColumna = 1.0 / columnasNumericas.size();

                for (int i = 0; i < columnasNumericas.size(); i++) {
                    int idxColumna = columnasNumericas.get(i);
                    String nombreColumna = datos[0][idxColumna];
                    int[] valores = convertirColumnaAEnteros(idxColumna);

                    for (String metodo : metodosSeleccionados) {
                        long tiempo = ejecutarMetodoOrdenamiento(metodo, valores.clone());
                        resultados.add(new Resultado(nombreColumna, metodo, tiempo));
                    }

                    final double progreso = (i + 1) * progresoPorColumna;
                    Platform.runLater(() ->
                            barraProgreso.setProgress(progreso));
                }
            } else {
                int idxColumna = selectorColumnas.getItems().indexOf(columnaSeleccionada);
                int[] valores = convertirColumnaAEnteros(idxColumna);
                double progresoPorMetodo = 1.0 / metodosSeleccionados.size();

                for (int i = 0; i < metodosSeleccionados.size(); i++) {
                    String metodo = metodosSeleccionados.get(i);
                    long tiempo = ejecutarMetodoOrdenamiento(metodo, valores.clone());
                    resultados.add(new Resultado(columnaSeleccionada, metodo, tiempo));

                    final double progreso = (i + 1) * progresoPorMetodo;
                    Platform.runLater(() ->
                            barraProgreso.setProgress(progreso));
                }
            }
            Platform.runLater(() -> {
                mostrarResultados(resultados);
                mostrarGrafica(resultados);
                barraProgreso.setVisible(false);
            });

        }).start();
    }

    private List<String> obtenerMetodosSeleccionados() {
        List<String> metodos = new ArrayList<>();
        String[] nombresMetodos = {
                "QuickSort",
                "MergeSort",
                "ShellSort",
                "Selección Directa",
                "Radix Sort",
                "Sort",
                "ParallelSort"
        };

        for (int i = 0; i < checkboxesMetodos.size(); i++) {
            if (checkboxesMetodos.get(i).isSelected()) {
                metodos.add(nombresMetodos[i]);
            }
        }
        return metodos;
    }

    private int[] convertirColumnaAEnteros(int indiceColumna) {
        List<Integer> valores = new ArrayList<>();
        for (int i = 1; i < datos.length; i++) {
            try {
                valores.add((int) Double.parseDouble(datos[i][indiceColumna]));
            } catch (NumberFormatException e) {
            }
        }
        return valores.stream().mapToInt(Integer::intValue).toArray();
    }

    private long ejecutarMetodoOrdenamiento(String metodo, int[] valores) {
        long inicio = System.nanoTime();

        switch (metodo) {
            case "QuickSort":
                Ordenamiento.quickSort(valores, 0, valores.length - 1);
                break;
            case "MergeSort":
                Ordenamiento.mergeSort(valores, 0, valores.length - 1);
                break;
            case "ShellSort":
                Ordenamiento.shellSort(valores);
                break;
            case "Selección Directa":
                Ordenamiento.seleccionDirecta(valores);
                break;
            case "Radix Sort":
                Ordenamiento.radixSort(valores);
                break;
            case "Sort":
                ordenamiento.sort(valores);
                break;
            case "ParallelSort":
                ordenamiento.parallelSort(valores);
                break;
        }

        return System.nanoTime() - inicio;
    }

    private void mostrarResultados(List<Resultado> resultados) {
        StringBuilder sb = new StringBuilder();
        sb.append("Resultados de la comparación\n");
        sb.append("============================\n\n");

        Resultado masRapido = resultados.stream()
                .min((r1, r2) -> Long.compare(r1.tiempo, r2.tiempo))
                .orElse(null);

        for (Resultado resultado : resultados) {
            String destacado = resultado == masRapido ? " MÁS RÁPIDO" : "";
            sb.append(String.format("Columna: %-25s | Método: %-20s | Tiempo: %d ns%s\n",
                    resultado.columna, resultado.metodo, resultado.tiempo, destacado));
        }

        if (masRapido != null) {
            sb.append("\nConclusión:\n");
            sb.append(String.format("El método más eficiente fue '%s' en la columna '%s' con %d ns",
                    masRapido.metodo, masRapido.columna, masRapido.tiempo));
        }

        areaResultados.setText(sb.toString());
    }

    private void mostrarGrafica(List<Resultado> resultados) {
        graficaTiempos.getData().clear();

        resultados.stream()
                .map(r -> r.columna)
                .distinct()
                .forEach(columna -> {
                    XYChart.Series<String, Number> serie = new XYChart.Series<>();
                    serie.setName(columna);

                    resultados.stream()
                            .filter(r -> r.columna.equals(columna))
                            .forEach(r -> {
                                serie.getData().add(new XYChart.Data<>(r.metodo, r.tiempo));
                            });

                    graficaTiempos.getData().add(serie);
                });
    }

    private List<Integer> obtenerColumnasNumericas() {
        List<Integer> indices = new ArrayList<>();
        if (datos == null || datos.length < 2) return indices;

        for (int i = 0; i < datos[0].length; i++) {
            boolean esNumerica = true;
            for (int j = 1; j < Math.min(datos.length, 100); j++) {
                try {
                    Double.parseDouble(datos[j][i]);
                } catch (NumberFormatException e) {
                    esNumerica = false;
                    break;
                }
            }
            if (esNumerica) indices.add(i);
        }
        return indices;
    }

    private void limpiarResultados() {
        areaResultados.clear();
        graficaTiempos.getData().clear();
        barraProgreso.setProgress(0);
        barraProgreso.setVisible(false);
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private static class Resultado {
        String columna;
        String metodo;
        long tiempo;

        Resultado(String columna, String metodo, long tiempo) {
            this.columna = columna;
            this.metodo = metodo;
            this.tiempo = tiempo;
        }
    }
}