import java.util.*;

public class EmpresasProponen {

    public static int[] galeShapleyEmpresasProponen(int[][] prefEmpresas, int[][] prefEstudiantes) {
        int n = prefEmpresas.length;
        int[][] rankEst = new int[n][n];
        for (int s = 0; s < n; s++) {
            for (int pos = 0; pos < n; pos++) {
                int e = prefEstudiantes[s][pos];
                rankEst[s][e] = pos;
            }
        }
        int[] nextIdx = new int[n];
        int[] e2s = new int[n];
        int[] s2e = new int[n];
        for (int i = 0; i < n; i++) e2s[i] = -1;
        for (int i = 0; i < n; i++) s2e[i] = -1;
        Queue<Integer> libres = new LinkedList<>();
        for (int e = 0; e < n; e++) libres.add(e);
        while (!libres.isEmpty()) {
            int e = libres.poll();
            int s = prefEmpresas[e][nextIdx[e]++];
            if (s2e[s] == -1) {
                s2e[s] = e;
                e2s[e] = s;
            } else {
                int eActual = s2e[s];
                if (rankEst[s][e] < rankEst[s][eActual]) {
                    s2e[s] = e;
                    e2s[e] = s;
                    e2s[eActual] = -1;
                    libres.add(eActual);
                } else {
                    libres.add(e);
                }
            }
        }
        return e2s;
    }

    public static void main(String[] args) {
        String[] emp = {"A","B","C","D"};
        String[] est = {"w","x","y","z"};
        int[][] prefEmpresas = {
                {3,2,1,0},
                {0,1,2,3},
                {0,1,2,3},
                {0,1,2,3}
        };
        int[][] prefEstudiantes = {
                {0,1,2,3},
                {1,2,3,0},
                {2,3,0,1},
                {3,0,1,2}
        };
        int[] e2s = galeShapleyEmpresasProponen(prefEmpresas, prefEstudiantes);
        for (int e = 0; e < e2s.length; e++) {
            System.out.println(emp[e] + " <> " + est[e2s[e]]);
        }
    }
}
