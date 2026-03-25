import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;

public class pesquisasequencial {

    public static final String FILE_PATH = "/tmp/disneyplus.csv";
    public ArrayList<Show> shows = new ArrayList<Show>();
    private int comparisons = 0; // Counter for comparisons

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

        // Parameterized constructor
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

        public String getShowId() {
            return show_id;
        }

        public String getTitle() {
            return title;
        }
    }

    public void read() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line = br.readLine(); // Skip header
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
                Date date_added = dateStr.isEmpty() ? new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse("March 1, 1900") : new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(dateStr);
                int release_year = fields[7].isEmpty() ? 0 : Integer.parseInt(fields[7]);
                String rating = fields[8].isEmpty() ? "NaN" : fields[8];
                String duration = fields[9].isEmpty() ? "NaN" : fields[9];
                String[] listed_in = fields[10].isEmpty() ? new String[]{"NaN"} : fields[10].split(", ");
                Arrays.sort(listed_in);

                shows.add(new Show(show_id, type, title, director, cast, country, date_added, release_year, rating, duration, listed_in));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public boolean searchByTitle(String title, List<String> titles) {
        for (String storedTitle : titles) {
            comparisons++;
            if (storedTitle.equals(title)) {
                return true;
            }
        }
        return false;
    }

    public void logExecution(String matricula, long executionTime) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(matricula + "_sequencial.txt"))) {
            writer.write(matricula + "\t" + executionTime + "\t" + comparisons);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        pesquisasequencial pesq = new pesquisasequencial();
        pesq.read();

        Scanner scanner = new Scanner(System.in);
        List<String> titlesFromIds = new ArrayList<>();
        List<String> titlesToCheck = new ArrayList<>();

        // Read IDs and store corresponding titles
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("FIM")) break;
            for (Show show : pesq.shows) {
                if (show.getShowId().equals(input)) {
                    titlesFromIds.add(show.getTitle());
                    break;
                }
            }
        }

        // Read titles to check
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("FIM")) break;
            titlesToCheck.add(input);
        }

        long startTime = System.currentTimeMillis();

        // Search for each title in the list of titles from IDs
        for (String title : titlesToCheck) {
            if (pesq.searchByTitle(title, titlesFromIds)) {
                System.out.println("SIM");
            } else {
                System.out.println("NAO");
            }
        }

        long endTime = System.currentTimeMillis();
        pesq.logExecution("853765", endTime - startTime);

        scanner.close();
    }
}
