package utils;

import java.io.IOException;
import java.util.Properties;

public class GetProperties {
    public static String BIQUBOOK_URL = "";   //笔趣阁首页
    public static String TOPBOOK_URL = "";   //顶点小说首页
    public static String BIQUBOOK_UPDATE_URL="";//笔趣阁更新地址
    public static String TOPBOOK_UPDATE_URL="";//顶点小说更新地址
    public static String MY_EMAIL_ACCOUNT = "";   //发件人地址
    public static String MY_EMAIL_PASSWORD = "";  //发件人密码
    public static String RECEIVE_EMAIL_ACCOUNT = "";  //收件人地址
    public static String MEAIL_163_SMTP_HOST = "";    //网易163邮箱SMTP服务器地址
    public static String SMTP_163_PORT = "";  //网易163邮箱端口
    public static String MEAIL_QQ_SMTP_HOST = ""; //QQ邮箱SMTP服务器地址
    public static String SMTP_QQ_PORT = "";   //QQ邮箱端口

    static {
        try {
            Properties props = new Properties();
            props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
            BIQUBOOK_URL = props.getProperty("BIQUBOOK_URL");
            TOPBOOK_URL = props.getProperty("TOPBOOK_URL");
            BIQUBOOK_UPDATE_URL = props.getProperty("BIQUBOOK_UPDATE_URL");
            TOPBOOK_UPDATE_URL = props.getProperty("TOPBOOK_UPDATE_URL");
            MY_EMAIL_ACCOUNT = props.getProperty("MY_EMAIL_ACCOUNT");
            MY_EMAIL_PASSWORD = props.getProperty("MY_EMAIL_PASSWORD");
            RECEIVE_EMAIL_ACCOUNT = props.getProperty("RECEIVE_EMAIL_ACCOUNT");
            MEAIL_163_SMTP_HOST = props.getProperty("MEAIL_163_SMTP_HOST");
            SMTP_163_PORT = props.getProperty("SMTP_163_PORT");
            MEAIL_QQ_SMTP_HOST = props.getProperty("MEAIL_QQ_SMTP_HOST");
            SMTP_QQ_PORT = props.getProperty("SMTP_QQ_PORT");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
