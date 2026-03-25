import java.util.*;

public class inverso {
    
    public static boolean Fim(String str) {
        return str.length() == 3 && str.charAt(0) == 'F' && str.charAt(1) == 'I' && str.charAt(2) == 'M';
    }

    public static String inverte(String str){
        int l = str.length();
        String result = "";
        for(int i = l - 1; i >= 0; i--){
            result += str.charAt(i);
        }
        return result;
    }

    public static void main(String[] args) {
        boolean fim;
        Scanner sc = new Scanner(System.in);
        do{
        String str = sc.nextLine();
        fim = Fim(str);
        if (!fim)
        System.out.println(inverte(str));
        }while(!fim);
        sc.close();
    }
}
