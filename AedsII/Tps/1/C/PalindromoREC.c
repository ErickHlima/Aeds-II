#include <stdio.h>
#include <string.h>
#include <stdbool.h>

bool Fim(const char *str) {
    return strlen(str) == 3 && str[0] == 'F' && str[1] == 'I' && str[2] == 'M';
}

bool ehPalindromo(const char *str, int i, int j) {
    return (i >= j) || (str[i] == str[j] && ehPalindromo(str, i + 1, j - 1));
}

int main() {
    char str[1000];
    while (fgets(str, sizeof(str), stdin)) {
        str[strcspn(str, "\n")] = 0;
        if (!Fim(str)) {
            printf("%s\n", ehPalindromo(str, 0, strlen(str) - 1) ? "SIM" : "NAO");
        }
    }
    return 0;
}

