import java.util.*;

public class cifraCesarREC {
    public static boolean Fim(String str) {
        return str.length() == 3 && str.charAt(0) == 'F' && str.charAt(1) == 'I' && str.charAt(2) == 'M';
    }

    public static String cifra(String str, int i) {
        if (i == str.length()) return "";
        char c = str.charAt(i);
        c = (c >= 32 && c <= 126) ? (char) (c + 3) : c;
        return c + cifra(str, i + 1);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean fim;
        do {
            String str = sc.nextLine();
            fim = Fim(str);
            if (!fim)
                System.out.println(cifra(str, 0));
        } while (!fim);

        sc.close();
    }
}
