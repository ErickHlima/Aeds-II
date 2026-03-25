import java.util.Scanner;

public class somaDigito {

    public static boolean Fim(String str) {
        return str.length() == 3 && str.charAt(0) == 'F' && str.charAt(1) == 'I' && str.charAt(2) == 'M';
    }

    public static int somaDigitos(int n) {
        int resp = 0;
        if (n == 0)
            return resp;
        else
            return (n % 10) + somaDigitos(n / 10);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str;
        boolean fim;
        do {
            str = sc.nextLine();
            fim = Fim(str);
            if (!fim) {
                int n = Integer.parseInt(str);
                System.out.println(somaDigitos(n));
            }
        } while (!fim);
        sc.close();
    }

}
