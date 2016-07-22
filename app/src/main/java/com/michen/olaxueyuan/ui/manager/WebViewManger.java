package com.michen.olaxueyuan.ui.manager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.michen.olaxueyuan.ui.activity.SEBaseActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;

/**
 * Created by mingge on 2016/7/7.
 */
public class WebViewManger {
    private WebViewManger() {
    }

    private static WebViewManger webViewManger;

    public static WebViewManger getInstance() {
        if (webViewManger == null) {
            webViewManger = new WebViewManger();
        }
        return webViewManger;
    }

    Activity activity;
    WebView mWebView;
    String mUrl;
    private final static int FILECHOOSER_RESULTCODE = 1;

    public void initView(Activity activity, WebView mWebView, String mUrl) {
        this.activity = activity;
        this.mWebView = mWebView;
        this.mUrl = mUrl;
        initWebView();
    }

    private void initWebView() {
        if (mWebView != null) {
            // 关闭自动保存密码
            mWebView.getSettings().setSavePassword(false);
        }

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);//不设置缓存
        webSettings.setSupportZoom(true);
//        webSettings.setDefaultFontSize(30);//WebSettings.ZoomDensity.MEDIUM);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);// 0 不给滚动条留空间

        mWebView.setDownloadListener(new MyWebViewDownLoadListener());
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {

                }
                super.onProgressChanged(view, newProgress);
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                activity.startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
            }

            // For Android 3.0+
            public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                activity.startActivityForResult(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE);
            }

            // For Android 4.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                activity.startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
            }
        });
        syncCookie(activity, mUrl);
    }


    private void syncCookie(Context context, String url) {
        try {
            CookieSyncManager.createInstance(context);

            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.removeSessionCookie();// 移除
            cookieManager.removeAllCookie();
            String oldCookie = cookieManager.getCookie(url);
            if (oldCookie != null) {
//                Log.d("Nat: webView.syncCookieOutter.oldCookie", oldCookie);
            }

            StringBuilder sbCookie = new StringBuilder();
            sbCookie.append(String.format("JSESSIONID=%s", "INPUT YOUR JSESSIONID STRING"));
            sbCookie.append(String.format(";domain=%s", "INPUT YOUR DOMAIN STRING"));
            sbCookie.append(String.format(";path=%s", "INPUT YOUR PATH STRING"));

            String cookieValue = sbCookie.toString();
            cookieManager.setCookie(url, cookieValue);
            CookieSyncManager.getInstance().sync();

            String newCookie = cookieManager.getCookie(url);
            if (newCookie != null) {
//                Log.d("Nat: webView.syncCookie.newCookie", newCookie);
            }
        } catch (Exception e) {
//            Log.e("Nat: webView.syncCookie failed", e.toString());
        }
    }

    private class MyWebViewDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(final String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                Toast t = Toast.makeText(activity, "需要SD卡。", Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER, 0, 0);
                t.show();
                return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("提示！");
            builder.setMessage("确认下载？");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.dismiss();
                    DownloaderTask task = new DownloaderTask();
                    task.execute(url);
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // 取消
                    dialog.dismiss();
                }
            });
            builder.show();

        }

    }

    // 内部类
    private class DownloaderTask extends AsyncTask<String, Void, String> {

        public DownloaderTask() {
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            String url = params[0];
            // Log.i("tag", "url="+url);
            String fileName = url.substring(url.lastIndexOf("/") + 1);
            fileName = URLDecoder.decode(fileName);

            File directory = Environment.getExternalStorageDirectory();
            File file = new File(directory, fileName);
            if (file.exists()) {
                return fileName;
            }
            try {
                HttpClient client = new DefaultHttpClient();
                // client.getParams().setIntParameter("http.socket.timeout",3000);//设置超时
                HttpGet get = new HttpGet(url);
                HttpResponse response = client.execute(get);
                if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                    HttpEntity entity = response.getEntity();
                    InputStream input = entity.getContent();

                    writeToSDCard(fileName, input);

                    input.close();
                    // entity.consumeContent();
                    return fileName;
                } else {
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onCancelled() {
            // TODO Auto-generated method stub
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            closeProgressDialog();
            if (result == null) {
                Toast t = Toast.makeText(activity, "连接错误！请稍后再试！", Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER, 0, 0);
                t.show();
                return;
            }

            Toast t = Toast.makeText(activity, "已保存到SD卡。", Toast.LENGTH_LONG);
            t.setGravity(Gravity.CENTER, 0, 0);
            t.show();
            File directory = Environment.getExternalStorageDirectory();
            File file = new File(directory, result);
            Intent intent = getFileIntent(file);

            activity.startActivity(intent);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

    }

    public Intent getFileIntent(File file) {
        // Uri uri = Uri.parse("http://m.ql18.com.cn/hpf10/1.pdf");
        Uri uri = Uri.fromFile(file);
        String type = getMIMEType(file);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, type);
        return intent;
    }

    private ProgressDialog mDialog;

    private void showProgressDialog() {
        if (mDialog == null) {
            mDialog = new ProgressDialog(activity);
            mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置风格为圆形进度条
            mDialog.setMessage("正在下载 ，请等待...");
            mDialog.setIndeterminate(false);// 设置进度条是否为不明确
            mDialog.setCancelable(true);// 设置进度条是否可以按退回键取消
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    // TODO Auto-generated method stub
                    mDialog = null;
                }
            });
            mDialog.show();

        }
    }

    private void closeProgressDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    public void writeToSDCard(String fileName, InputStream input) {

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File directory = Environment.getExternalStorageDirectory();
            File file = new File(directory, fileName);
            try {
                FileOutputStream fos = new FileOutputStream(file);
                byte[] b = new byte[2048];
                int j = 0;
                while ((j = input.read(b)) != -1) {
                    fos.write(b, 0, j);
                }
                fos.flush();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
//            LogUtil.i("tag" + "NO SDCard.");
        }
    }

    private String getMIMEType(File f) {
        String type = "";
        String fName = f.getName();
        /* 取得扩展名 */
        String end = fName.substring(fName.lastIndexOf(".") + 1, fName.length()).toLowerCase();

		/* 依扩展名的类型决定MimeType */
        if (end.equals("pdf")) {
            type = "application/pdf";//
        } else if (end.equals("m4a") || end.equals("mp3") || end.equals("mid") || end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {
            type = "audio/*";
        } else if (end.equals("3gp") || end.equals("mp4")) {
            type = "video/*";
        } else if (end.equals("jpg") || end.equals("gif") || end.equals("png") || end.equals("jpeg") || end.equals("bmp")) {
            type = "image/*";
        } else if (end.equals("apk")) {
            /* android.permission.INSTALL_PACKAGES */
            type = "application/vnd.android.package-archive";
        } else if (end.equals("pptx") || end.equals("ppt")) {
            type = "application/vnd.ms-powerpoint";
        } else if (end.equals("docx") || end.equals("doc")) {
            type = "application/vnd.ms-word";
        } else if (end.equals("xlsx") || end.equals("xls")) {
            type = "application/vnd.ms-excel";
        } else {
            // /*如果无法直接打开，就跳出软件列表给用户选择 */
            type = "*/*";
        }
        return type;
    }

    /**
     * @Description: 注入js函数监听image点击事件
     * @author DingLei
     * @date 2014-5-13
     */
    private void addImageClickListner() {
        // 这段js函数的功能就是，遍历所有的img节点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        mWebView.loadUrl("javascript:(function(){" + "var objs = document.getElementsByTagName(\"img\"); " + "var imgs = new Array(objs.length); " + "for(var i=0;i<objs.length;i++)  "
                + "{" + "    imgs[i] = objs[i].src;" + "    objs[i].onclick=function()  " + "    {  " + "        window.imagelistner.openImage(this.src,imgs);  " + "    }  " + "}"
                + "})()");
    }

    /**
     * @author DingLei
     * @version 1.0
     * @Description: 图片点击的JS处理类
     * @date 2014-5-13
     */
    public class ImageJavascriptInterface {
        private SEBaseActivity context;

        public ImageJavascriptInterface(SEBaseActivity context) {
            this.context = context;
        }

        public void openImage(String src, String[] imgs) {
            if (imgs != null && imgs.length > 0) {
            }
        }

    }
}
