package utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ConnectionUtils {
    public  Document Connect(String address) {
        try {
            Document document = Jsoup.connect(address).validateTLSCertificates(false).get();
            return document;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}