#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main()
{
    int n;
    scanf("%d", &n);

    FILE *arq;
    double valor;

    arq = fopen("valores.bin", "wb");
    if (arq == NULL)
    {
        printf("Erro ao abrir o arquivo para escrita!\n");
        return 1;
    }

    for (int i = 0; i < n; i++)
    {
        scanf("%lf", &valor);
        fwrite(&valor, sizeof(double), 1, arq);
    }
    fclose(arq);

    arq = fopen("valores.bin", "rb");
    if (arq == NULL)
    {
        printf("Erro ao abrir o arquivo para leitura!\n");
        return 1;
    }

    for (int i = n - 1; i >= 0; i--)
    {
        fseek(arq, i * sizeof(double), SEEK_SET);
        fread(&valor, sizeof(double), 1, arq);

        // Verifica se o numero e inteiro
        if (valor == (long long)valor)
        {
            printf("%lld\n", (long long)valor); // Imprime sem .0
        }
        else
        {
            printf("%g\n", valor);
        }
    }
    fclose(arq);

    return 0;
}
