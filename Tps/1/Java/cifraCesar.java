import java.util.*;

public class cifraCesar {
    public static boolean fim(String str) {
        return str.length() == 3 && str.charAt(0) == 'F' && str.charAt(1) == 'I' && str.charAt(2) == 'M';
    }

    public static String cifra(String str) {
        int tam = str.length();
        int[] n = new int[tam];//n esta vazio corrigir
        char[] resp = new char[tam];

        for (int i = 0; i < tam; i++) {
            n[i] = str.charAt(i);
            if (n[i] >= 32 && n[i] <= 126) {
                n[i] = str.charAt(i) + 3;
            }
        }
        for (int i = 0; i < tam; i++) {
            resp[i] = (char) n[i];
        }
        return new String(resp);
    }

    public static void main(String[] args) {
        boolean fim;
        Scanner sc = new Scanner(System.in);

        do {
            String str = sc.nextLine();
            fim = fim(str);
            if (!fim)
                System.out.println(cifra(str));

        } while (!fim);
        sc.close();
    }
}
