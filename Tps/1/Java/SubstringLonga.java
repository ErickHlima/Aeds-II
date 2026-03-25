import java.util.Scanner;

public class SubstringLonga {

    public static boolean Fim(String str) {
        return str.length() == 3 && str.charAt(0) == 'F' && str.charAt(1) == 'I' && str.charAt(2) == 'M';
    }

    // Metodo que retorna o comprimento da maior substring sem caracteres repetidos
    public static int maiorSubstringSemRepeticao(String str) {
        int max = 0;
        int inicio = 0;
        int i, j;

        for (i = 0; i < str.length(); i++) {
            int novaPosicaoInicio = inicio; // Atualizar o inicio depois do for interno
            for (j = inicio; j < i; j++) {
                if (str.charAt(i) == str.charAt(j)) {
                    // Atualiza a nova posição de inicio
                    if (j + 1 > novaPosicaoInicio) {
                        novaPosicaoInicio = j + 1;
                    }
                }
            }
            inicio = novaPosicaoInicio; // Atualiza o inicio
            int tamAtual = i - inicio + 1;
            if (tamAtual > max) {
                max = tamAtual;
            }
        }

        return max;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean fim;
        do {
            String str = sc.nextLine();
            fim = Fim(str);
            if (!fim) {
                System.out.println(maiorSubstringSemRepeticao(str));
            }
        } while (!fim);
        sc.close();
    }
}
