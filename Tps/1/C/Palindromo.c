#include <stdio.h>
#include <stdbool.h>
#include <string.h>

int tamanho(char str[])
{
    int cont = 0;
    while (str[cont] != '\0')
    {
        cont++;
    }
    return cont;
}

bool ehPalindromo(char str[])
{
    int n = tamanho(str);
    int i = 0;
    int j = n - 1;
    bool resp = true;
    while (i < j)
    {
        if (str[i] != str[j])
        {
            resp = false;
            i = j;
        }
        i++;
        j--;
    }
    return resp;
}

int main()
{
    char s1[] = "FIM";
    char str[1000];

    scanf(" %[^\n]", str);

    while (strcmp(str, s1) != 0)
    {
        if (ehPalindromo(str))
            printf("SIM\n");
        else
            printf("NAO\n");

        scanf(" %[^\n]", str);
    }

    return 0;
}
