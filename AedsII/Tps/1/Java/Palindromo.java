import java.util.Scanner;

public class Palindromo {
    public static boolean ehPalindromo(String str) {
        int i = 0;
        int j = str.length() - 1;
        boolean resp = true;
        while (i < j) {
            if (str.charAt(i) != str.charAt(j)) {
                resp = false;
                i = j;
            } else {
                i++;
                j--;
            }
        }
        return resp;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNext()) {
            String str = sc.nextLine();
            if (ehPalindromo(str)) {
                System.out.println("SIM");
            } else
                System.out.println("NAO");
        }

        sc.close();
    }
}
