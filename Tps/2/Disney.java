import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.io.FileWriter;

public class Disney {

    public static final String FILE_PATH = "/tmp/disneyplus.csv";

    private NodeDuplo primeiro, ultimo;
    private int tamanho = 0;

    private static long comparacoes = 0;
    private static long movimentacoes = 0;
    private static long inicioTempo = 0;

    private class NodeDuplo {
        Show elemento;
        NodeDuplo ant, prox;
        NodeDuplo(Show elemento) {
            this.elemento = elemento;
            this.ant = null;
            this.prox = null;
        }
    }

    public Disney() {
        primeiro = new NodeDuplo(null);
        ultimo = primeiro;
        tamanho = 0;
    }

    public void inserirFim(Show show) {
        NodeDuplo novo = new NodeDuplo(show);
        ultimo.prox = novo;
        novo.ant = ultimo;
        ultimo = novo;
        tamanho++;
    }

    private NodeDuplo getNode(int idx) {
        NodeDuplo atual = primeiro.prox;
        for (int i = 0; i < idx && atual != null; i++) {
            atual = atual.prox;
        }
        return atual;
    }

    private void swap(NodeDuplo a, NodeDuplo b) {
        Show tmp = a.elemento;
        a.elemento = b.elemento;
        b.elemento = tmp;
        movimentacoes += 3;
    }

    public void quicksort() {
        inicioTempo = System.currentTimeMillis();
        quicksort(0, tamanho - 1);
        long tempo = System.currentTimeMillis() - inicioTempo;
        gerarLog(tempo);
    }

    private void quicksort(int esq, int dir) {
        if (esq < dir) {
            int p = partition(esq, dir);
            quicksort(esq, p - 1);
            quicksort(p + 1, dir);
        }
    }

    private int partition(int esq, int dir) {
        NodeDuplo pivotNode = getNode(dir);
        Show pivot = pivotNode.elemento;
        int i = esq - 1;
        for (int j = esq; j < dir; j++) {
            NodeDuplo atual = getNode(j);
            int cmp = compare(atual.elemento, pivot);
            comparacoes++;
            if (cmp < 0) {
                i++;
                swap(getNode(i), atual);
            }
        }
        swap(getNode(i + 1), pivotNode);
        return i + 1;
    }

    private int compare(Show a, Show b) {
        int cmp = a.getDate_added().compareTo(b.getDate_added());
        if (cmp == 0) {
            cmp = a.getTitle().compareTo(b.getTitle());
        }
        return cmp;
    }

    public void mostrar() {
        NodeDuplo atual = primeiro.prox;
        int idx = 0;
        while (atual != null) {
            print(atual.elemento, idx++);
            atual = atual.prox;
        }
    }

    public void print(Show show, int idx) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        System.out.print("=> ");
        System.out.print(show.getShow_id() + " ## ");
        System.out.print(show.getTitle() + " ## ");
        System.out.print(show.getType() + " ## ");
        System.out.print(show.getDirector() + " ## ");
        System.out.print(Arrays.toString(show.getCast()) + " ## ");
        System.out.print(show.getCountry() + " ## ");
        System.out.print(dateFormat.format(show.getDate_added()) + " ## ");
        System.out.print(show.getRelease_year() + " ## ");
        System.out.print(show.getRating() + " ## ");
        System.out.print(show.getDuration() + " ## ");
        System.out.print(Arrays.toString(show.getListed_in()));
        System.out.println(" ##");
    }

    private void gerarLog(long tempo) {
        String matricula = "853765";
        try {
            FileWriter fw = new FileWriter("853765_quicksort.txt");
            fw.write(matricula + "\t" + comparacoes + "\t" + movimentacoes + "\t" + tempo + "\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("Erro ao escrever log: " + e.getMessage());
        }
    }

    public class Show {
        private String show_id;
        private String type;
        private String title;
        private String director;
        private String[] cast;
        private String country;
        private Date date_added;
        private int release_year;
        private String rating;
        private String duration;
        private String[] listed_in;

        public Show() {}

        public Show(String show_id, String type, String title, String director, String[] cast, String country, Date date_added, int release_year, String rating, String duration, String[] listed_in) {
            this.show_id = show_id;
            this.type = type;
            this.title = title;
            this.director = director;
            this.cast = cast;
            this.country = country;
            this.date_added = date_added;
            this.release_year = release_year;
            this.rating = rating;
            this.duration = duration;
            this.listed_in = listed_in;
        }

        public Show clone() {
            return new Show(
                show_id,
                type,
                title,
                director,
                cast != null ? cast.clone() : null,
                country,
                date_added != null ? new Date(date_added.getTime()) : null,
                release_year,
                rating,
                duration,
                listed_in != null ? listed_in.clone() : null
            );
        }

        public String getShow_id() { return show_id; }
        public String getType() { return type; }
        public String getTitle() { return title; }
        public String getDirector() { return director; }
        public String[] getCast() { return cast; }
        public String getCountry() { return country; }
        public Date getDate_added() { return date_added; }
        public int getRelease_year() { return release_year; }
        public String getRating() { return rating; }
        public String getDuration() { return duration; }
        public String[] getListed_in() { return listed_in; }
    }

    public static Map<String, Show> readAllShows() {
        Map<String, Show> map = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line = br.readLine(); // header
            while ((line = br.readLine()) != null) {
                String[] fields = parseCSVLine(line);
                String show_id = fields[0].isEmpty() ? "NaN" : fields[0];
                String type = fields[1].isEmpty() ? "NaN" : fields[1];
                String title = fields[2].isEmpty() ? "NaN" : fields[2];
                String director = fields[3].isEmpty() ? "NaN" : fields[3];
                String[] cast = fields[4].isEmpty() ? new String[]{"NaN"} : fields[4].split(", ");
                Arrays.sort(cast);
                String country = fields[5].isEmpty() ? "NaN" : fields[5];
                String dateStr = fields[6].trim();
                Date date_added = null;
                try {
                    date_added = dateStr.isEmpty() ? new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse("March 1, 1900") : new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(dateStr);
                } catch (Exception e) {
                    date_added = new Date();
                }
                int release_year = fields[7].isEmpty() ? 0 : Integer.parseInt(fields[7]);
                String rating = fields[8].isEmpty() ? "NaN" : fields[8];
                String duration = fields[9].isEmpty() ? "NaN" : fields[9];
                String[] listed_in = fields[10].isEmpty() ? new String[]{"NaN"} : fields[10].split(", ");
                Show show = new Disney().new Show(show_id, type, title, director, cast, country, date_added, release_year, rating, duration, listed_in);
                map.put(show_id, show);
            }
        } catch (Exception e) {}
        return map;
    }

    public static String[] parseCSVLine(String line) {
        List<String> tokens = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;
        for (char c : line.toCharArray()) {
            if (c == '\"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                tokens.add(current.toString().replaceAll("^\"|\"$", ""));
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }
        tokens.add(current.toString().replaceAll("^\"|\"$", ""));
        return tokens.toArray(new String[0]);
    }

    public static void main(String[] args) throws Exception {
        Map<String, Show> allShows = readAllShows();
        Disney lista = new Disney();
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            if (input.equals("FIM")) break;
            Show show = allShows.get(input);
            if (show != null) lista.inserirFim(show.clone());
        }

        if (sc.hasNextLine()) {
            String nStr = sc.nextLine();
            if (!nStr.isEmpty()) {
                int n = Integer.parseInt(nStr);
                for (int i = 0; i < n; i++) {
                    if (!sc.hasNextLine()) break;
                    String linha = sc.nextLine();
                    String[] parts = linha.split(" ");
                    String cmd = parts[0];

                    if (cmd.equals("I")) {
                        Show show = allShows.get(parts[1]);
                        if (show != null) lista.inserirFim(show.clone());
                    }
                }
            }
        }

        lista.quicksort();
        lista.mostrar();
        sc.close();
    }
}
