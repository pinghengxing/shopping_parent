package com.alipay.config;

import java.io.FileWriter;
import java.io.IOException;
/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。*/
public class AlipayConfig {


//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

        // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
        public static String app_id = "2016102200739226";

        // 商户私钥，您的PKCS8格式RSA2私钥
        public static String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCjCpzyrC6Xh9RxaJSg9uzMedKTQLypRH3AUoUubseNVyMFlEAh1V+TPZOnDuETxj8nMqbV6CzJf828tW1+ivCHYc6lRfji05Sl1D3Po2qGGaRGWphY+Lh+jl5nVSQ7yY/WgPuriUmCzCdJb0HQt8WkY04Ib1vBLRFET55TAirOVR8JJ+IG/GXcP5CrF2ZOWpxiNVSQEg/rdl2nT6F1NQfYEQBsmfrjKQRgR0RW3+BbYUr8iSiX1C8+urfMgXfR2Z2vwN39NsZ2vJfKiGhFY5qoHrysycepEfgyuZ+sPVzKWM1LBYQOhex8zSHF0r4pw30GL2oukk2b/0+65XIeu/XFAgMBAAECggEAXEvpxUbLqWRvUzQMz4J5Utez6UBAjOIE/ha45iw4Mz8cyE/AbmbIECZPq8a0i+kyXxJBd95TXE5JPYbHUVd+CmXIAa+20VFNWQLpat3rs7ZYVKt8iwFiD+KwdnawaFgWYSvrTJQPVUgVBXFCJNGjxQeiaQwBOT42BCG+UudrLBoCVryxEXwELFZ6DtcZCOIsPRGnN2GtobCaud8D5Pd6IhM3/CNOxQI+d502+MSWGVHngegiEswZ9P9FKZ1Nc4VQ8RVZK7KbFaZsbw9RxhfSL1Ckt3Gpah4t+ATr8EcE8fwrsLp5AEdQNsbFNjy6kfM8zvYXfu+HVhbntRr0aVUkgQKBgQDqVYMLSQCU09kbT7jFiWqRQe+//rtnkdjjo10WVTNKVAIeNj+EXy9kO97KVfqSSXj6t/tCUTc+YhZgasMot/tZO5LrFb700OpVTU1Dsihvn8TQw+c8nFGfvdbmgAZPnJ0cZep7ehmPcdQrXz73aEvRUdGL2MY95VSB2cwO5PYwKQKBgQCyHaqUycMHmQL3tSzyadT8leUAkEiS9q1tW2c6h65H5Z5eEBNXrtUj+tDDYY7IjOXZ8Xo/aDdWCjPvUdQCh6x/3MWS2F8t+Ll8t5voEFX3geMLIDx9OSL9vohwxE3ef5AKyRNZNg2T9F/N4BdmfgaK3yfrReDGLFCsnrNAzh4cPQKBgBgQU/aytnaSErd1RloytFjaVo520DPgjE9EFWkXsr6tRdJoa7liNPGsHELW9XZUSTIl9o1m3a9wTy/zhrsFua/+KCYwA34MfiPoXkjXYMOluoZdt79HpjIrQohFGWDPimrCJIQtk7ZhUBJsI4EwmqiSKFyeFC2DAVycSxI0fPuRAoGAMb2zK4YE3xRODdscyD3h6v/5SeUBP3mACTAjbecCNrXbOi2HGU+L2lZrAoU2GK+waOLU3uw9t9t1kXnqmSJGfBatXkrqma4CLuAfu/kD6PDu9OZ1vgq8yNvywnsvu9kn4rswmhNMS2tNbfMM1eSP8yxKu5tyICW0CeDv8kMdiYkCgYBA6Iagdv1VX38RO577gP+X9AythBMDa8dVqteFU+Vhvls9B9/LfaTMoz4Be5A7hOitsgJSSydePOHgqylHVVy5xx359UrY2P094YXPXFSoW18b7HcDK2hAYGYDqfKtq+Unz/kBPVKSbosxmgjCLfzXM2w5Z/8rLy2EGMeFwU73xw==";

        // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
        public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtrMyUXxs/dSjxUx3izQWu9cYIKYXz8CQQH+eITbmnHx204jfITOrn20tXqc6nlxcuC3cjFuSCnQP4oJdPqDyf9L70bnLxrldudFXuoMKL/7TbnDH8ZKl+a1FGmr2bi7bMTMt97XN43rcKdnbGU6j+wUHyqq+juAfG8/OOqyc7u+qWMpICicELmwqfF/ZA1fEd7Ja312VnjIeK5+XxNgr4NL3FtdCPsIYuIVUKKId+q16IoFr58VBDCBAE7lNrZtuxx1d1+SvxyBbytSI7xYE59EeaTMav1q/7HmzsoeTlRDYRxyqCzG7YuZ3R2G1k785KX7C9CdDolgFpRrH65Fi8QIDAQAB";

        // 服务器异步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
        public static String notify_url = " http://fmuqqn.natappfree.cc/callBack/asynCallBack";

        // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
        public static String return_url = " http://fmuqqn.natappfree.cc/callBack/synCallBack";

        // 签名方式
        public static String sign_type = "RSA2";

        // 字符编码格式
        public static String charset = "utf-8";

        // 支付宝网关
        public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

        // 支付宝网关
        public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        /**
         * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
         * @param sWord 要写入日志里的文本内容
         */
        public static void logResult(String sWord) {
            FileWriter writer = null;
            try {
                writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
                writer.write(sWord);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

