import java.util.*;

public class is {


    public static boolean Fim(String str) {
        return str.length() == 3 && str.charAt(0) == 'F' && str.charAt(1) == 'I' && str.charAt(2) == 'M';
    }

    // Verifica se a string é composta apenas por vogais
    public static boolean Vogal(String str) {
        boolean resp = true;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            // Se encontrar um caractere que não é uma vogal, retorna falso
            if (!(c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' ||
                    c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U')) {
                resp = false;
            }
        }
        return resp;
    }

    // Verifica se a string é composta apenas por consoantes
    public static boolean Consoante(String str) {
        boolean resp = true;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            // Se encontrar uma vogal, retorna falso
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' ||
                    c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U') {
                resp = false;
            }
        }
        return resp;
    }

    // Verifica se a string é um número inteiro (só números de 0 a 9)
    public static boolean Inteiro(String str) {
        boolean resp = true;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            // Se encontrar um caractere que não seja um número, retorna falso
            if (c < '0' || c > '9') {
                resp = false;
            }
        }
        return resp;
    }

    // Verifica se a string é um número real (pode ter um ponto ou vírgula como separador decimal)
    public static boolean Real(String str) {
        boolean resp = true;
        int countSeparador = 0;

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            // Conta quantos pontos ou vírgulas existem
            if (c == '.' || c == ',') {
                countSeparador++;
            } else if (c < '0' || c > '9') {
                resp = false;
            }
        }

        // Verifica se há mais de um separador ou se o separador está no começo ou final
        if (countSeparador > 1 || str.charAt(0) == '.' || str.charAt(0) == ',' ||
                str.charAt(str.length() - 1) == '.' || str.charAt(str.length() - 1) == ',') {
            resp = false;
        }

        return resp;
    }

    public static void main(String[] args) {
        boolean fim;
        String result;
        Scanner sc = new Scanner(System.in);
        do {
            String str = sc.nextLine();
            fim = Fim(str);
            if (!fim) {

                result = Vogal(str) ? "SIM" : "NAO";
                result += Consoante(str) ? " SIM" : " NAO";
                result += Inteiro(str) ? " SIM" : " NAO";
                result += Real(str) ? " SIM" : " NAO";
                System.out.println(result); // Exibe o resultado
            }
        } while (!fim);
        sc.close();
    }
}
