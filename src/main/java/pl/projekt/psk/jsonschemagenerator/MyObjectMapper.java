package pl.projekt.psk.jsonschemagenerator;

import java.util.LinkedHashMap;
import java.util.Map;

public class MyObjectMapper {

    private static final char KLAMRA_CHAR_O = '{';
    private static final char KLAMRA_CHAR_Z = '}';
    private static final String EMPTY_STRING = "";

    /*
    1. walidacja String json'a
    2. oczysczenie
    3. iteruje po znakach w tym stringu i kazdy znak umieszczam do BUFORA
    4. jak znajde znak ':' to oczyszczam bufor, uznaje bufor za klucz i zeruje bufor
    5. znowu dodaje znaki do bufora
    6. koncu musze dostać znak ',' i jak go dostaje to uznaje wszystkei znaki z bufora za wartosc
    7. wrzucam do map aktualny klucz i wartosc i zeruje bufor i aktualny klucz
    8. WAZNE: jesli podczas parsowania wartosci okaze sie ze zawiera ona znak '{' oznacza to zagniezdzony
       obiekt i tutaj w chodzi w gre rekurencja
    9. na samym koncu zawsze znajduje sie wartosc ktora po sobie nie ma znaku ',' wiec nie czekam na znak ','
       bez tego ostani elemnt nie zostal by dodany do mapy wynikowej!!
     */
    public static Map<String, Object> fromJson(String json) {
        if (json == null || json.isEmpty()) {
            return new LinkedHashMap<>();
        }
        String cleanJson = json.trim();
        if (cleanJson.startsWith(String.valueOf(KLAMRA_CHAR_O)) && cleanJson.endsWith(String.valueOf(KLAMRA_CHAR_Z))) {
            cleanJson = cleanJson.substring(1, cleanJson.length() - 1);
        }
        int licznikKlamr = 0;

        LinkedHashMap<String, Object> map = new LinkedHashMap<>();

        StringBuilder bufor = new StringBuilder();
        String aktualnyKlucz = null;

        for (char znak : cleanJson.toCharArray()) {
            licznikKlamr = liczKlamryDoDalszychKrokow(znak, licznikKlamr);

            if (znak == ':' && licznikKlamr == 0) {
                aktualnyKlucz = bufor.toString().trim().replace("\"", EMPTY_STRING);
                bufor.setLength(0);
            } else if (znak == ',' && licznikKlamr == 0) {
                map.put(aktualnyKlucz, parseValue(bufor.toString()));
                bufor.setLength(0);
                aktualnyKlucz = null;
            } else {
                bufor.append(znak);
            }
        }
        map.put(aktualnyKlucz, parseValue(bufor.toString()));
        return map;
    }

    private static int liczKlamryDoDalszychKrokow(char znak, int licznikKlamr) {
        if (KLAMRA_CHAR_O == znak) licznikKlamr++;
        if (KLAMRA_CHAR_Z == znak) licznikKlamr--;
        return licznikKlamr;
    }

    private static Object parseValue(String tekst) {
        tekst = tekst.trim();
        if (tekst.startsWith(String.valueOf(KLAMRA_CHAR_O))) {
            return fromJson(tekst);
        }
        return tekst.replace("\"", EMPTY_STRING);
    }
}