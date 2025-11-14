package Ordenamientos;

public class Ordenamiento {

    public static void quickSort(int[] lista, int inicio, int fin) {
        if (lista == null || lista.length == 0 || inicio < 0 || fin >= lista.length || inicio >= fin) {
            return;
        }

        int izquierda = inicio;
        int derecha = fin;
        int posicion = inicio;
        boolean bandera = true;

        while (bandera) {
            bandera = false;

            while (derecha >= 0 && posicion >= 0 && posicion < lista.length &&
                    derecha < lista.length && lista[posicion] <= lista[derecha] && posicion != derecha) {
                derecha--;
            }

            if (posicion != derecha && derecha >= 0 && derecha < lista.length) {
                int aux = lista[posicion];
                lista[posicion] = lista[derecha];
                lista[derecha] = aux;
                posicion = derecha;

                while (izquierda >= 0 && izquierda < lista.length && posicion >= 0 &&
                        posicion < lista.length && lista[posicion] >= lista[izquierda] && posicion != izquierda) {
                    izquierda++;
                }

                if (posicion != izquierda && izquierda >= 0 && izquierda < lista.length) {
                    bandera = true;
                    aux = lista[posicion];
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


    public static void mergeSort(int[] lista, int p, int r) {
        if (p < r) {
            int q = (p + r) / 2;
            mergeSort(lista, p, q);
            mergeSort(lista, q + 1, r);
            merge(lista, p, r, q);
        }
    }

    public static void merge(int[] lista, int p, int r, int q) {
        int n1 = q - p + 1;
        int n2 = r - q;

        int[] L = new int[n1 + 1];
        int[] R = new int[n2 + 1];

        for (int i = 0; i < n1; i++) {
            L[i] = lista[p + i];
        }
        for (int j = 0; j < n2; j++) {
            R[j] = lista[q + 1 + j];
        }

        L[n1] = Integer.MAX_VALUE;
        R[n2] = Integer.MAX_VALUE;

        int i = 0, j = 0;

        for(int k = p; k <= r; k++) {
            if (L[i] <= R[j]) {
                lista[k] = L[i];
                i++;
            } else {
                lista[k] = R[j];
                j++;
            }
        }
    }

    public static void shellSort(int[] lista) {
        if (lista == null || lista.length == 0) return;

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
                        int aux = lista[i];
                        lista[i] = lista[i + inter];
                        lista[i + inter] = aux;
                        bandera = true;
                    }
                    i++;
                }
            }
        }
    }

    public static void seleccionDirecta(int[] lista){
        if (lista == null || lista.length == 0) return;

        int n = lista.length;

        for(int i = 0; i < n - 1; i++){
            int menor = lista[i];
            int k = i;
            for(int j = i + 1; j < n; j++){
                if(lista[j] < menor){
                    menor = lista[j];
                    k = j;
                }
            }
            lista[k] = lista[i];
            lista[i] = menor;
        }
    }

    public static void radixSort(int[] lista) {
        if(lista == null || lista.length <= 1) return;

        int max = lista[0];
        int min = lista[0];
        for (int i = 1; i < lista.length; i++) {
            if (lista[i] > max) max = lista[i];
            if (lista[i] < min) min = lista[i];
        }


        int offset = 0;
        if (min < 0) {
            offset = -min;
            for (int i = 0; i < lista.length; i++) {
                lista[i] += offset;
            }
            max += offset;
        }


        int exp = 1;
        while(max / exp > 0){
            countingSort(lista, exp);
            exp *= 10;
        }

        if (offset > 0) {
            for (int i = 0; i < lista.length; i++) {
                lista[i] -= offset;
            }
        }
    }

    public static void countingSort(int[] lista, int exp){
        if (lista == null || lista.length == 0) return;

        int n = lista.length;
        int[] salida = new int[n];
        int[] contador = new int[10];

        for(int i = 0; i < n; i++){
            int dig = (lista[i] / exp) % 10;
            contador[dig]++;
        }

        for(int i = 1; i < 10; i++){
            contador[i] += contador[i - 1];
        }

        for(int i = n - 1; i >= 0; i--){
            int dig = (lista[i] / exp) % 10;
            int pos = contador[dig] - 1;
            if (pos >= 0 && pos < n) {
                salida[pos] = lista[i];
            }
            contador[dig]--;
        }
        for(int i = 0; i < n ; i++){
            lista[i] = salida[i];
        }
    }

    public static int getMax(int[] lista) {
        if (lista == null || lista.length == 0) return 0;

        int m = lista[0];
        for(int v : lista) {
            if(v > m) m = v;
        }
        return m;
    }

    public void sort(int[] lista) {
        if (lista != null && lista.length > 0) {
            java.util.Arrays.sort(lista);
        }
    }

    public void parallelSort(int[] lista) {
        if (lista != null && lista.length > 0) {
            java.util.Arrays.parallelSort(lista);
        }
    }
}
