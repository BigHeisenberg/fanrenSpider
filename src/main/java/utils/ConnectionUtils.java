package utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ConnectionUtils {
    public  Document Connect(String address) {
        try {
            Document document = Jsoup.connect(address).validateTLSCertificates(false).get();//不需要tls证书验证，避免Jsoup访问https网站报错
            return document;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}