package com.michen.olaxueyuan.ui.course.pay.weixin;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;
import android.util.Xml;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.manager.Logger;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.result.UserWXpayResult;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class WxPayUtile {

    private Context context;
    PayReq req;
    final IWXAPI msgApi;
    Map<String, String> resultunifiedorder;
    StringBuffer sb;
    private String total_fee;
    private String notify_url;
    private String body;
    private String outTradNo;
    private UserWXpayResult.ResultBean wxpayResult = new UserWXpayResult().getResult();
    private static final String TAG = "MicroMsg.SDKSample";

    public WxPayUtile(Context context, String total_fee, String notify_url,
                      String body, UserWXpayResult.ResultBean wxpayResult, String outTradNo) {
        super();
        msgApi = WXAPIFactory.createWXAPI(context, null);
        req = new PayReq();
        msgApi.registerApp(Constants.APP_ID);
        sb = new StringBuffer();
        this.context = context;
        this.total_fee = total_fee;
        this.notify_url = notify_url;
        this.body = body;
        this.wxpayResult = wxpayResult;
        this.outTradNo = outTradNo;
    }

    public static WxPayUtile getInstance(Context context, String total_fee,
                                         String notify_url, String body, UserWXpayResult.ResultBean wxpayResult, String outTradNo) {

        return new WxPayUtile(context, total_fee, notify_url, body, wxpayResult, outTradNo);
    }

    public void doPay() {
        if (!msgApi.isWXAppInstalled()) {
            ToastUtil.showToastLong(context, R.string.wechat_not_install);
            return;
        }
        GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
        getPrepayId.execute();
    }

    /**
     * 生成签名
     */

    private String genPackageSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Constants.API_KEY);

        String packageSign = MD5.getMessageDigest(sb.toString().getBytes())
                .toUpperCase(Locale.CHINA);
        // Log.e("orion-packageSign-->", packageSign);
        return packageSign;
    }

    private String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Constants.API_KEY);

        this.sb.append("sign str\n" + sb.toString() + "\n\n");
        String appSign = MD5.getMessageDigest(sb.toString().getBytes())
                .toUpperCase();
        //   Log.e("orion-appSign-->", appSign);
        return appSign;
    }

    private String toXml(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<" + params.get(i).getName() + ">");

            sb.append(params.get(i).getValue());
            sb.append("</" + params.get(i).getName() + ">");
        }
        sb.append("</xml>");

        // Log.e("orion-sb--->", sb.toString());
        return sb.toString();
    }

    private class GetPrepayIdTask extends
            AsyncTask<Void, Void, Map<String, String>> {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(context,
                    context.getString(R.string.app_tip),
                    context.getString(R.string.getting_prepayid));
        }

        @Override
        protected void onPostExecute(Map<String, String> result) {
            if (dialog != null) {
                dialog.dismiss();
            }
            sb.append("prepay_id\n" + result.get("prepay_id") + "\n\n");
            resultunifiedorder = result;

            //  Log.e("orion-result_code-->", result.get("result_code"));
            if (result.get("result_code").equals("SUCCESS")) {
                //   Log.e("orion-result_code-->", "IS SUCCESS!");
                genPayReq();
            } else {
                Message msg = new Message();
                msg.obj = result.get("result_code");
                msg.what = 800;
            }

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Map<String, String> doInBackground(Void... params) {

            String url = String
                    .format("https://api.mch.weixin.qq.com/pay/unifiedorder");
            String entity = genProductArgs();

            //   Log.e("orion-entity-->", entity);

            byte[] buf = HttpWxUtile.httpPost(url, entity);

            String content = new String(buf);
            //  Log.e("orion-content-->", content);
            Map<String, String> xml = decodeXml(content);

            return xml;
        }
    }

    public Map<String, String> decodeXml(String content) {

        try {
            Map<String, String> xml = new HashMap<String, String>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {

                String nodeName = parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    case XmlPullParser.START_TAG:

                        if ("xml".equals(nodeName) == false) {
                            // 实例化student对象
                            xml.put(nodeName, parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }

            return xml;
        } catch (Exception e) {
            //   Log.e("orion-e--->", e.toString());
        }
        return null;

    }

    private String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000))
                .getBytes());
    }

    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 获取OutTradNo可根据用户自行更改
     *
     * @return
     */
    private String genOutTradNo() {
        // Random random = new Random();
        // int time = (int) System.currentTimeMillis();
        // return
        // MD5.getMessageDigest(String.valueOf(random.nextInt(10000)+time).getBytes());
        return outTradNo;
    }

    //
    private String genProductArgs() {
        StringBuffer xml = new StringBuffer();

        try {
            String nonceStr = genNonceStr();
            /*req.appId =wxpayResult.getAppId();
            req.partnerId = wxpayResult.getPartnerId();
			req.prepayId =  wxpayResult.getPrepayId();
			req.packageValue = wxpayResult.getPackageValue();
			req.nonceStr =  wxpayResult.getNonceStr();
			req.timeStamp = wxpayResult.getTimeStamp();*/
            xml.append("</xml>");
            List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
            packageParams.add(new BasicNameValuePair("appid", wxpayResult.getAppid()));
            packageParams.add(new BasicNameValuePair("body", body));
            packageParams.add(new BasicNameValuePair("mch_id", wxpayResult.getPartnerid()));
            packageParams.add(new BasicNameValuePair("nonce_str", wxpayResult.getNoncestr()));
            packageParams.add(new BasicNameValuePair("notify_url", notify_url));
            packageParams.add(new BasicNameValuePair("out_trade_no", genOutTradNo()));
            packageParams.add(new BasicNameValuePair("spbill_create_ip", "127.0.0.1"));
            packageParams.add(new BasicNameValuePair("total_fee", total_fee));
            packageParams.add(new BasicNameValuePair("trade_type", "APP"));
            /*xml.append("</xml>");
            List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
			packageParams.add(new BasicNameValuePair("appid", Constants.APP_ID));
			packageParams.add(new BasicNameValuePair("body", body));
			packageParams.add(new BasicNameValuePair("mch_id", Constants.MCH_ID));
			packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
			packageParams.add(new BasicNameValuePair("notify_url", notify_url));
			packageParams.add(new BasicNameValuePair("out_trade_no",genOutTradNo()));
			packageParams.add(new BasicNameValuePair("spbill_create_ip","127.0.0.1"));
			packageParams.add(new BasicNameValuePair("total_fee", total_fee));
			packageParams.add(new BasicNameValuePair("trade_type", "APP"));*/
            String sign = genPackageSign(packageParams);
            packageParams.add(new BasicNameValuePair("sign", sign));

            String xmlstring = toXml(packageParams);

            return xmlstring;

        } catch (Exception e) {
            Log.e(TAG, "genProductArgs fail, ex = " + e.getMessage());
            return null;
        }

    }

    private void genPayReq() {

        req.appId = wxpayResult.getAppid();
        req.partnerId = wxpayResult.getPartnerid();
        req.prepayId = wxpayResult.getPrepayid();
        req.packageValue = wxpayResult.getPackageX();
        req.nonceStr = wxpayResult.getNoncestr();
        req.timeStamp = wxpayResult.getTimestamp();

	/*	List<NameValuePair> signParams = new LinkedList<NameValuePair>();
		signParams.add(new BasicNameValuePair("appid",wxpayResult.getAppId()));
		signParams.add(new BasicNameValuePair("noncestr", wxpayResult.getNonceStr()));
		signParams.add(new BasicNameValuePair("package",wxpayResult.getPackageValue()));
		signParams.add(new BasicNameValuePair("partnerid", wxpayResult.getPartnerId()));
		signParams.add(new BasicNameValuePair("prepayid", wxpayResult.getPrepayId()));
		signParams.add(new BasicNameValuePair("timestamp", wxpayResult.getTimeStamp()));*/

        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

        req.sign = wxpayResult.getSign();

        sb.append("sign\n" + req.sign + "\n\n");

        Logger.e("orion-signParams-->"+signParams.toString());

        sendPayReq();

    }

    private void sendPayReq() {
        msgApi.registerApp(Constants.APP_ID);
        msgApi.sendReq(req);
    }
}
