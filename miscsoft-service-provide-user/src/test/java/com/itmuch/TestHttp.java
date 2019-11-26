package com.itmuch;

import com.google.common.collect.Maps;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.Iterator;
import java.util.Map;

public class TestHttp {

    private static final String KEY = "64196004bbb946398fb5b4d4ef17277a";
    private static final String SERCET = "38CC135B7615196EF52E032C36B67FF2";

    public static void main(String args[]) throws Exception{
//        StringBuilder url = new StringBuilder("http://api.qichacha.com/ECIInvestment/GetInvestmentList?");
        StringBuilder url = new StringBuilder("http://api.qichacha.com/Recruitment/GetList?");

        Map<String, String> param = Maps.newHashMap();
        param.put("searchKey", "北京京东世纪贸易有限公司");
        param.put("key", KEY);
        for(Map.Entry<String, String> entry : param.entrySet()){
            if(StringUtils.isNotBlank(entry.getKey()) && StringUtils.isNotBlank(entry.getValue())){
               url.append(entry.getKey());
               url.append("=");
               url.append(entry.getValue());
               url.append("&");
            }
        }
        Map<String, String> header = getHeader();
        System.out.println(doGet(url.substring(0, url.length() -1), header, null));
    }

    private static Map<String, String> getHeader(){
        Map<String, String> header = Maps.newHashMap();
        String timeSpan = String.valueOf(System.currentTimeMillis() / 1000);
        header.put("Timespan", timeSpan);
        header.put("Token", DigestUtils.md5Hex(KEY.concat(timeSpan).concat(SERCET)).toUpperCase());
        return header;
    }

    public static String doGet(String url, Map<String, String> header, Map<String, String> param) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse response = null;

        try {
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                Iterator var6 = param.keySet().iterator();

                while(var6.hasNext()) {
                    String key = (String)var6.next();
                    builder.addParameter(key, (String)param.get(key));
                }
            }
            URI uri = builder.build();
            HttpGet httpGet = new HttpGet(uri);
            for(Map.Entry<String, String> entry : header.entrySet()){
                if(StringUtils.isNotBlank(entry.getKey()) && StringUtils.isNotBlank(entry.getValue())){
                    httpGet.addHeader(entry.getKey(), entry.getValue());
                }
            }
            response = httpclient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception var16) {
            var16.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }

                httpclient.close();
            } catch (IOException var15) {
                var15.printStackTrace();
            }

        }

        return resultString;
    }
}
