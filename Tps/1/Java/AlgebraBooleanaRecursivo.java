import java.util.Scanner;

public class AlgebraBooleanaRecursivo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean ativo = true;

        while (ativo && sc.hasNext()) {
            String linha = sc.nextLine();

            ativo = linha.charAt(0) != '0';

            if (ativo) {
                if (avaliarExpressaoCompleta(linha)) {
                    System.out.println("1");
                } else {
                    System.out.println("0");
                }
            }
        }

        sc.close();
    }

    // Inicia a avaliação da expressão booleana e extrai os valores das variáveis
    public static boolean avaliarExpressaoCompleta(String linha) {
        int[] pos = {0}; // posição na string
        boolean[] vars = lerValoresVariaveis(linha, pos);
        return avaliarExpressao(linha, pos, vars);
    }

    // Extrai os valores booleanos das variáveis da entrada
    public static boolean[] lerValoresVariaveis(String linha, int[] pos) {
        int qtdVars = 0;

        // Lê a quantidade de variáveis
        while (linha.charAt(pos[0]) != ' ') {
            qtdVars = qtdVars * 10 + (linha.charAt(pos[0]) - '0');
            pos[0]++;
        }
        pos[0]++; // Pula o espaço

        boolean[] vars = new boolean[qtdVars];

        // Lê os valores das variáveis
        for (int j = 0; j < qtdVars; j++) {
            vars[j] = linha.charAt(pos[0]) == '1';
            pos[0] += 2; // Pula número e espaço
        }

        return vars;
    }

    public static boolean avaliarExpressao(String linha, int[] pos, boolean[] vars) {
        // Ignora espaços e vírgulas
        while (pos[0] < linha.length() && (linha.charAt(pos[0]) == ' ' || linha.charAt(pos[0]) == ',')) {
            pos[0]++;
        }

        // Se encontrar parêntese abrindo
        if (pos[0] < linha.length() && linha.charAt(pos[0]) == '(') {
            pos[0]++;
        }

        if (pos[0] >= linha.length()) return false;

        char atual = linha.charAt(pos[0]);

        // Se for variável (A, B, C, ...)
        if (atual >= 'A' && atual < 'A' + vars.length) {
            return vars[linha.charAt(pos[0]++) - 'A'];
        }

        // Se for NOT
        if (linha.startsWith("not", pos[0])) {
            pos[0] += 3;
            boolean resultado = !avaliarExpressao(linha, pos, vars);

            // Pula parêntese fechando, se houver
            if (pos[0] < linha.length() && linha.charAt(pos[0]) == ')') {
                pos[0]++;
            }

            return resultado;
        }

        // Se for AND
        if (linha.startsWith("and", pos[0])) {
            pos[0] += 3;
            boolean resultado = true;

            // Processa todos os operandos
            while (pos[0] < linha.length() && linha.charAt(pos[0]) != ')') {
                resultado &= avaliarExpressao(linha, pos, vars);
            }

            // Pula parêntese fechando
            if (pos[0] < linha.length() && linha.charAt(pos[0]) == ')') {
                pos[0]++;
            }

            return resultado;
        }

        // Se for OR
        if (linha.startsWith("or", pos[0])) {
            pos[0] += 2;
            boolean resultado = false;

            // Processa todos os operandos
            while (pos[0] < linha.length() && linha.charAt(pos[0]) != ')') {
                resultado |= avaliarExpressao(linha, pos, vars);
            }

            // Pula parêntese fechando
            if (pos[0] < linha.length() && linha.charAt(pos[0]) == ')') {
                pos[0]++;
            }

            return resultado;
        }

        return false; // Caso não reconheça nada
    }
}
