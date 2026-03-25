import java.util.*;

public class Espelho {


    public static int inverte(int n){
        int reverso = 0;
        while (n > 0) {
            reverso = reverso * 10 + n % 10;
            n /= 10;
        }
        return reverso;
    }
    public static void main(String[] args) {
        int n1;
        int n2;
        int m;
        Scanner sc = new Scanner(System.in);


            n1 = sc.nextInt();
            n2 = sc.nextInt();
            m = (n2 - n1) + 1;
            for (int i = 0; i < m; i++){
               
                System.out.print(n1++);
               
            }
            for (int i = 0; i < m; i++){
                System.out.print(inverte(n2--));
            }
        sc.close();
    }
}

