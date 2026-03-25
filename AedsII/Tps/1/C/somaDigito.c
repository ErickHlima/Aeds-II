#include <stdio.h>
#include <string.h>
#include <stdlib.h>


int Fim(char str[]) {
    return (strlen(str) == 3 && str[0] == 'F' && str[1] == 'I' && str[2] == 'M');
}

// Função recursiva para somar os dígitos de um número
int somaDigitos(int n) {
    if (n == 0) {
        return 0;
    }
    return (n % 10) + somaDigitos(n / 10);
}

int main() {
    char str[100];
    int fim = 0;

    do {
        fgets(str, sizeof(str), stdin);
        
        // Remove o '\n'
        str[strcspn(str, "\n")] = 0;
        
        fim = Fim(str);
        
        if (!fim) {
            int n = atoi(str);
            printf("%d\n", somaDigitos(n));
        }
    } while (!fim);
    
    return 0;
}
