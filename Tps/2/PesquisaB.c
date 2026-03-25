#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#define MAX_SHOWS 1400
#define MAX_LINE 1024

typedef struct {
    char show_id[20];
    char type[20];
    char title[100];
    char director[100];
    char cast[300];
    char country[50];
    char date_added[30];
    int release_year;
    char rating[10];
    char duration[20];
    char listed_in[200];
    char description[500];
} Show;

Show shows[MAX_SHOWS];
int show_count = 0;

// Removes leading and trailing spaces
char* trim(char* str) {
    while (*str == ' ' || *str == '\t') str++;
    char* end = str + strlen(str) - 1;
    while (end > str && (*end == '\n' || *end == '\r' || *end == ' ')) *end-- = '\0';
    return str;
}

// Comparison for qsort
int cmp_str(const void* a, const void* b) {
    return strcmp(*(char**)a, *(char**)b);
}

// Sorts a comma preserving original formatting
void ordenar_lista(char* entrada) {
    if (strlen(entrada) == 0) return;

    char* tokens[100];
    int count = 0;

    char* temp = strdup(entrada);
    char* token = strtok(temp, ",");

    while (token && count < 100) {
        tokens[count++] = strdup(trim(token)); // Remove spaces
        token = strtok(NULL, ",");
    }

    // Sort alphabetically
    qsort(tokens, count, sizeof(char*), cmp_str);

    // Rebuild the sorted string
    entrada[0] = '\0';
    for (int i = 0; i < count; i++) {
        strcat(entrada, tokens[i]);
        if (i < count - 1) strcat(entrada, ", ");
        free(tokens[i]);
    }

    free(temp);
}

// Parses a CSV line into a Show struct
void parse_csv_line(char* line, Show* show) {
    char* ptr = line;
    char buffer[500];
    int field = 0, inQuotes = 0, buf_idx = 0;

    for (; *ptr; ptr++) {
        if (*ptr == '"') {
            inQuotes = !inQuotes; // Toggle quotes state
        } else if (*ptr == ',' && !inQuotes) {
            buffer[buf_idx] = '\0';
            // Assign parsed value to the correct field
            switch (field) {
                case 0: strcpy(show->show_id, trim(buffer)); break;
                case 1: strcpy(show->type, trim(buffer)); break;
                case 2: strcpy(show->title, trim(buffer)); break;
                case 3: strcpy(show->director, trim(buffer)); break;
                case 4: strcpy(show->cast, trim(buffer)); break;
                case 5: strcpy(show->country, trim(buffer)); break;
                case 6: strcpy(show->date_added, trim(buffer)); break;
                case 7: show->release_year = atoi(trim(buffer)); break;
                case 8: strcpy(show->rating, trim(buffer)); break;
                case 9: strcpy(show->duration, trim(buffer)); break;
                case 10: strcpy(show->listed_in, trim(buffer)); break;
                case 11: strcpy(show->description, trim(buffer)); break;
            }
            buf_idx = 0;
            field++;
        } else {
            buffer[buf_idx++] = *ptr; // Append character to buffer
        }
    }
    buffer[buf_idx] = '\0';
    if (field == 11) {
        strcpy(show->description, trim(buffer));
    }
}

// Reads the CSV file and populates the shows array
void read_csv(const char* path) {
    FILE* fp = fopen(path, "r");
    if (!fp) {
        printf("Erro ao abrir o arquivo.\n");
        exit(1);
    }

    char line[MAX_LINE];
    fgets(line, sizeof(line), fp); // Skip header

    // Read each line and parse it
    while (fgets(line, sizeof(line), fp)) {
        if (show_count >= MAX_SHOWS) break;
        parse_csv_line(line, &shows[show_count]);
        show_count++;
    }

    fclose(fp);
}

// Prints a Show struct with sorted lists
void print_show(Show* s) {
    char cast_ordenado[300];
    char listed_in_ordenado[200];

    // Copy and sort the cast and listed_in fields
    strcpy(cast_ordenado, s->cast);
    strcpy(listed_in_ordenado, s->listed_in);

    ordenar_lista(cast_ordenado);
    ordenar_lista(listed_in_ordenado);

    printf("=> %s ## %s ## %s ## %s ## [%s] ## %s ## %s ## %d ## %s ## %s ## [%s] ## %s ##\n",
        strlen(s->show_id) > 0 ? s->show_id : "NaN",
        strlen(s->title) > 0 ? s->title : "NaN",
        strlen(s->type) > 0 ? s->type : "NaN",
        strlen(s->director) > 0 ? s->director : "NaN",
        strlen(cast_ordenado) > 0 ? cast_ordenado : "NaN",
        strlen(s->country) > 0 ? s->country : "NaN",
        strlen(s->date_added) > 0 ? s->date_added : "March 1, 1900",
        s->release_year > 0 ? s->release_year : 0,
        strlen(s->rating) > 0 ? s->rating : "NaN",
        strlen(s->duration) > 0 ? s->duration : "NaN",
        strlen(listed_in_ordenado) > 0 ? listed_in_ordenado : "NaN",
        strlen(s->description) > 0 ? s->description : "NaN"
    );
}

// Function to insert a new record into the array
void insert_show(Show new_show) {
    shows[show_count++] = new_show;
}

// Function to compare two shows by title (used for binary search)
int compare_titles(const void* a, const void* b) {
    return strcmp(((Show*)a)->title, ((Show*)b)->title);
}

// Binary search function
int binary_search(const char* title, int* comparisons) {
    int left = 0, right = show_count - 1;
    while (left <= right) {
        (*comparisons)++;
        int mid = (left + right) / 2;
        int cmp = strcmp(title, shows[mid].title);
        if (cmp == 0) return 1; // Found
        else if (cmp < 0) right = mid - 1;
        else left = mid + 1;
    }
    return 0; // Not found
}

// Function to log execution details
void log_execution(const char* matricula, double execution_time, int comparisons) {
    FILE* log_file = fopen("853765_binaria.txt", "w");
    if (log_file) {
        fprintf(log_file, "%s\t%.6f\t%d\n", matricula, execution_time, comparisons);
        fclose(log_file);
    }
}

int main() {
    read_csv("/tmp/disneyplus.csv");

    char input[500];
    char ids[1000][20];
    int id_count = 0;

    // Read IDs
    while (1) {
        fgets(input, sizeof(input), stdin);
        char* newline = strchr(input, '\n');
        if (newline) *newline = '\0';

        if (strcmp(input, "FIM") == 0) break;

        strcpy(ids[id_count++], input);
    }

    // Collect titles corresponding to the IDs
    char titles_from_ids[1000][100];
    int title_count = 0;

    for (int i = 0; i < id_count; i++) {
        for (int j = 0; j < show_count; j++) {
            if (strcmp(ids[i], shows[j].show_id) == 0) {
                strcpy(titles_from_ids[title_count++], shows[j].title);
                break;
            }
        }
    }

    // Read titles to check
    char titles_to_check[1000][100];
    int check_count = 0;

    while (1) {
        fgets(input, sizeof(input), stdin);
        char* newline = strchr(input, '\n');
        if (newline) *newline = '\0';

        if (strcmp(input, "FIM") == 0) break;

        strcpy(titles_to_check[check_count++], input);
    }

    // Search for each title in the list of titles from IDs
    int comparisons = 0;
    clock_t start = clock();

    for (int i = 0; i < check_count; i++) {
        int found = 0;
        for (int j = 0; j < title_count; j++) {
            comparisons++;
            if (strcmp(titles_to_check[i], titles_from_ids[j]) == 0) {
                found = 1;
                break;
            }
        }
        printf("%s\n", found ? "SIM" : "NAO");
    }

    clock_t end = clock();
    double execution_time = (double)(end - start) / CLOCKS_PER_SEC;

    // Log execution details
    log_execution("123456789", execution_time, comparisons);

    return 0;
}
