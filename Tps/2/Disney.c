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
} Show;

// Array with all shows loaded from CSV
Show shows[MAX_SHOWS];
int show_count = 0;

// Removes leading and trailing spaces
char* trim(char* str) {
    while (*str == ' ' || *str == '\t') str++;
    char* end = str + strlen(str) - 1;
    while (end > str && (*end == '\n' || *end == '\r' || *end == ' ')) *end-- = '\0';
    return str;
}

// Parses a CSV line into a Show struct
void parse_csv_line(char* line, Show* show) {
    char* ptr = line;
    char buffer[500];
    int field = 0, inQuotes = 0, buf_idx = 0;

    for (; *ptr; ptr++) {
        if (*ptr == '"') {
            inQuotes = !inQuotes;
        } else if (*ptr == ',' && !inQuotes) {
            buffer[buf_idx] = '\0';
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
            }
            buf_idx = 0;
            field++;
        } else {
            buffer[buf_idx++] = *ptr;
        }
    }

    buffer[buf_idx] = '\0';
    if (field == 10) {
        strcpy(show->listed_in, trim(buffer));
    }
}


// Reads the CSV file and populates the shows array
void read_csv(const char* path) {
    FILE* fp = fopen(path, "r");
    if (!fp) {
        printf("Error\n");
        exit(1);
    }

    char line[MAX_LINE];
    fgets(line, sizeof(line), fp); // Skip header

    while (fgets(line, sizeof(line), fp)) {
        if (show_count >= MAX_SHOWS) break;
        parse_csv_line(line, &shows[show_count]);
        show_count++;
    }

    fclose(fp);
}

// Find a show by show_id in the shows array
Show* find_show_by_id(const char* id) {
    for (int i = 0; i < show_count; i++) {
        if (strcmp(shows[i].show_id, id) == 0) {
            return &shows[i];
        }
    }
    return NULL;
}

typedef struct Node {
    Show* show;
    struct Node* next;
} Node;

Node* head = NULL;
Node* tail = NULL;
int tamanho_lista = 0;

void inserirInicio(Show* show) {
    Node* novo = (Node*)malloc(sizeof(Node));
    novo->show = show;
    novo->next = head;
    head = novo;
    if (tamanho_lista == 0) tail = novo;
    tamanho_lista++;
}

void inserirFim(Show* show) {
    Node* novo = (Node*)malloc(sizeof(Node));
    novo->show = show;
    novo->next = NULL;
    if (tamanho_lista == 0) {
        head = tail = novo;
    } else {
        tail->next = novo;
        tail = novo;
    }
    tamanho_lista++;
}

void inserir(Show* show, int pos) {
    if (pos < 0 || pos > tamanho_lista) return;
    if (pos == 0) {
        inserirInicio(show);
    } else if (pos == tamanho_lista) {
        inserirFim(show);
    } else {
        Node* novo = (Node*)malloc(sizeof(Node));
        novo->show = show;
        Node* ant = head;
        for (int i = 0; i < pos - 1; i++) ant = ant->next;
        novo->next = ant->next;
        ant->next = novo;
        tamanho_lista++;
    }
}

Show* removerInicio() {
    if (tamanho_lista == 0) return NULL;
    Node* temp = head;
    Show* show = temp->show;
    head = head->next;
    free(temp);
    tamanho_lista--;
    if (tamanho_lista == 0) tail = NULL;
    return show;
}

Show* removerFim() {
    if (tamanho_lista == 0) return NULL;
    if (tamanho_lista == 1) {
        Show* show = head->show;
        free(head);
        head = tail = NULL;
        tamanho_lista = 0;
        return show;
    }
    Node* ant = head;
    while (ant->next != tail) ant = ant->next;
    Show* show = tail->show;
    free(tail);
    tail = ant;
    tail->next = NULL;
    tamanho_lista--;
    return show;
}

Show* remover(int pos) {
    if (tamanho_lista == 0 || pos < 0 || pos >= tamanho_lista) return NULL;
    if (pos == 0) return removerInicio();
    if (pos == tamanho_lista - 1) return removerFim();
    Node* ant = head;
    for (int i = 0; i < pos - 1; i++) ant = ant->next;
    Node* temp = ant->next;
    Show* show = temp->show;
    ant->next = temp->next;
    free(temp);
    tamanho_lista--;
    return show;
}

int cmp_cast(const void* a, const void* b) {
    return strcmp(*(const char**)a, *(const char**)b);
}

void print_show(Show* s) {
    char cast_sorted[300];
    strcpy(cast_sorted, s->cast);

    char* tokens[100];
    int count = 0;
    char* temp = strdup(cast_sorted);
    char* token = strtok(temp, ",");
    while (token && count < 100) {
        tokens[count] = trim(token);
        token = strtok(NULL, ",");
        count++;
    }
    if (count > 1) {
        qsort(tokens, count, sizeof(char*), cmp_cast);
    }
    cast_sorted[0] = '\0';
    for (int i = 0; i < count; i++) {
        strcat(cast_sorted, tokens[i]);
        if (i < count - 1) strcat(cast_sorted, ", ");
    }
    free(temp);

    char listed_in_copy[200];
    strcpy(listed_in_copy, s->listed_in);
    char* listed_tokens[50];
    int listed_count = 0;
    char* listed_temp = strtok(listed_in_copy, ",");
    while (listed_temp && listed_count < 50) {
        listed_tokens[listed_count++] = trim(listed_temp);
        listed_temp = strtok(NULL, ",");
    }

    printf("=> %s ## %s ## %s ## %s ## [%s] ## %s ## %s ## %d ## %s ## %s ## [",
        strlen(s->show_id) > 0 ? s->show_id : "NaN",
        strlen(s->title) > 0 ? s->title : "NaN",
        strlen(s->type) > 0 ? s->type : "NaN",
        strlen(s->director) > 0 ? s->director : "NaN",
        strlen(cast_sorted) > 0 ? cast_sorted : "NaN",
        strlen(s->country) > 0 ? s->country : "NaN",
        strlen(s->date_added) > 0 ? s->date_added : "March 1, 1900",
        s->release_year > 0 ? s->release_year : 0,
        strlen(s->rating) > 0 ? s->rating : "NaN",
        strlen(s->duration) > 0 ? s->duration : "NaN"
    );
    for (int i = 0; i < listed_count; i++) {
        printf("%s", listed_tokens[i]);
        if (i < listed_count - 1) printf(", ");
    }
    printf("] ##\n");
}

typedef struct QueueNode {
    Show* show;
    struct QueueNode* next;
} QueueNode;

QueueNode* frente = NULL;
QueueNode* tras = NULL;
int tamanho_fila = 0;
const int MAX_FILA = 5;

void fila_inserir(Show* show) {
    if (tamanho_fila == MAX_FILA) {
        QueueNode* temp = frente;
        frente = frente->next;
        free(temp);
        tamanho_fila--;
    }
    QueueNode* novo = (QueueNode*)malloc(sizeof(QueueNode));
    novo->show = show;
    novo->next = NULL;
    if (tamanho_fila == 0) {
        frente = tras = novo;
    } else {
        tras->next = novo;
        tras = novo;
    }
    tamanho_fila++;

    int soma = 0, count = 0;
    QueueNode* curr = frente;
    while (curr) {
        soma += curr->show->release_year;
        curr = curr->next;
        count++;
    }
    int media = (int)((soma / (double)tamanho_fila) + 0.5);
    printf("[Media] %d\n", media);
}

Show* fila_remover() {
    if (tamanho_fila == 0) return NULL;
    QueueNode* temp = frente;
    Show* show = temp->show;
    printf("(R) %s\n", show->title);
    frente = frente->next;
    free(temp);
    tamanho_fila--;
    if (tamanho_fila == 0) tras = NULL;
    return show;
}

void mostrar_fila() {
    QueueNode* curr = frente;
    int idx = 0;
    while (curr) {
        printf("[%d] ", idx++);
        print_show(curr->show);
        curr = curr->next;
    }
}

void mostrar_lista() {
    Node* curr = head;
    while (curr) {
        print_show(curr->show);
        curr = curr->next;
    }
}

typedef struct DNode {
    Show* show;
    struct DNode* prev;
    struct DNode* next;
} DNode;

DNode* dhead = NULL;
DNode* dtail = NULL;
int tamanho_dlista = 0;

void dinserirFim(Show* show) {
    DNode* novo = (DNode*)malloc(sizeof(DNode));
    novo->show = show;
    novo->next = NULL;
    novo->prev = dtail;
    if (dtail) dtail->next = novo;
    dtail = novo;
    if (!dhead) dhead = novo;
    tamanho_dlista++;
}

void dliberar() {
    DNode* curr = dhead;
    while (curr) {
        DNode* temp = curr;
        curr = curr->next;
        free(temp);
    }
    dhead = dtail = NULL;
    tamanho_dlista = 0;
}

int date_to_int(const char* date) {
    int day = 0, year = 0;
    char month_str[20];
    int month = 0;
    if (sscanf(date, "%s %d, %d", month_str, &day, &year) != 3) return 0;
    if (strcmp(month_str, "January") == 0) month = 1;
    else if (strcmp(month_str, "February") == 0) month = 2;
    else if (strcmp(month_str, "March") == 0) month = 3;
    else if (strcmp(month_str, "April") == 0) month = 4;
    else if (strcmp(month_str, "May") == 0) month = 5;
    else if (strcmp(month_str, "June") == 0) month = 6;
    else if (strcmp(month_str, "July") == 0) month = 7;
    else if (strcmp(month_str, "August") == 0) month = 8;
    else if (strcmp(month_str, "September") == 0) month = 9;
    else if (strcmp(month_str, "October") == 0) month = 10;
    else if (strcmp(month_str, "November") == 0) month = 11;
    else if (strcmp(month_str, "December") == 0) month = 12;
    else return 0;
    return year * 10000 + month * 100 + day;
}

int cmp_show(Show* a, Show* b) {
    int da = date_to_int(a->date_added);
    int db = date_to_int(b->date_added);
    int cmp = da - db;
    if (cmp == 0) {
        return strcmp(a->title, b->title);
    }
    return cmp;
}

DNode* dget_node(int idx) {
    DNode* curr = dhead;
    for (int i = 0; i < idx && curr; i++) curr = curr->next;
    return curr;
}

void dswap(DNode* a, DNode* b) {
    Show* tmp = a->show;
    a->show = b->show;
    b->show = tmp;
}

int dpartition(int left, int right) {
    DNode* pnode = dget_node(right);
    Show* pivot = pnode->show;
    int i = left - 1;
    for (int j = left; j < right; j++) {
        DNode* jnode = dget_node(j);
        if (cmp_show(jnode->show, pivot) < 0) {
            i++;
            dswap(dget_node(i), jnode);
        }
    }
    dswap(dget_node(i + 1), pnode);
    return i + 1;
}

void dquicksort(int left, int right) {
    if (left < right) {
        int pi = dpartition(left, right);
        dquicksort(left, pi - 1);
        dquicksort(pi + 1, right);
    }
}

void dmostrar_lista() {
    DNode* curr = dhead;
    while (curr) {
        print_show(curr->show);
        curr = curr->next;
    }
}

// Função para log (tempo e comparações)
void log_quicksort2(int comps, int movs, double tempo) {
    FILE* f = fopen("853765_quicksort2.txt", "w");
    fprintf(f, "853765_quicksort2\t%d\t%d\t%lf\n", comps, movs, tempo);
    fclose(f);
}

int main() {
    read_csv("/tmp/disneyplus.csv");

    char input[100];
    while (1) {
        if (!fgets(input, sizeof(input), stdin)) break;
        char* newline = strchr(input, '\n');
        if (newline) *newline = '\0';
        if (strcmp(input, "FIM") == 0) break;
        Show* show = find_show_by_id(input);
        if (show) dinserirFim(show);
    }

    int comps = 0, movs = 0;
    clock_t start = clock();
    dquicksort(0, tamanho_dlista - 1);
    clock_t end = clock();
    double tempo = ((double)(end - start)) / CLOCKS_PER_SEC;

    dmostrar_lista();

    log_quicksort2(comps, movs, tempo);

    dliberar();
    return 0;
}
