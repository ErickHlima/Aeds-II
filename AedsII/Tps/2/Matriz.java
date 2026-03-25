import java.util.Scanner;

public class Matriz {
    private int[][] matriz;
    private int linhas;
    private int colunas;
    
    // Construtor - aloca células dinamicamente
    public Matriz(int linhas, int colunas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.matriz = new int[linhas][colunas];
    }
    
    // Método para definir um elemento
    public void setElemento(int linha, int coluna, int valor) {
        matriz[linha][coluna] = valor;
    }
    
    // Método para obter um elemento
    public int getElemento(int linha, int coluna) {
        return matriz[linha][coluna];
    }
    
    // Getters para dimensões
    public int getLinhas() {
        return linhas;
    }
    
    public int getColunas() {
        return colunas;
    }
    
    public Matriz soma(Matriz outra) {
        if (this.linhas != outra.linhas || this.colunas != outra.colunas) {
            return null; // Matrizes incompatíveis para soma
        }
        
        Matriz resultado = new Matriz(this.linhas, this.colunas);
        for (int i = 0; i < this.linhas; i++) {
            for (int j = 0; j < this.colunas; j++) {
                resultado.setElemento(i, j, this.matriz[i][j] + outra.matriz[i][j]);
            }
        }
        return resultado;
    }
    
    public Matriz multiplicacao(Matriz outra) {
        if (this.colunas != outra.linhas) {
            return null; // Matrizes incompatíveis para multiplicação
        }
        
        Matriz resultado = new Matriz(this.linhas, outra.colunas);
        for (int i = 0; i < this.linhas; i++) {
            for (int j = 0; j < outra.colunas; j++) {
                int soma = 0;
                for (int k = 0; k < this.colunas; k++) {
                    soma += this.matriz[i][k] * outra.matriz[k][j];
                }
                resultado.setElemento(i, j, soma);
            }
        }
        return resultado;
    }
    
    public void mostrarDiagonalPrincipal() {
        for (int i = 0; i < Math.min(linhas, colunas); i++) {
            System.out.print(matriz[i][i]);
            if (i < Math.min(linhas, colunas) - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }
    
    public void mostrarDiagonalSecundaria() {
        for (int i = 0; i < Math.min(linhas, colunas); i++) {
            System.out.print(matriz[i][colunas - 1 - i]);
            if (i < Math.min(linhas, colunas) - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }
    
    public void imprimir() {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                System.out.print(matriz[i][j]);
                if (j < colunas - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numCasos = scanner.nextInt();
        
        for (int caso = 0; caso < numCasos; caso++) {
            // Primeira matriz
            int l1 = scanner.nextInt();
            int c1 = scanner.nextInt();
            Matriz matriz1 = new Matriz(l1, c1);
            
            for (int i = 0; i < l1; i++) {
                for (int j = 0; j < c1; j++) {
                    matriz1.setElemento(i, j, scanner.nextInt());
                }
            }
            
            // Segunda matriz
            int l2 = scanner.nextInt();
            int c2 = scanner.nextInt();
            Matriz matriz2 = new Matriz(l2, c2);
            
            for (int i = 0; i < l2; i++) {
                for (int j = 0; j < c2; j++) {
                    matriz2.setElemento(i, j, scanner.nextInt());
                }
            }
            
            matriz1.mostrarDiagonalPrincipal();
            matriz1.mostrarDiagonalSecundaria();
            
            Matriz soma = matriz1.soma(matriz2);
            if (soma != null) {
                soma.imprimir();
            }
            
            Matriz multiplicacao = matriz1.multiplicacao(matriz2);
            if (multiplicacao != null) {
                multiplicacao.imprimir();
            }
        }
        
        scanner.close();
    }
}
