package Ordenamientos;

public class Ordenamiento {


    public static void quickSort(int[] lista, int inicio, int fin) {
        if (inicio < fin) {
            int pivote = recursivo(lista, inicio, fin);
            quickSort(lista, inicio, pivote - 1);
            quickSort(lista, pivote + 1, fin);
        }
    }

    private static int recursivo(int[] lista, int inicio, int fin) {
        int pivote = lista[(inicio + fin) / 2];
        int i = inicio;
        int j = fin;

        while (i <= j) {
            while (lista[i] < pivote) {
                i++;
            }
            while (lista[j] > pivote) {
                j--;
            }

            if (i <= j) {
                int aux = lista[i];
                lista[i] = lista[j];
                lista[j] = aux;
                i++;
                j--;
            }
        }

        return i - 1;
    }


    public static void mergeSort(int[] lista, int izquierda, int derecha) {
        if (izquierda < derecha) {
            int medio = (izquierda + derecha) / 2;
            mergeSort(lista, izquierda, medio);
            mergeSort(lista, medio + 1, derecha);
            merge(lista, izquierda, medio, derecha);
        }
    }

    private static void merge(int[] lista, int izquierda, int medio, int derecha) {
        int n1 = medio - izquierda + 1;
        int n2 = derecha - medio;

        int[] L = new int[n1];
        int[] R = new int[n2];

        System.arraycopy(lista, izquierda, L, 0, n1);
        System.arraycopy(lista, medio + 1, R, 0, n2);

        int i = 0, j = 0, k = izquierda;

        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                lista[k++] = L[i++];
            } else {
                lista[k++] = R[j++];
            }
        }

        while (i < n1) lista[k++] = L[i++];
        while (j < n2) lista[k++] = R[j++];
    }

    public static void shellSort(int[] lista) {
        int n = lista.length;

        for (int inter = n / 2; inter > 0; inter /= 2) {
            for (int i = inter; i < n; i++) {
                int temp = lista[i];
                int j = i;

                while (j >= inter && lista[j - inter] > temp) {
                    lista[j] = lista[j - inter];
                    j -= inter;
                }

                lista[j] = temp;
            }
        }
    }

    public static void seleccionDirecta(int[] lista) {
        int n = lista.length;

        for (int i = 0; i < n - 1; i++) {
            int k = i;

            for (int j = i + 1; j < n; j++) {
                if (lista[j] < lista[k]) {
                    k = j;
                }
            }

            intercambiar(lista, i, k);
        }
    }

    private static void intercambiar(int[] lista, int i, int j) {
        int temp = lista[i];
        lista[i] = lista[j];
        lista[j] = temp;
    }

    public static void radixSort(int[] lista) {
        int n = lista.length;
        if (n == 0) return;

        int max = lista[0];
        int min = lista[0];

        for (int v : lista) {
            if (v > max) max = v;
            if (v < min) min = v;
        }

        int offset = -min;
        if (min < 0) {
            for (int i = 0; i < n; i++) lista[i] += offset;
            max += offset;
        }

        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSort(lista, exp);
        }

        if (min < 0) {
            for (int i = 0; i < n; i++) lista[i] -= offset;
        }
    }

    private static void countingSort(int[] lista, int exp) {
        int n = lista.length;
        int[] salida = new int[n];
        int[] contador = new int[10];

        for (int num : lista) {
            contador[(num / exp) % 10]++;
        }

        for (int i = 1; i < 10; i++) {
            contador[i] += contador[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            int dig = (lista[i] / exp) % 10;
            salida[--contador[dig]] = lista[i];
        }

        System.arraycopy(salida, 0, lista, 0, n);
    }

    public static void sort(int[] lista) {
        java.util.Arrays.sort(lista);
    }

    public static void parallelSort(int[] lista) {
        java.util.Arrays.parallelSort(lista);
    }
}