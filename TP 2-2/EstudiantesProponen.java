import java.util.*;

public class EstudiantesProponen {

    public static int[] galeShapleyEstudiantesProponen(int[][] prefEstudiantes, int[][] prefEmpresas) {
        int n = prefEstudiantes.length;
        int[][] rankEmp = new int[n][n];
        for (int e = 0; e < n; e++) {
            for (int pos = 0; pos < n; pos++) {
                int s = prefEmpresas[e][pos];
                rankEmp[e][s] = pos;
            }
        }
        int[] nextIdx = new int[n];
        int[] s2e = new int[n];
        int[] e2s = new int[n];
        for (int i = 0; i < n; i++) s2e[i] = -1;
        for (int i = 0; i < n; i++) e2s[i] = -1;
        Queue<Integer> libres = new LinkedList<>();
        for (int s = 0; s < n; s++) libres.add(s);
        while (!libres.isEmpty()) {
            int s = libres.poll();
            int e = prefEstudiantes[s][nextIdx[s]++];
            if (e2s[e] == -1) {
                e2s[e] = s;
                s2e[s] = e;
            } else {
                int sActual = e2s[e];
                if (rankEmp[e][s] < rankEmp[e][sActual]) {
                    e2s[e] = s;
                    s2e[s] = e;
                    s2e[sActual] = -1;
                    libres.add(sActual);
                } else {
                    libres.add(s);
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
        int[] e2s = galeShapleyEstudiantesProponen(prefEstudiantes, prefEmpresas);
        for (int e = 0; e < e2s.length; e++) {
            System.out.println(emp[e] + " <> " + est[e2s[e]]);
        }
    }
}
