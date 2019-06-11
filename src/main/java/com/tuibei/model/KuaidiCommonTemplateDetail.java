package com.tuibei.model;

import com.tuibei.model.KuaidiCommonTemplate;
import com.tuibei.model.kdn.KDNTraces;
import com.tuibei.model.kdn.KDNTracesDetail;
import lombok.Data;
import lombok.ToString;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 快递返回公共模板，返回前端
 */
@Data
@ToString
public class KuaidiCommonTemplateDetail implements Serializable {

    private List<KuaidiCommonTemplate> traces;//快递物流信息
    private String state;//快递目前的状态
    private String operator;//快递运营方
    private String traceNum;//快递单号
    private String code;//公司code
    private String time;//查询时间



    public void KDN2Common(KDNTracesDetail kdnTracesDetail) {
        this.traces =new ArrayList<KuaidiCommonTemplate>();
        List<KDNTraces> kdnTraces = kdnTracesDetail.getTraces();
        if(!CollectionUtils.isEmpty(kdnTraces)){
            for(KDNTraces trace :kdnTraces){
                KuaidiCommonTemplate tem =new KuaidiCommonTemplate();
                tem.setAccept_location(trace.getAcceptStation());
                tem.setAccept_time(trace.getAcceptTime());
                traces.add(tem);
            }
        };
        // 0-无轨迹，1-已揽收，2-在途中，3-签收,4-问题件
        switch (kdnTracesDetail.getState()){
            case 0:
                this.setState("无轨迹");
                break;
            case 1:
                this.setState("已揽收");
                break;
            case 2:
                this.setState("在途中");
                break;
            case 3:
                this.setState("签收");
                break;
            case 4:
                this.setState("问题件");
                break;
            default :
                this.setState("查询异常");

        }


    }
}
