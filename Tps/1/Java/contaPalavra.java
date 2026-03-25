import java.util.*;

public class contaPalavra {

    public static boolean Fim(String str) {
        return str.length() == 3 && str.charAt(0) == 'F' && str.charAt(1) == 'I' && str.charAt(2) == 'M';
    }

    public static int Contador(String str) {
        int l = str.length();
        int cont = 1;
        for (int i = 0; i < l; i++) {
            if (str.charAt(i) == ' ')
                cont++;
        }
        return cont;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean fim;
        do {
            String str = sc.nextLine();
            fim = Fim(str);
            if (!fim)
                System.out.println(Contador(str));
        } while (!fim);
        sc.close();
    }
}
