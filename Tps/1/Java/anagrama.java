import java.util.*;

public class anagrama {

    public static boolean Fim(String str) {
        return str.length() == 3 && str.charAt(0) == 'F' && str.charAt(1) == 'I' && str.charAt(2) == 'M';
    }

    // Separa a linha em duas palavras ignorando o hifen e espaços
    public static String[] separar(String linha) {
        String s1 = "";
        String s2 = "";
        boolean segundaPalavra = false;

        for (int i = 0; i < linha.length(); i++) {
            char c = linha.charAt(i);
            if (c == '-') {
                segundaPalavra = true; // Comeca a segunda palavra ao encontrar o hifen
            } else if (c != ' ') {
                if (!segundaPalavra) {
                    s1 = s1 + c; // Primeira
                } else {
                    s2 = s2 + c; // Segunda
                }
            }
        }

        return new String[] { s1, s2 };
    }
    public static String toLowerCase(String str) {
        String novaStr = "";
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            // Converte para minusculo
            if (c >= 'A' && c <= 'Z') {
                c = (char) (c + 32);
            }
            novaStr = novaStr + c;
        }
        return novaStr;
    }
    

    public static boolean verificaAnagrama(String s1, String s2) {
        // Converter para minúsculas
        s1 = toLowerCase(s1);
        s2 = toLowerCase(s2);
    
        boolean ehAnagrama = (s1.length() == s2.length()); // Verifica o tamanho
    
        int[] freq = new int[256]; // Tabela ASCII
    
        for (int i = 0; i < s1.length() && ehAnagrama; i++) {
            freq[s1.charAt(i)]++; // Conta caracteres de s1
            freq[s2.charAt(i)]--; // Subtrai caracteres de s2
        }
    
        // Verifica se todas as posições ficaram zeradas
        for (int i = 0; i < 256 && ehAnagrama; i++) {
            if (freq[i] != 0) {
                ehAnagrama = false;
            }
        }
    
        return ehAnagrama;
    }
    

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean fim;
        do {
            String linha = sc.nextLine();
            fim = Fim(linha);
            if (!fim) {

                String[] palavras = separar(linha);
                String s1 = palavras[0];
                String s2 = palavras[1];

                System.out.println(verificaAnagrama(s1, s2) ? "SIM" : "NÃO");
            }
        } while (!fim);
        sc.close();
    }
}
