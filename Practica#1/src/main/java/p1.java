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
        String linea = new String();
        String url= new String();
        boolean punto = true;
        System.out.print("Por favor Introducir una url:");
        do{
            Scanner scanner = new Scanner(System.in);
            linea = scanner.nextLine();
            if(linea.contains(".")){
                punto=false;
            }
            else{
                System.out.print("Url no valida, por favor introducir otra:");
            }
        }while(punto);
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
        System.out.print("e)");
        for(int i=0; i< forms.size();i++){
            System.out.println("Form ID:"+forms.get(i).attr("id"));
            inputs = forms.get(i).select("input");
            for(int j=0; j < inputs.size() ; j++ ){
                System.out.println("Input name:" + inputs.get(j).attr("name") + " Input type:" + inputs.get(j).attr("type"));
            }

        }

    }
}
