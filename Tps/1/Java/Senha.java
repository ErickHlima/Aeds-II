import java.util.Scanner;

public class Senha {

    public static boolean Fim(String str) {
        return str.length() == 3 && str.charAt(0) == 'F' && str.charAt(1) == 'I' && str.charAt(2) == 'M';
    }

    public static boolean Verifica(String str) {
        boolean temMaiuscula = false;
        boolean temMinuscula = false;
        boolean temNumero = false;
        boolean temSimbolo = false;
        
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if(str.length() >= 8){
            if (c >= 'A' && c <= 'Z') {
                temMaiuscula = true;
            } else if (c >= 'a' && c <= 'z') {
                temMinuscula = true;
            } else if (c >= '0' && c <= '9') {
                temNumero = true;
            } else if ((c >= 33 && c <= 47) || (c >= 58 && c <= 64) || (c >= 91 && c <= 96) || (c >= 123 && c <= 126)) {
                temSimbolo = true;
            } else {
                return false; // Caractere inválido
            }
        } else {
            return false;
        }
        }
        
        return temMaiuscula && temMinuscula && temNumero && temSimbolo; 
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean fim;
        do {
            String str = sc.nextLine();
            fim = Fim(str);
            if (!fim)
                System.out.println(Verifica(str) ? "SIM" : "NAO");
        } while (!fim);
        sc.close();
    }

}
