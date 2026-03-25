import java.util.Scanner;

public class PalindromoREC {

    public static boolean Fim(String str) {
        return str.length() == 3 && str.charAt(0) == 'F' && str.charAt(1) == 'I' && str.charAt(2) == 'M';
    }

    public static boolean ehPalindromo(String str, int i, int j) {
        return (i >= j) || (str.charAt(i) == str.charAt(j) && ehPalindromo(str, i + 1, j - 1));
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean fim;
        while (sc.hasNext()) {
            String str = sc.nextLine();
            fim = Fim(str);
            if (!fim)
            System.out.println(ehPalindromo(str, 0, str.length() - 1) ? "SIM" : "NAO");
        }
        sc.close();
    }
}
