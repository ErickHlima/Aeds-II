import java.util.Scanner;

class Jogador {
    int peso;
    int altura;

    public Jogador(int peso, int altura) {
        this.peso = peso;
        this.altura = altura;
    } 

    public void print() {
        System.out.print("peso= " + peso + " altura= " + altura);
    }
}

class Node {
    Jogador jogador;
    Node next;

    public Node(Jogador jogador) {
        this.jogador = jogador;
        this.next = null;
    }
}

class ListaJogador {
    private Node head;
    private int tamanho;

    public ListaJogador() {
        this.head = null;
        this.tamanho = 0;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void inserirInicio(Jogador jogador) {
        Node novo = new Node(jogador);
        novo.next = head;
        head = novo;
        tamanho++;
    }

    public void inserirFinal(Jogador jogador) {
        Node novo = new Node(jogador);
        if (isEmpty()) {
            head = novo;
        } else {
            Node atual = head;
            while (atual.next != null) {
                atual = atual.next;
            }
            atual.next = novo;
        }
        tamanho++;
    }

    public void inserirEmPosicao(Jogador jogador, int posicao) {
        if (posicao < 0 || posicao > tamanho) {
            System.out.println("Posição inválida.");
            return;
        }
        if (posicao == 0) {
            inserirInicio(jogador);
        } else if (posicao == tamanho) {
            inserirFinal(jogador);
        } else {
            Node novo = new Node(jogador);
            Node atual = head;
            for (int i = 0; i < posicao - 1; i++) {
                atual = atual.next;
            }
            novo.next = atual.next;
            atual.next = novo;
            tamanho++;
        }
    }

    public Jogador removerEmPosicao(int posicao) {
        if (isEmpty() || posicao < 0 || posicao >= tamanho) {
            System.out.println("Posição inválida ou lista vazia.");
            return null;
        }
        if (posicao == 0) {
            Jogador removido = head.jogador;
            head = head.next;
            tamanho--;
            return removido;
        }
        Node atual = head;
        for (int i = 0; i < posicao - 1; i++) {
            atual = atual.next;
        }
        Jogador removido = atual.next.jogador;
        atual.next = atual.next.next;
        tamanho--;
        return removido;
    }

    public void printLista() {
        Node atual = head;
        System.out.print("[");
        while (atual != null) {
            atual.jogador.print();
            if (atual.next != null) System.out.print(", ");
            atual = atual.next;
        }
        System.out.println("]");
    }
}

public class teste {
    public static void main(String[] args) {
        ListaJogador lista = new ListaJogador();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Digite 1 para inserir no início, 2 para inserir no final,");
            System.out.println("3 para inserir em posição específica, 4 para remover de posição específica, 0 para sair:");
            int opcao = sc.nextInt();

            if (opcao == 1) {
                System.out.print("Digite o peso do jogador (kg): ");
                int peso = sc.nextInt();
                System.out.print("Digite a altura do jogador (cm): ");
                int altura = sc.nextInt();
                Jogador jogador = new Jogador(peso, altura);
                lista.inserirInicio(jogador);
                System.out.print("Lista: ");
                lista.printLista();
            } else if (opcao == 2) {
                System.out.print("Digite o peso do jogador (kg): ");
                int peso = sc.nextInt();
                System.out.print("Digite a altura do jogador (cm): ");
                int altura = sc.nextInt();
                Jogador jogador = new Jogador(peso, altura);
                lista.inserirFinal(jogador);
                System.out.print("Lista: ");
                lista.printLista();
            } else if (opcao == 3) {
                System.out.print("Digite o peso do jogador (kg): ");
                int peso = sc.nextInt();
                System.out.print("Digite a altura do jogador (cm): ");
                int altura = sc.nextInt();
                System.out.print("Digite a posição para inserir: ");
                int posicao = sc.nextInt();
                Jogador jogador = new Jogador(peso, altura);
                lista.inserirEmPosicao(jogador, posicao);
                System.out.print("Lista: ");
                lista.printLista();
            } else if (opcao == 4) {
                System.out.print("Digite a posição para remover: ");
                int posicao = sc.nextInt();
                Jogador removido = lista.removerEmPosicao(posicao);
                if (removido != null) {
                    System.out.print("Removido: ");
                    removido.print();
                    System.out.println();
                }
                System.out.print("Lista: ");
                lista.printLista();
            } else if (opcao == 0) {
                break;
            } else {
                System.out.println("Opção inválida.");
            }
        }
        sc.close();
    }
}
