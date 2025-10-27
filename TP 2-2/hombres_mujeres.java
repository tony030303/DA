
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class hombres_mujeres {

    //Implementación: Hombres proponen a mujeres
    // Reglas a seguir:
    // (i) No existen dos pares (h, m) y (h’, m’) en S con la propiedad que si (h, m’) ∉ P, h prefiere
    // a m’ en lugar de m, y m’ prefiere a h en lugar de h’. (Inestabilidad básica)
    // (ii) Existe un par(h, m) ∈ S, y un hombre h’, tal que h’ no es parte de ningún par en el
    // macheo, (h’, m) ∉ P, y m prefiere a h’ en lugar de h. (Hombre soltero más deseable y no
    // prohibido)
    // (iii) Existe un par(h, m) ∈ S, y una mujer m’, tal que m’ no es parte de ningún par en el
    // macheo, (h, m’) ∉ P, y h prefiere a m’ en lugar de m. (Mujer soltera más deseable y no
    // prohibida)
    // (iv) Existe un hombre h y una mujer m, ninguno de los cuales forman parte de una pareja
    // en el emparejamiento, es decir (h, m) ∉ P. (Hay dos personas solteras sin nada que les impida casarse entre ellas)
    public static void main(String[] args) {
        int h, m;
        h = 5; //establezco indices
        m = 4; //establezco indices

        //No hay restricciones (CASO BASE)
        boolean[][] prohibidos = {
            {false, false, false, false}, //hombre 0
            {false, false, false, false}, //hombre 1
            {false, false, false, false}, //hombre 2
            {false, false, false, false}, //hombre 3
            {false, false, false, false} //hombre 4
        };

        //Caso con restricciones, ejemplo
        /*
        boolean[][] prohibidos = {
            {false, false, false, false}, //hombre 0
            {false, true, false, false}, //hombre 1 (no puede salir con mujer 1)
            {false, false, false, false}, //hombre 2
            {false, false, false, false}, //hombre 3
            {false, false, false, false} //hombre 4
        };
         */
        int[][] rank_H = {
            {0, 1, 2, 3}, //hombre 0
            {0, 1, 2, 3}, //hombre 1
            {2, 3, 0, 1}, //hombre 2
            {3, 2, 1, 0}, //hombre 3
            {3, 2, 1, 0} //hombre 4
        };

        int[][] pref_M = {
            {0, 1, 2, 3, 4}, //mujer 0
            {1, 0, 2, 3, 4}, //mujer 1
            {4, 2, 1, 0, 3}, //mujer 2
            {4, 3, 2, 1, 0} //mujer 3
        };

        int[][] rank_M = new int[m][h]; //matriz de ranking inversa para las mujeres (para evitar búsquedas lineales)
        //ejemplo: si rank_M[0][3] = 2, significa que la mujer 0 le da el 2do lugar al hombre 3
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < h; j++) {
                int hombre = pref_M[i][j];
                rank_M[i][hombre] = j;
            }
        }

        int[][] parejas = new int[h][2];  //matriz de las parejas formadas
        int[] parejaDeMujer = new int[m]; //almacena la pareja de cada mujer (para evitar busquedas despues)
        Queue<Integer> solteros = new LinkedList<>(); //para evitar recorridos extra
        galeShapley(h, m, prohibidos, rank_H, rank_M, parejas, parejaDeMujer);

        System.out.println("---Parejas formadas (Hombre - Mujer)---");
        for (int i = 0; i < parejas.length; i++) {
            if (parejas[i][1] != -1) { //si tiene pareja
                System.out.println("Hombre " + i + " - Mujer " + parejas[i][1]);
            } else {
                solteros.add(i); //si no tiene pareja, lo agrego a la cola de solteros (para no recorrer todo de nuevo cuando los liste)
            }
        }

        System.out.println("\n---Solteras---");
        for (int i = 0; i < m; i++) {
            if (parejaDeMujer[i] == -1) { //si no tiene pareja
                System.out.println("Mujer " + i);
            }
        }

        System.out.println("\n---Solteros---");
        int solteroActual;
        while (!(solteros.isEmpty())) {
            solteroActual = solteros.poll();
            System.out.println("Hombre " + solteroActual);
        }

    }

    public static void galeShapley(int cantHombres, int cantMujeres, boolean[][] prohibidos, int[][] rank_H, int[][] rank_M, int[][] parejas, int[] parejaDeMujer) {
        //Método auxiliar donde se emparejan hombres y mujeres siguiendo las restricciones del algoritmo (Gale Shapley).
        int[] siguiente_mujer = new int[cantHombres]; //guardo la sig mujer a la que debe proponer cada hombre.
        Queue<Integer> cola_hombres_libres = new LinkedList<>();

        //inicialización
        Arrays.fill(parejaDeMujer, -1); //inicializo todas las mujeres sin pareja (lleno el arreglo de -1)

        for (int i = 0; i < cantHombres; i++) {
            cola_hombres_libres.add(i); //agrego todos los hombres a la cola de hombres libres
            parejas[i][0] = i; //asign el id d/c hombre.
            parejas[i][1] = -1; //valor default, no tiene pareja
            siguiente_mujer[i] = 0; //el primer indice de la lista de preferencias.
        }

        //mientras haya hombres libres
        while (!(cola_hombres_libres.isEmpty())) {
            //busco un hombre libre (reemplazar por uan cola, más facil menos búsqueda)
            int h = cola_hombres_libres.poll(); //saco el primer hombre libre de la cola

            //h guardará el id del hombre libre seleccionado.
            int m = -1; //guarda la candidata a proponer (inicialmente no hay ninguna)

            //busco la sig mujer a la que le va a proponer (no tiene que estar prohibida para él)
            while (m == -1 && siguiente_mujer[h] < cantMujeres) {
                int candidata = rank_H[h][siguiente_mujer[h]]; //mujer posible candidata a evaluar (según preferencia)
                if (!prohibidos[h][candidata]) {
                    //si la mujer no le es prohibida al hombre, entonces sirve
                    m = candidata;
                }
                siguiente_mujer[h]++; //aumento indice para probar en la sig iteración
            }

            //m ahora guardará el id de la mujer seleccionada para proponer (si es que no llegó al fin).
            if (m != -1) {
                //Caso 1 : si la mujer está libre, se emparejan
                if (parejaDeMujer[m] == -1) {
                    parejas[h][1] = m; //emparejados
                    parejaDeMujer[m] = h;

                } else { //Caso 2: si la mujer no está libre, compruebo si prefiere a otro h (en vez de a su pareja actual)

                    int h_actual = parejaDeMujer[m];

                    //compruebo si la mujer prefiere al nuevo hombre (h) o al que ya tiene (h_actual)
                    if (rank_M[m][h] < rank_M[m][h_actual]) { //si prefiere al nuevo, entonces la mujer rechaza al actual y se va con el nuevo

                        parejas[h][1] = m; //emparejados
                        parejas[h_actual][1] = -1; // ahora quedó libre
                        parejaDeMujer[m] = h;      // la mujer ahora está con h
                        //agrego al hombre a la cola
                        cola_hombres_libres.add(h_actual);

                    } //Caso 3: si no, la mujer sigue con su pareja actual y el hombre h sigue libre (no pasa nada)
                    else {
                        cola_hombres_libres.add(h); //vuelvo a agregar al hombre h a la cola porque sigue libre y verifico con otra mujer
                    }
                }
            }
            //Caso 4: si m == - 1, significa que el hombre h no tiene más mujeres a las que proponer (todas le son prohibidas o ya están emparejadas)
            //por lo que no puede hacer nada y sigue libre (caso que no deberia pasar si hay n mujeres y n hombres sin prohibidos).

        }
    }

}
