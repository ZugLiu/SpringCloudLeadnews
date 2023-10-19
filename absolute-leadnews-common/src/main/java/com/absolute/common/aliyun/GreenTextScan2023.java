package com.absolute.common.aliyun;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.green20220302.Client;
import com.aliyun.green20220302.models.TextModerationRequest;
import com.aliyun.green20220302.models.TextModerationResponse;
import com.aliyun.green20220302.models.TextModerationResponseBody;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "aliyun")
public class GreenTextScan2023 {
    private String accessKeyId;
    private String secret;

    public Map textScan(String content) throws Exception {
        Config config = new Config();
        config.setAccessKeyId(accessKeyId);
        config.setAccessKeySecret(secret);
        config.setRegionId("cn-beijing");
        config.setEndpoint("green-cip.cn-beijing.aliyuncs.com");
        //连接时超时时间，单位毫秒（ms）。
        config.setReadTimeout(6000);
        //读取时超时时间，单位毫秒（ms）。
        config.setConnectTimeout(3000);

        Client client = new Client(config);
        // 创建RuntimeObject实例并设置运行参数。
        RuntimeOptions runtime = new RuntimeOptions();
        runtime.readTimeout = 10000;
        runtime.connectTimeout = 10000;

        //检测参数构造
        JSONObject serviceParameters = new JSONObject();
        serviceParameters.put("content", content);

        if (serviceParameters.get("content") == null || serviceParameters.getString("content").trim().length() == 0) {
            System.out.println("text moderation content is empty");
            return null;
        }

        TextModerationRequest textModerationRequest = new TextModerationRequest();
        /*
        文本检测服务 service code
        */
        textModerationRequest.setService("nickname_detection");
        textModerationRequest.setServiceParameters(serviceParameters.toJSONString());
        try {
            // 调用方法获取检测结果。
            TextModerationResponse response = client.textModerationWithOptions(textModerationRequest, runtime);

            // 自动路由。
            if (response != null) {
                // 服务端错误，区域切换到cn-beijing。
                if (500 == response.getStatusCode() || (response.getBody() != null && 500 == (response.getBody().getCode()))) {
                    // 接入区域和地址请根据实际情况修改。
                    config.setRegionId("cn-beijing");
                    config.setEndpoint("green-cip.cn-beijing.aliyuncs.com");
                    client = new Client(config);
                    response = client.textModerationWithOptions(textModerationRequest, runtime);
                }

            }
            // 打印检测结果。
            if (response != null) {
                if (response.getStatusCode() == 200) {
                    TextModerationResponseBody result = response.getBody();
                    System.out.println(JSON.toJSONString(result));
                    Integer code = result.getCode();
                    if (code != null && code == 200) {
                        TextModerationResponseBody.TextModerationResponseBodyData data = result.getData();
                        System.out.println("labels = [" + data.getLabels() + "]");
                        System.out.println("reason = [" + data.getReason() + "]");
                    } else {
                        System.out.println("text moderation not success. code:" + code);
                    }
                } else {
                    System.out.println("response not success. status:" + response.getStatusCode());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
