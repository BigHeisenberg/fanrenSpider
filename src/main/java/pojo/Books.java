package pojo;

import java.io.Serializable;

public class Books implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String aMOrPm;
    private String title;
    private String text;
    private String url;
    private String updateTime;
    private int sendMailTag;

    public String getaMOrPm()
    {
        return this.aMOrPm;
    }

    public void setaMOrPm(String aMOrPm) {
        this.aMOrPm = aMOrPm;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getSendMailTag() {
        return sendMailTag;
    }

    public void setSendMailTag(int sendMailTag) {
        this.sendMailTag = sendMailTag;
    }
}