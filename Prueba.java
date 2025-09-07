/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author pcoronel
 */
public class Prueba {

    public static void main(String[] args) {

//        // Crear primer heap
//        HeapBinomial heap1 = new HeapBinomial();
//        heap1.insertar(1);
//        heap1.insertar(3);
//        heap1.insertar(7);
//
//        System.out.println("Heap 1:");
//        System.out.println(heap1.toString());
//
//        // Crear segundo heap
//        HeapBinomial heap2 = new HeapBinomial();
//        heap2.insertar(5);
        ////        heap2.insertar(20);
////        heap2.insertar(1);
////
//        System.out.println("\nHeap 2:");
//        System.out.println(heap2.toString());
//
//        // Unir heaps
//        heap1.union(heap2);
//
//        System.out.println("\nHeap 1 luego de unir con Heap 2:");
//        System.out.println(heap1.toString());
        HeapBinomial heap = new HeapBinomial();
        // Insertamos algunos valores
//        heap.insertar(10);
//        heap.insertar(7);
//        heap.insertar(5);
//        heap.insertar(12);
//        heap.insertar(15);
//        heap.insertar(20);
//        heap.insertar(25);
//
//        System.out.println("Heap antes de extraer el mínimo:");
//        System.out.println(heap.toString());
//
//        // Extraemos el mínimo
//        int min = heap.extraerMin();
//
//        System.out.println("\nClave mínima extraída: " + min);
//        System.out.println("\nHeap después de extraer el mínimo:");
//        System.out.println(heap.toString());

        // Insertamos algunos valores
        heap.insertar(0);
        heap.insertar(1);
        heap.insertar(44);
        heap.insertar(22);
        heap.insertar(6666);

        System.out.println(heap.toString());
        boolean valor = heap.disminuirClave(22, 21);
        System.out.println("cambiado?:" + valor + "\n");
        System.out.println(heap.toString());

        //        // Eliminamos la raíz mínima (clave = 1)
        //        System.out.println("\nEliminamos el nodo con clave = 1");
        //        heap.eliminar(1);
        //        System.out.println(heap.toString());
        //
        //        // Eliminamos otro valor que no existe
        //        System.out.println("\nIntentamos eliminar el nodo con clave = 99");
        //        heap.eliminar(99);
        //        System.out.println(heap.toString());
    }

}
