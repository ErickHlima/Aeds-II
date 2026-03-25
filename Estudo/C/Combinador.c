#include <stdio.h>
#include <string.h>

int main()
{
    char s1[100], s2[100], result[200];

    while (scanf("%99s %99s", s1, s2) != EOF) {
        int len1 = strlen(s1), len2 = strlen(s2);
        int i = 0, j = 0, k = 0;

        while (i < len1 && i < len2) {
            result[k++] = s1[i];
            result[k++] = s2[i];
            i++;
        }

        while (i < len1) {
            result[k++] = s1[i++];
        }
        while (i < len2) {
            result[k++] = s2[i++];
        }

        result[k] = '\0';

        printf("%s\n", result);
    }

    return 0;
}
