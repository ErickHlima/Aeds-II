import java.util.Scanner;

class Piloto {
    public String nome;
    public int peso;
    public int rank;
         
    public Piloto(String nome,int peso,int rank) {
        this.nome =  nome;
        this.peso = peso;
        this.rank = rank;
    }   

    void print(){
        System.out.println(nome + " " + peso + " " + rank);
    }
}

class AnalisePiloto {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine(); // Consumir a quebra de linha após o número
        Piloto[] pilotos = new Piloto[n];
    
        for (int i = 0; i < n; i++){
            System.out.println("nome:");
            String nome = sc.nextLine();
            System.out.println("peso:");
            int peso = sc.nextInt();
            System.out.println("rank:");
            int rank = sc.nextInt();
            sc.nextLine(); // Consumir a quebra de linha após rank
            pilotos[i] = new Piloto(nome,peso,rank);
        }
        
        for (int i = 0; i < n - 1; i++){
            for (int j = 0; j < n - 1 - i; j++){
                int cmp = pilotos[j].nome.compareTo(pilotos[j + 1].nome);
                if(cmp > 0 ||
                   (cmp == 0 && pilotos[j].peso > pilotos[j + 1].peso) ||
                   (cmp == 0 && pilotos[j].peso == pilotos[j + 1].peso && pilotos[j].rank > pilotos[j + 1].rank)) {
                    Piloto temp = pilotos[j];
                    pilotos[j] = pilotos[j + 1];
                    pilotos[j + 1] = temp;
                }
            }
        }

        for (Piloto p : pilotos) {
            p.print();
        }
        sc.close();
    }
}
