#include <stdio.h>
#include <string.h>
#include <stdbool.h>

bool Fim(char str[]) {
    return (strlen(str) == 3 && str[0] == 'F' && str[1] == 'I' && str[2] == 'M');
}

// Funcao recursiva para inverter a string
void inverte(char str[], int i, int j) {
    if (i < j) {
        char temp = str[i];
        str[i] = str[j];
        str[j] = temp;
        inverte(str, i + 1, j - 1);
    }
}

int main() {
    char str[1000];
    fgets(str, sizeof(str), stdin);
    str[strcspn(str, "\n")] = 0; // Remove o caractere de nova linha
    
    while (!Fim(str)) {
        inverte(str, 0, strlen(str) - 1);
        printf("%s\n", str);
        fgets(str, sizeof(str), stdin);
        str[strcspn(str, "\n")] = 0;
    }
    
    return 0;
}
