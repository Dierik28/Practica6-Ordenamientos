package Ordenamientos;

public class Ordenamiento {

    public static void quickSort(double [] lista, int inicio, int fin) {
        int izquierda = inicio;
        int derecha = fin;
        int posicion = inicio;
        boolean bandera = true;
        while (bandera) {
            bandera = false;
            while (lista[posicion] <= lista[derecha] && posicion != derecha) {
                derecha--;
                if (posicion != derecha) {
                    int aux = (int) lista[posicion];
                    lista[posicion] = lista[derecha];
                    lista[derecha] = aux;
                    posicion = derecha;
                    while (lista[posicion] > lista[izquierda] && posicion != izquierda) {
                        izquierda++;
                    }
                    if (posicion != izquierda) {
                        bandera = true;
                        aux = (int) lista[posicion];
                        lista[posicion] = lista[izquierda];
                        lista[izquierda] = aux;
                        posicion = izquierda;
                        if ((posicion - 1) > inicio) {
                            quickSort(lista, izquierda, posicion - 1);
                        }
                        if (fin > (posicion + 1)) {
                            quickSort(lista, posicion + 1, fin);
                        }
                    }
                }
            }
        }
    }


    public static void mergeSort(double [] lista, int p, int r) {
        if (p < r) {
            int q = (p + r) / 2;
            mergeSort(lista, q, p);
            mergeSort(lista, q + 1, r);
            merge(lista, q, p, r);
        }
    }

    public static void merge(double [] lista, int p, int r, int q) {
        int n1 = q - p+1;
        int n2 = r - q;

        int[] L = new int[n1 + 1];
        int[] R = new int[n2 + 1];

        for (int i = 0; i < n1; i++) {
            L[i] = (int) lista[p + 1];
        }
        for (int j = 0; j < n2; j++) {
            R[j] = (int) lista[q + 1 + j];
        }

        int i = 0, j = 0;

        for(int k = p; k <= r; k++) {
            if (L[i] <= R[j]) {
                lista[k] = L[i];
                i++;
            }else{
                lista[k] = R[j];
                j++;
            }
        }
    }

    public static void shellSort(double [] lista) {
        int n = lista.length;
        int inter = n;

        while (inter > 1) {
            inter /= 2;
            boolean bandera = true;

            while (bandera) {
                bandera = false;
                int i = 0;
                while((i + inter) < n) {
                    if(lista[i] > lista[i + inter]) {
                        int aux = (int) lista [i];
                        lista[i] = lista[i + inter];
                        lista[i + inter] = aux;
                        bandera = true;
                    }
                    i++;
                }
            }
        }
    }

    public static void seleccionDirecta(double [] lista){
        int n = lista.length;

        for(int i = 0; i < n - 1; i++){
            int menor = (int) lista[i];
            int k = i;
            for(int j = i + 1; j < n; j++){
                if(lista[j] < menor){
                    menor = (int) lista[j];
                    k = j;
                }
            }
            lista[k] = lista[i];
            lista[i] = menor;
        }
    }

    public static void radixSort(double [] lista) {
        if(lista.length <= 1)return;
        int max = getMax(lista);
        int exp = 1;
        int pasada = 1;
        while(max / exp > 0){
            countingSort(lista, exp);
            exp *= 10;
            pasada ++;
        }
    }

    public static void countingSort(double [] lista, int exp){
        int n = lista.length;
        int [] salida = new int[n];
        int [] contador = new int[10];
        for(int i = 0; i < n; i++){
            int dig = (int) ((lista[i] / exp) % 10);
            contador[dig]++;
        }
        for(int i = 1; i < 10; i++){
            contador[i] += contador[i - 1];
        }
        for(int i = n - 1; i >= 0; i--){
            int dig = (int) ((lista[i] / exp) % 10);
            int pos = contador[dig] - 1;
            salida[pos] = (int) lista[i];
            contador[dig]--;
        }
        for(int i = 0; i < n ; i++){
            lista[i] = salida[i];
        }
    }


    public static int getMax(double [] lista) {
        int m = (int) lista[0];
        for(double v : lista) if(v > m) m= (int) v;
        return m;
    }

    public void sort(double[] lista) {
        java.util.Arrays.sort(lista);
    }

    public void parallelSort(double[] lista) {
        java.util.Arrays.parallelSort(lista);
    }



}
