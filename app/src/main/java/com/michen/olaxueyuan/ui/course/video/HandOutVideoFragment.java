package com.michen.olaxueyuan.ui.course.video;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnPageChangeListener;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.manager.Logger;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.event.VideoPdfEvent;
import com.michen.olaxueyuan.ui.course.CourseVideoActivity;
import com.michen.olaxueyuan.ui.me.activity.BaseFragment;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by mingge on 2016/9/19.
 */
public class HandOutVideoFragment extends BaseFragment implements OnPageChangeListener {
    View view;
    @Bind(R.id.pdfView)
    PDFView pdfView;
    @Bind(R.id.no_pdf)
    TextView noPdf;
    @Bind(R.id.loading_text)
    TextView loadingText;
    @Bind(R.id.pdf_layout)
    LinearLayout pdfLayout;
    @Bind(R.id.send_mail_text)
    TextView sendMailText;

    private int pageNumber = 1;
    HttpUtils http = new HttpUtils();
    HttpHandler handler;
    private int nowPosition = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.handout_video_fragment, null);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initView();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        ButterKnife.unbind(this);
        if (handler != null) {
            handler.cancel();
        }
    }

    private void initView() {

    }

    private void loadPdf(File file) {
        try {
            pdfView.fromFile(file)
                    .defaultPage(pageNumber)
                    .onPageChange(this)
                    .swipeVertical(true)
                    .load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * {@link CourseVideoActivity#setDownloadPdfPosition(int)}
     * {@link com.michen.olaxueyuan.ui.course.SystemVideoActivity#setDownloadPdfPosition(int)}
     */
    public void onEventMainThread(VideoPdfEvent videoPdf) {
        if (videoPdf.type == 1 && nowPosition != videoPdf.position) {
            nowPosition = videoPdf.position;
            name = videoPdf.name;
            if (TextUtils.isEmpty(videoPdf.url)) {
                setVisible(false, true, false);
                return;
            }
            downLoadPdf(videoPdf.url, videoPdf.id);
        }
    }

    private void downLoadPdf(String url, long id) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            ToastUtil.showToastShort(getActivity(), R.string.need_sd);
            return;
        }
        String fileName = "/" + id + ".pdf";
//        final String target = "/sdcard/OlaAcademy/" + fileName;
        final String target = getActivity().getExternalCacheDir() + fileName;
        final File file = new File(target);
        if (file.exists()) {
            setVisible(false, false, true);
            downLoadUrl = target;
            loadPdf(file);
            return;
        }
        handler = http.download(url, target,
                true, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
                true, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
                new RequestCallBack<File>() {
                    @Override
                    public void onStart() {
                        setVisible(true, false, false);
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        Logger.e("current==" + current);
                        Logger.e("(int) (current / total * 100)==" + (int) (current / total * 100));
//                        loadingText.setText(getString(R.string.loading_text) + (int) (current / total * 100) + "%");
                        loadingText.setText("下载中..." + (int) (current * 100 / total) + "%");
                    }

                    @Override
                    public void onSuccess(ResponseInfo<File> responseInfo) {
                        setVisible(false, false, true);
                        loadPdf(file);
                        downLoadUrl = target;
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        setVisible(false, true, false);
                    }
                });
    }

    private void setVisible(boolean loadingTextFlag, boolean noPdfFlag, boolean pdfViewFlag) {
        loadingText.setVisibility(loadingTextFlag ? View.VISIBLE : View.GONE);
        noPdf.setVisibility(noPdfFlag ? View.VISIBLE : View.GONE);
        pdfLayout.setVisibility(pdfViewFlag ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
    }

    private String downLoadUrl;
    private String name;

    @OnClick({R.id.send_mail_text})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_mail_text:
                if (TextUtils.isEmpty(downLoadUrl)) {
                    ToastUtil.showToastShort(getActivity(), "pdf文件正在下载中，请稍后");
                }
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_SUBJECT, "欧拉学院学习讲义-" + name);
//                intent.putExtra(Intent.EXTRA_TEXT, name);
                intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + downLoadUrl));
                intent.setType("image");
                intent.setType("message/rfc882");
                startActivity(Intent.createChooser(intent, "请选择邮件发送文件"));
                break;
        }
    }
}
