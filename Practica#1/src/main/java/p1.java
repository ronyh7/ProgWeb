import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Rony on 5/26/2016.
 */
public class p1 {
    public static void main(String[] args) {
        Scanner data = new Scanner(System.in);
        String linea = data.nextLine();
        String url = new String();
        if (!linea.contains("http")) {
            url = "http://" + linea;
        } else {
            url = linea;
        }

        Document doc = new Document("");
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements parrafos = doc.getElementsByTag("p");
        Elements imagenes = doc.getElementsByTag("img");
        Elements forms = doc.getElementsByTag("form");
        Elements inputs;

        String[] lineas = doc.toString().split("\n");
        System.out.println("a)" + lineas.length);
        System.out.println("b)" + parrafos.size());
        System.out.println("c)" + imagenes.size());
        System.out.println("d)" + forms.size());
    }
}
