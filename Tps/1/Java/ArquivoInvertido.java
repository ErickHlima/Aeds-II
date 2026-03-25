import java.io.RandomAccessFile;
import java.io.IOException;
import java.util.Scanner;

public class ArquivoInvertido {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        // Escrita dos valores no arquivo
        try (RandomAccessFile arq = new RandomAccessFile("valores.txt", "rw")) {
            for (int i = 0; i < n; i++) {
                double valor = sc.nextDouble();
                arq.writeDouble(valor);
            }
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arq: " + e.getMessage());
        }

        try (RandomAccessFile arq = new RandomAccessFile("valores.txt", "r")) {
            for (int i = n - 1; i >= 0; i--) {
                arq.seek(i * 8);
                double valor = arq.readDouble();
                
                // Verifica se o numero e inteiro (841.0 -> 841)
                if (valor == (long) valor) {
                    System.out.println((long) valor); // Imprime sem o .0
                } else {
                    System.out.println(valor);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arq: " + e.getMessage());
        }
        
        sc.close();
    }
}
