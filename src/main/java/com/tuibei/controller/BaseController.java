package com.tuibei.controller;

import com.tuibei.utils.AES;
import com.tuibei.utils.Encrypt;
import com.tuibei.utils.GsonUtils;
import com.tuibei.utils.PageData;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

public class BaseController {

    /**
     * 得到ModelAndView
     */
    public ModelAndView getModelAndView(){
        return new ModelAndView();
    }

    /**
     * 得到request对象
     */
    public HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    /**
     * h请求信息封装到Map 中
     * @return
     */
    public PageData getPageData(){
        return new PageData(this.getRequest());
    }

    /**
     * AES 校验参数准确性
     */

    public boolean AESCHECK(String encrypt) {

        boolean check =true;

        String s = AES.decryptCbcMode(encrypt,null,null);
        try {
            Encrypt ens = GsonUtils.fromJson(encrypt, Encrypt.class);
            if(null==ens|| StringUtils.isEmpty(ens.getCurrentTime())
                    ||StringUtils.isEmpty(ens.getMember_id())
            ||StringUtils.isEmpty(ens.getUrl())){
                check=false;
                return check;

            }
            String current_time=ens.getCurrentTime();
            String url =ens.getUrl();

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE,5);//时间在5分钟之内
            long timeInMillis = cal.getTimeInMillis();
            long receive = Long.valueOf(current_time);
            long current =System.currentTimeMillis();
            if(receive>timeInMillis && receive<current){
                check =false;
                return check;
            }



            HttpServletRequest request = this.getRequest();
            if(!request.getRequestURL().toString().contains(url)){
                check=false;
                return check;

            }
        }catch (Exception e){
            check=false;
        }

        return check;

    }




}
