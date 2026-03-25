import java.util.*;

public class isREC {

    public static boolean Fim(String str) {
        return str.length() == 3 && str.charAt(0) == 'F' && str.charAt(1) == 'I' && str.charAt(2) == 'M';
    }

    public static boolean Vogal(String str, int i) {
        return (i == str.length()) ? true : ((str.charAt(i) == 'a' || str.charAt(i) == 'e' || str.charAt(i) == 'i' || str.charAt(i) == 'o' || str.charAt(i) == 'u' ||
                str.charAt(i) == 'A' || str.charAt(i) == 'E' || str.charAt(i) == 'I' || str.charAt(i) == 'O' || str.charAt(i) == 'U') && Vogal(str, i + 1));
    }

    public static boolean Consoante(String str, int i) {
        return (i == str.length()) ? true : (!(str.charAt(i) == 'a' || str.charAt(i) == 'e' || str.charAt(i) == 'i' || str.charAt(i) == 'o' || str.charAt(i) == 'u' ||
                str.charAt(i) == 'A' || str.charAt(i) == 'E' || str.charAt(i) == 'I' || str.charAt(i) == 'O' || str.charAt(i) == 'U') &&
                ((str.charAt(i) >= 'A' && str.charAt(i) <= 'Z') || (str.charAt(i) >= 'a' && str.charAt(i) <= 'z')) && Consoante(str, i + 1));
    }

    public static boolean Inteiro(String str, int i) {
        return (i == str.length()) ? true : ((str.charAt(i) >= '0' && str.charAt(i) <= '9') && Inteiro(str, i + 1));
    }

    
    public static boolean Real(String str, int i, int countSeparador) {
        return (i == str.length()) ? countSeparador == 1 || countSeparador == 0 : ((str.charAt(i) == '.' || str.charAt(i) == ',') ? Real(str, i + 1, countSeparador + 1) : ((str.charAt(i) >= '0' && str.charAt(i) <= '9') && Real(str, i + 1, countSeparador)));
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String str = sc.nextLine();
            if (Fim(str)) break;
            System.out.println((Vogal(str, 0) ? "SIM" : "NAO") +
                               (Consoante(str, 0) ? " SIM" : " NAO") +
                               (Inteiro(str, 0) ? " SIM" : " NAO") +
                               (Real(str, 0, 0) ? " SIM" : " NAO"));
        }
        sc.close();
    }
}
