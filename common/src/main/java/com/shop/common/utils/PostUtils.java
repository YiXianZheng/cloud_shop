package com.shop.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther Toney
 * @Date 2018/9/21 00:51
 * @Description:
 */
public class PostUtils {

    private static Logger logger = LoggerFactory.getLogger(PostUtils.class);

    /**
     * 发送HttpPost请求
     * @param strURL 服务地址
     * @param params 请求参数
     * @return 成功:返回json字符串
     */
        public static String jsonPost(String strURL, Map<String, String> params) {

        Date postBeginTime = new Date();
        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(strURL);

            //添加请求头
            post.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            post.setHeader("Content-Type", "application/json");


            if(params != null) {
                List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();

                for (Map.Entry<String, String> entry : params.entrySet()
                        ) {
                    urlParameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }

                post.setEntity(new UrlEncodedFormEntity(urlParameters, "utf-8"));
            }

            HttpResponse response = client.execute(post);
            logger.info("Sending 'POST' request to URL : " + strURL);
            logger.info("Post parameters : " + post.getEntity());
            logger.info("Response Code : " +
                    response.getStatusLine().getStatusCode());

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            return result.toString();
        }catch (Exception e) {
            logger.error("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
            return null;
        }finally {
            Date postEndTime = new Date();
            logger.info("post ["+ strURL +"] cost time == >  ["+ (postEndTime.getTime()-postBeginTime.getTime()) +"ms]");
        }

    }

    /**
     * 发送HttpPost请求
     * @param strURL 服务地址
     * @param params 请求参数
     * @param charset 字符编码
     * @return 成功:返回json字符串
     */
    public static String jsonPostForCharset(String strURL, Map<String, String> params, String charset) {

        Date postBeginTime = new Date();
        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(strURL);

            //添加请求头
            post.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

            if(params != null) {
                List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();

                for (Map.Entry<String, String> entry : params.entrySet()
                        ) {
                    urlParameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }

                post.setEntity(new UrlEncodedFormEntity(urlParameters, charset));
            }

            String temp = new String(new BufferedReader(new InputStreamReader(post.getEntity().getContent())).readLine());
            HttpResponse response = client.execute(post);
            logger.info("Sending 'POST' request to URL : " + strURL);
            logger.info("Post parameters : " + post.getEntity());
            logger.info("Response Code : " +
                    response.getStatusLine().getStatusCode());

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent(), charset));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            return result.toString();
        }catch (Exception e) {
            logger.error("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
            return null;
        }finally {
            Date postEndTime = new Date();
            logger.info("post ["+ strURL +"] cost time == >  ["+ (postEndTime.getTime()-postBeginTime.getTime()) +"ms]");
        }

    }

    /**
     * 发送HttpPost请求
     * @param strURL 服务地址
     * @param xmlStr 请求参数
     * @return 成功:返回json字符串
     */
    public static String xmlPost(String strURL, String xmlStr, String contentType) {

        Date postBeginTime = new Date();
        try {
            URL url = new URL(strURL);
            URLConnection con = url.openConnection();
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", contentType);

            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
            out.write(new String(xmlStr.getBytes(StandardCharsets.UTF_8)));
            out.flush();
            out.close();

            logger.info("Sending 'POST' request to URL : " + strURL);

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

            StringBuilder result = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        } catch (Exception e) {
            logger.error("send POST request exception == > " + e);
            e.printStackTrace();
            return null;
        }finally {
            Date postEndTime = new Date();
            logger.info("post [" + strURL + "] cost time == >  [" + (postEndTime.getTime() - postBeginTime.getTime()) + "ms]");
        }
    }


    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        Date postBeginTime = new Date();
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
            Date postEndTime = new Date();
            logger.info("post ["+ url +"] cost time == >  ["+ (postEndTime.getTime()-postBeginTime.getTime()) +"ms]");
        }
        return result;
    }

    public static String postWithJson(String url, String json) {
        String returnValue = "这是默认返回值，接口调用失败";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try{
            //第一步：创建HttpClient对象
            httpClient = HttpClients.createDefault();

            //第二步：创建httpPost对象
            HttpPost httpPost = new HttpPost(url);

            //第三步：给httpPost设置JSON格式的参数
            StringEntity requestEntity = new StringEntity(json,"utf-8");
            requestEntity.setContentEncoding("UTF-8");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setEntity(requestEntity);

            //第四步：发送HttpPost请求，获取返回值
            returnValue = httpClient.execute(httpPost,responseHandler); //调接口获取返回值时，必须用此方法

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //第五步：处理返回值
        return returnValue;
    }

    /**
     * json参数post请求
     * @param url
     * @param jsonObject
     * @return
     * @throws IOException
     */
    public static String send(String url, JSONObject jsonObject) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        StringEntity s = new StringEntity(jsonObject.toString(), "UTF-8");
        s.setContentType("application/json");

        post.setEntity(s);

        post.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        post.setHeader("Content-Type", "application/json");

        CloseableHttpResponse response = client.execute(post);
        HttpEntity entity = response.getEntity();
        String body = "";
        if (entity != null) {
            body = EntityUtils.toString(entity);
        }
        EntityUtils.consume(entity);
        response.close();
        return body;
    }
}
