import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Html {

    public static boolean isFim(String s){
        return s.length() == 3 && s.equals("FIM");
    }

    public static int contarChar(String s, char c){
        int cont = 0;
        for(int i = 0; i < s.length(); i++){
            if(Character.toLowerCase(s.charAt(i)) == c)
                cont++;
        }
        return cont;
    }

    public static int contarTag(String html, String tag) {
        String regex = "<\\s*" + tag + "\\s*[^>]*>";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(html);

        int cont = 0;
        while (matcher.find()) {
            cont++;
        }
        return cont;
    }

    public static int contarConsoantes(String s) {
        int cont = 0;
        s = s.toLowerCase();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 'a' && c <= 'z' && "aeiou".indexOf(c) == -1)
                cont++;
        }
        return cont;
    }
    
    public static String baixar(String url) {
        try{
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
    
            HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
            return resp.body();
        }catch(Exception e){
            return "";
        }
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String nome, url, html, resp;
        do{
            nome = sc.nextLine();
            if(!isFim(nome)){
                url = sc.nextLine();
                html = baixar(url);
                resp = "a(" + contarChar(html, 'a') + ") ";
                resp += "e(" + contarChar(html, 'e') + ") ";
                resp += "i(" + contarChar(html, 'i') + ") ";
                resp += "o(" + contarChar(html, 'o') + ") ";
                resp += "u(" + contarChar(html, 'u') + ") ";
                resp += "á(" + contarChar(html, 'á') + ") ";
                resp += "é(" + contarChar(html, 'é') + ") ";
                resp += "í(" + contarChar(html, 'í') + ") ";
                resp += "ó(" + contarChar(html, 'ó') + ") ";
                resp += "ú(" + contarChar(html, 'ú') + ") ";
                resp += "à(" + contarChar(html, 'à') + ") ";
                resp += "è(" + contarChar(html, 'è') + ") ";
                resp += "ì(" + contarChar(html, 'ì') + ") ";
                resp += "ò(" + contarChar(html, 'ò') + ") ";
                resp += "ù(" + contarChar(html, 'ù') + ") ";
                resp += "ã(" + contarChar(html, 'ã') + ") ";
                resp += "õ(" + contarChar(html, 'õ') + ") ";
                resp += "â(" + contarChar(html, 'â') + ") ";
                resp += "ê(" + contarChar(html, 'ê') + ") ";
                resp += "î(" + contarChar(html, 'î') + ") ";
                resp += "ô(" + contarChar(html, 'ô') + ") ";
                resp += "û(" + contarChar(html, 'û') + ") ";
                resp += "consoante(" + contarConsoantes(html) + ") ";
                resp += "<br>(" + contarTag(html, "br") + ") ";
                resp += "<table>(" + contarTag(html, "table") + ") ";
                resp += nome;
                MyIO.println(resp);
            }
        }while(!isFim(nome));
        sc.close();
    }
}
