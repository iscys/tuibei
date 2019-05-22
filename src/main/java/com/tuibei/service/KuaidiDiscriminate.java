
package com.tuibei.service;
import com.tuibei.http.KuaidiNiaoHttp;
import com.tuibei.model.KuaidiDisNiaoModel;
import com.tuibei.utils.GsonUtils;
import com.tuibei.utils.KudiNiaoMD5Utils;

import java.util.HashMap;

public class KuaidiDiscriminate {

    //DEMO
    public static void main(String[] args) {
        KuaidiDiscriminate api = new KuaidiDiscriminate();
        try {
            String result = api.getOrderTracesByJson("75120498194666");
            KuaidiDisNiaoModel kuaidiDisModel = GsonUtils.fromJson(result, KuaidiDisNiaoModel.class);
            System.out.print(kuaidiDisModel);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //电商ID
    private String EBusinessID="1531615";
    //电商加密私钥，快递鸟提供，注意保管，不要泄漏
    private String AppKey="40d6e732-4a6d-4b4a-9108-88d4a3bfe5ef";
    //请求url
    private String ReqURL="http://api.kdniao.com/Ebusiness/EbusinessOrderHandle.aspx";

    /**
     * Json方式 单号识别
     * @throws Exception
     */
    public String getOrderTracesByJson(String expNo) throws Exception{
        String requestData= "{'LogisticCode':'" + expNo + "'}";

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("RequestData", KudiNiaoMD5Utils.urlEncoder(requestData, "UTF-8"));
        params.put("EBusinessID", EBusinessID);
        params.put("RequestType", "2002");
        String dataSign= KudiNiaoMD5Utils.encrypt(requestData, AppKey, "UTF-8");
        params.put("DataSign", KudiNiaoMD5Utils.urlEncoder(dataSign, "UTF-8"));
        params.put("DataType", "2");
        String result=sendPost(ReqURL, params);

        return result;
    }





    /**
     * 向指定 URL 发送POST方法的请求
     * @param url 发送请求的 URL
     * @param params 请求的参数集合
     * @return 远程资源的响应结果
     */
    @SuppressWarnings("unused")
    private String sendPost(String url, HashMap<String, String> params)throws Exception {
        String result = KuaidiNiaoHttp.INSTANCE.doPost("http://api.kdniao.com/Ebusiness/EbusinessOrderHandle.aspx", params);
        System.out.println(result);
        return result;
    }



}
