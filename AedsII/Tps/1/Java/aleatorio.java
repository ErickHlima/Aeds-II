import java.util.*;

public class aleatorio {

    public static boolean fim(String str) {
        return str.length() == 3 && str.charAt(0) == 'F' && str.charAt(1) == 'I' && str.charAt(2) == 'M';
    }

    public static String rand(String str, char l1, char l2) {
        String resultado = "";
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == l1) {
                resultado += l2; // Substitui a primeira letra sorteada pela segunda
            } else {
                resultado += c;
            }
        }
        return resultado;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean fim;
        Random gerador = new Random();
        gerador.setSeed(4);

        do {
            char letra1 = (char) ('a' + (Math.abs(gerador.nextInt()) % 26));
            char letra2 = (char) ('a' + (Math.abs(gerador.nextInt()) % 26));
            String str = sc.nextLine();
            fim = fim(str);
            if (!fim)
                System.out.println(rand(str, letra1, letra2));

        } while (!fim);
        sc.close();
    }
}
