package utils;


import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


public class WXPushUtils {
    private static Logger LOG = LoggerFactory.getLogger(WXPushUtils.class);
    public static HttpRequestUtils httpRequestUtils = new HttpRequestUtils();

    /**
     * 调用Server酱推送至微信
     * Server酱介绍：https://sc.ftqq.com
     * @param pushtext
     */
    public void WXPush(String pushtext) {
        String requestUrl = GetProperties.SERVERCHAN_SCKEY_URL;
        String json = httpRequestUtils.sendPost(requestUrl, pushtext);
        LOG.info("Server酱返回消息：" + json);

        Map<String, Object> map = getMap(json);
        if (map.get("errmsg").toString().indexOf("success")!=-1){
            LOG.info("成功推送至微信消息");
        }else if (map.get("errmsg").toString().indexOf("不要重复发送同样的内容")!=-1){
            LOG.info("推送失败，该消息已重复推送");
        }else{
            LOG.info("推送失败");
        }
    }

    /**
     * JSON转Map
     * @param json
     * @return
     */
    public Map<String, Object> getMap(String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.putAll(jsonObject);
        return valueMap;
    }
}
