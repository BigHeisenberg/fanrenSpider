package utils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.Books;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class SpiderUtils implements Job {
    private static Logger LOG = LoggerFactory.getLogger(SpiderUtils.class);
    private static ConnectionUtils connectionUtils = new ConnectionUtils();
    private static Books books = new Books();
    public void execute(JobExecutionContext context) throws JobExecutionException {
        SpiderUtils spider = new SpiderUtils();
        spider.getUrlList();
    }


    public void getUrlList() {
        Document biquDcument = connectionUtils.Connect(GetProperties.BIQUBOOK_UPDATE_URL);//笔趣阁更新地址
        Document topDcument = connectionUtils.Connect(GetProperties.TOPBOOK_UPDATE_URL);//顶点小说更新地址

        String topTime = topDcument.select("[class=\"recommend\"]").select("h2").first().text();
        topTime = topTime.substring(topTime.indexOf("更新：") + 3);//获取顶点小说页面更新时间
        String bqTime =biquDcument.select("[class=\"intro str-over-dot\"]").text()
                .replaceAll("凡人修仙传仙界篇最新章节 更新时间：","").replaceAll("T"," ");//百趣阁站更新时间
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date topUpdateTime = new Date();
        Date bqUpdateTime = new Date();

        Date nowTime = new Date();//系统当前时间
        Date AMTime = new Date();
        Date PMTime = new Date();
        DateFormat bcdate = new SimpleDateFormat("yyyy-MM-dd");
        String amTime = bcdate.format(nowTime) + " 09:30:00";//早上更新时间
        String pmTime = bcdate.format(nowTime) + " 17:30:00";//下午更新时间
        try {
            AMTime = sdf.parse(amTime);
            PMTime = sdf.parse(pmTime);
            topUpdateTime = sdf.parse(topTime);//顶点小说页面更新时间
            bqUpdateTime = sdf.parse(bqTime);//百趣阁页面更新时间
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int topAmSub = topUpdateTime.compareTo(AMTime);
        int topPmSub = topUpdateTime.compareTo(PMTime);
        int bqAmSub = bqUpdateTime.compareTo(AMTime);
        int bqPmSub = bqUpdateTime.compareTo(PMTime);
        int firstSub = bqUpdateTime.compareTo(topUpdateTime);
        if (firstSub==1){
            LOG.info("百趣阁网站优先更新，"+ "网站更新时间为：" + bqTime);
            List bqUrlList = new ArrayList();
            Elements bqElements = biquDcument.select("[class=\"chapter\"]");
            Elements bqAElements = bqElements.select("a"); //获取百趣阁页面最新章节列表
            for (Element lib : bqAElements) {
                bqUrlList.add(lib.attr("href"));
            }
            String bqUrlHead = "https://m.biquke.com";
            if (books.getSendMailTag() <1 && bqAmSub == 1 && bqPmSub!=1) {
                LOG.info("百趣阁网站今日中午文章已更新，"+ "网站更新时间为：" + bqTime);
                getBook(bqUrlHead + bqUrlList.get(0).toString(), bqTime, "中午");
                books.setSendMailTag(1);
            }else if (books.getSendMailTag() ==0 && bqAmSub == 1 && bqPmSub == 1) {
                books.setSendMailTag(2);
            }else if (books.getSendMailTag() ==1 && bqAmSub == 1 && bqPmSub == 1) {
                LOG.info("百趣阁网站今日下午文章已更新，"+ "网站更新时间为：" + bqTime);
                getBook(bqUrlHead + bqUrlList.get(0).toString(), bqTime, "下午");
                books.setSendMailTag(2);
            }else if (books.getSendMailTag() <1 && bqAmSub !=1 && bqPmSub !=1) {
                LOG.info( "百趣阁网站今日中午文章还未更新，" + "网站更新时间为：" + bqTime);
            }else if (books.getSendMailTag() ==1 && bqAmSub ==1 && bqPmSub !=1) {
                LOG.info( "百趣阁网站今日下午文章还未更新，" + "网站更新时间为：" + bqTime);
            }else if (books.getSendMailTag() ==2 && (bqAmSub == 1) || (bqPmSub == 1)) {
                LOG.info( "百趣阁网站今日文章均更新完毕，" + "网站更新时间为：" + bqTime);
            }

        }else if (firstSub== -1){
            LOG.info("顶点小说网站优先更新，"+ "网站更新时间为：" + topTime);
            List topUrlList = new ArrayList();
            Elements topElements = topDcument.select("[class=\"directoryArea\"]");
            Elements topAElements = topElements.select("a"); //获取顶点小说页面最新章节列表
//            String newFirstTitle = topAElements.get(0).textNodes().get(0).toString();
            for (Element lit : topAElements) {
                topUrlList.add(lit.attr("href"));
            }
            String topUrlHead = "https://wap.dingdiann.com";
            if (books.getSendMailTag() <1 && topAmSub == 1 && topPmSub != 1) {
                LOG.info("顶点小说网站今日中午文章已更新，"+ "网站更新时间为：" + topTime);
                getBook(topUrlHead + topUrlList.get(0).toString(), topTime, "中午");
                books.setSendMailTag(1);
            } else if (books.getSendMailTag() ==0 && topAmSub == 1 && topPmSub == 1) {
                books.setSendMailTag(2);
            } else if ( books.getSendMailTag() ==1 && topAmSub == 1 && topPmSub == 1) {
                LOG.info("顶点小说网站今日下午文章已更新，"+ "网站更新时间为：" + topTime);
                getBook(topUrlHead + topUrlList.get(0).toString(), topTime, "下午");
                books.setSendMailTag(2);
            }else if (books.getSendMailTag() <1 && topAmSub !=1 && topPmSub !=1) {
                LOG.info( "顶点小说网站今日中午文章还未更新，" + "网站更新时间为：" + topTime);
            }else if (books.getSendMailTag() ==1 && topAmSub ==1 && topPmSub !=1) {
                LOG.info( "顶点小说网站今日下午文章还未更新，" + "网站更新时间为：" + topTime);
            }else if (books.getSendMailTag() ==2 && (topAmSub == 1) || (topPmSub == 1)) {
                LOG.info( "顶点小说网站今日文章均更新完毕，" + "网站更新时间为：" + topTime);
            }
        }
    }

    public void getBook(String url, String updateTime, String amOrPm) {
        Document document = connectionUtils.Connect(url);
        String title = "";
        Elements elements = null;
        if (url.indexOf(GetProperties.BIQUBOOK_URL)!=-1){
            elements= document.select("[id=\"nr\"]");
            title = document.select("title").text().replaceAll("_凡人修仙传仙界篇_修真小说_笔趣阁移动版", "");//最新章节标题
        }else if(url.indexOf(GetProperties.TOPBOOK_URL)!=-1){
            elements = document.select("[class=\"Readarea ReadAjax_content\"]");
            elements.select("p").remove();
            title = document.select("title").text().replaceAll("_凡人修仙传仙界篇_顶点小说", "");//最新章节标题
        }
        List<String> contentList = new ArrayList<String>();
        List<String> codeList = new ArrayList<String>();
        for (Element e : elements) {
            contentList.add(e.text());      //所有内容都装入list
        }
        String allContent = contentList.toString();
        allContent = allContent.substring(1, allContent.length() - 1);//最新章节内容，可放入邮件内容或者写入数据库
        for (Element e1 : elements) {
            codeList.add(e1.toString());
        }
        String allCode = codeList.toString();//最新章节页面代码
        LOG.info("最新章节标题----------------------\n" + title);
        LOG.info("最新章节内容----------------------\n" + allContent);
        LOG.info("最新章节页面源码----------------------\n" + allCode);
        LOG.info("最新章节地址----------------------\n" + url);
        Books book1 = new Books();
        book1.setaMOrPm(amOrPm);
        book1.setText(allContent);
        book1.setUrl(url);
        book1.setTitle(title);
        book1.setUpdateTime(updateTime);
        SendMailUtils sendMailUtils = new SendMailUtils();//调用发送邮件工具类
        sendMailUtils.SendMail(books);
    }

    public static void main(String[] args) {
        SpiderUtils spider = new SpiderUtils();
        spider.getUrlList();
    }

}