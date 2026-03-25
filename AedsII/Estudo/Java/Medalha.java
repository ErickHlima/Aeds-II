import java.util.Scanner;

public class Medalha {
    public String Pais;
    public int Ouro;
    public int Prata;
    public int Bronze;

    public Medalha(String Pais, int Ouro, int Prata, int Bronze){
        this.Pais = Pais;
        this.Ouro = Ouro;
        this.Prata = Prata;
        this.Bronze = Bronze;
    }

    public static void swap(Medalha[] med, int i, int j){
        Medalha tmp = med[j];
        med[j] = med[i];
        med[i] = tmp;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        String Pais;
        int Ouro;
        int Prata;
        int Bronze;
        Medalha[] pais = new Medalha[n];
        for (int i = 0; i < n; i++){
            Pais = sc.next();
            Ouro = sc.nextInt();
            Prata = sc.nextInt();
            Bronze = sc.nextInt();
            pais[i] = new Medalha(Pais, Ouro, Prata, Bronze);
        }

        for(int i = 0; i < n - 1; i++){ // Ordenando
            int menor = i;
            for(int j = i + 1; j < n; j++){
                if(pais[j].Ouro < pais[menor].Ouro){
                    menor = j;
                }
            }
            swap(pais, i, menor);
        }

        for(int i = 0; i < n - 1; i++){ // Ordenando
            int menor = i;
            for(int j = i + 1; j < n; j++){
                if(pais[j].Ouro == pais[menor].Ouro && pais[j].Prata < pais[menor].Prata){
                    menor = j;
                }
            }
            swap(pais, i, menor);
        }

        for(int i = 0; i < n - 1; i++){ // Ordenando
            int menor = i;
            for(int j = i + 1; j < n; j++){
                if(pais[j].Ouro == pais[menor].Ouro && pais[j].Prata == pais[j].Prata && pais[j].Bronze < pais[menor].Bronze){
                    menor = j;
                }
            }
            swap(pais, i, menor);
        }

        for(int i = n - 1; i >= 0; i--){ // Ordenando
            System.out.println(pais[i].Pais + " " + pais[i].Ouro + " " + pais[i].Prata + " " + pais[i].Bronze);
        }

        sc.close();
    }
}   
