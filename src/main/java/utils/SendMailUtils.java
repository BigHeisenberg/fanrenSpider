package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.Books;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class SendMailUtils {
    private static Logger LOG = LoggerFactory.getLogger(SendMailUtils.class);


    public void SendMail(Books books)
    {
        Properties p = new Properties();
        if (GetProperties.MY_EMAIL_ACCOUNT.indexOf("163.com") != -1) {       //如果是163邮箱就使用163邮箱配置信息
            p.setProperty("mail.smtp.host", GetProperties.MEAIL_163_SMTP_HOST);
            p.setProperty("mail.smtp.port", GetProperties.SMTP_163_PORT);
            p.setProperty("mail.smtp.socketFactory.port", GetProperties.SMTP_163_PORT);
            p.setProperty("mail.smtp.socketFactory.class", "SSL_FACTORY");////使用JSSE的SSL socketfactory来取代默认的socketfactory
            LOG.info("开始使用163邮箱编写邮件");
        } else if (GetProperties.MY_EMAIL_ACCOUNT.indexOf("qq.com") != -1) {     //如果是QQ邮箱就使用QQ邮箱配置信息
            p.setProperty("mail.smtp.host", GetProperties.MEAIL_QQ_SMTP_HOST);
            p.setProperty("mail.smtp.port", GetProperties.SMTP_QQ_PORT);
            p.setProperty("mail.smtp.ssl.enable", "true");//开启SSL连接
            LOG.info("开始使用QQ邮箱编写邮件");
        }
        p.setProperty("mail.smtp.auth", "true");    //验证用户名密码
        p.setProperty("mail.debug", "true");    //开启调试模式（启用调试模式可打印客户端与服务器交互过程时一问一答的响应消息）
        Session session = Session.getInstance(p, new Authenticator() {
            // 设置认证账户信息
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(GetProperties.MY_EMAIL_ACCOUNT,GetProperties.MY_EMAIL_PASSWORD);
            }
        });
//        session.setDebug(true);
        LOG.info("邮箱已登录成功，开始创建邮件内容");
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(GetProperties.MY_EMAIL_ACCOUNT));//设置发件人信息
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        try {
            message.setRecipients(Message.RecipientType.TO, GetProperties.RECEIVE_EMAIL_ACCOUNT);    //设置收件人信息
            String webName = "";
            if (books.getUrl().indexOf(GetProperties.BIQUBOOK_URL)!=-1){
                webName = "百趣阁站";
            }else if(books.getUrl().indexOf(GetProperties.TOPBOOK_URL)!=-1){
                webName = "顶点小说站";
            }
            message.setSubject("南宫婉温馨提醒：今日" +webName+ books.getaMOrPm() + "章节已更新");     //设置邮件主题
            message.setContent("<h1>更新时间：" + books.getUpdateTime() + "<br> 更新地址: <a href=\"" + books.getUrl() + "\">" + books.getTitle() + "</a></h1>", "text/html;charset=UTF-8");//设置邮件内容
            message.setSentDate(new Date());
            message.saveChanges();
            LOG.info("邮件内容编写完毕，开始发送邮件给" + GetProperties.RECEIVE_EMAIL_ACCOUNT);
            Transport.send(message);    //发送邮件
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}