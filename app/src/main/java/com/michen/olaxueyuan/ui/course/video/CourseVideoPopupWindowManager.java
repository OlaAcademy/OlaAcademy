package com.michen.olaxueyuan.ui.course.video;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.PaintDrawable;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
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
import com.michen.olaxueyuan.common.manager.Utils;
import com.michen.olaxueyuan.download.DownloadManager;
import com.michen.olaxueyuan.download.DownloadService;
import com.michen.olaxueyuan.protocol.event.VideoPdfEvent;
import com.michen.olaxueyuan.protocol.result.CourseVideoResult;
import com.michen.olaxueyuan.ui.course.CourseVideoActivity;

import java.io.File;
import java.util.List;

/**
 * Created by mingge on 2017/6/8.
 * 视频的popupWindow
 */

public class CourseVideoPopupWindowManager {
	private static CourseVideoPopupWindowManager manager;

	public static CourseVideoPopupWindowManager getInstance() {
		if (manager == null) {
			manager = new CourseVideoPopupWindowManager();
		}
		return manager;
	}

	public void initView(CourseVideoActivity activity) {
		this.activity = activity;
	}

	private PopupWindow handOutPopup;
	private int[] location = new int[2];
	public CourseVideoActivity activity;
	private int pageNumber = 1;
	HttpUtils http = new HttpUtils();
	HttpHandler handler;

	private PDFView pdfView;
	private TextView noPdf;
	private TextView loadingText;
	private LinearLayout pdfLayout;
	private TextView sendMailText;

	public void showHandOutPop(final Context context, VideoPdfEvent videoPdf) {
		View popView = LayoutInflater.from(context).inflate(R.layout.handout_video_pop, null);
		popView.setFocusable(true);
		popView.setFocusableInTouchMode(true);
		activity.popDownLine.getLocationOnScreen(location);
		handOutPopup = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, Utils.getScreenMetrics(activity).y - location[1]);
		popView.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					if (handOutPopup.isShowing()) {
						handOutPopup.dismiss();
					}
					return true;
				}
				return false;
			}
		});
		handOutPopup.setTouchInterceptor(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE && !handOutPopup.isFocusable()) {
					//如果焦点不在popupWindow上，且点击了外面，不再往下dispatch事件：
					//不做任何响应,不 dismiss popupWindow
					return true;
				}
				//否则default，往下dispatch事件:关掉popupWindow，
				return false;
			}
		});
		pdfView = (PDFView) popView.findViewById(R.id.pdfView);
		noPdf = (TextView) popView.findViewById(R.id.no_pdf);
		loadingText = (TextView) popView.findViewById(R.id.loading_text);
		pdfLayout = (LinearLayout) popView.findViewById(R.id.pdf_layout);
		sendMailText = (TextView) popView.findViewById(R.id.send_mail_text);
		ImageView close = (ImageView) popView.findViewById(R.id.close_pop);
		close.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				handOutPopup.dismiss();
			}
		});

		if (videoPdf.type == 1) {
			name = videoPdf.name;
			if (TextUtils.isEmpty(videoPdf.url)) {
				setVisible(false, true, false);
				return;
			}
			downLoadPdf(videoPdf.url, videoPdf.id, context, loadingText);
		}
		sendMailText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(downLoadUrl)) {
					ToastUtil.showToastShort(context, "pdf文件正在下载中，请稍后");
				}
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.putExtra(Intent.EXTRA_SUBJECT, "欧拉学院学习讲义-" + name);
				intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + downLoadUrl));
				intent.setType("image");
				intent.setType("message/rfc882");
				context.startActivity(Intent.createChooser(intent, "请选择邮件发送文件"));
			}
		});

		handOutPopup.setAnimationStyle(R.style.AnimBottom);
		// 显示窗口 (设置layout在PopupWindow中显示的位置)
		handOutPopup.showAtLocation(activity.findViewById(R.id.root),
				Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
	}

	private void loadPdf(File file) {
		try {
			pdfView.fromFile(file)
					.defaultPage(pageNumber)
					.onPageChange(new OnPageChangeListener() {
						@Override
						public void onPageChanged(int page, int pageCount) {
							pageNumber = page;
						}
					})
					.swipeVertical(true)
					.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void downLoadPdf(String url, long id, final Context context, final TextView loadingText) {
		if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			ToastUtil.showToastShort(context, R.string.need_sd);
			return;
		}
		String fileName = "/" + id + ".pdf";
//        final String target = "/sdcard/OlaAcademy/" + fileName;
		final String target = context.getExternalCacheDir() + fileName;
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

	private String downLoadUrl;
	private String name;


	private List<CourseVideoResult.ResultBean.VideoListBean> list;
	private DownloadManager downloadManager;
	private PopupWindow downLoadPopup;
	private DowloadPopAdapter adapter;
	boolean isAll = false;//是否已经全选
	int selectCount = 0;


	/**
	 * 下载列表的popupWindow
	 *
	 * @param list
	 * @param title
	 */
	public void downLoadPopupWindow(final List<CourseVideoResult.ResultBean.VideoListBean> list, final String title) {
		this.list = list;
		downloadManager = DownloadService.getDownloadManager(activity);
		View popView = LayoutInflater.from(activity).inflate(R.layout.video_pop_download_view, null);
		popView.setFocusable(true);
		popView.setFocusableInTouchMode(true);
		activity.popDownLine.getLocationOnScreen(location);
		downLoadPopup = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, Utils.getScreenMetrics(activity).y - location[1]);
		downLoadPopup.setBackgroundDrawable(new PaintDrawable());
		downLoadPopup.setFocusable(true);
		Utils.setWindowBackGroundAlpha(activity, 0.4f);
		popView.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					if (downLoadPopup.isShowing()) {
						downLoadPopup.dismiss();
						Utils.setWindowBackGroundAlpha(activity, 1f);
					}
					return true;
				}
				return false;
			}
		});
		downLoadPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
			@Override
			public void onDismiss() {
				Utils.setWindowBackGroundAlpha(activity, 1f);
				isAll = false;
			}
		});
		final ImageView close = (ImageView) popView.findViewById(R.id.close);
		TextView downloadTitle = (TextView) popView.findViewById(R.id.download_title);
		final ListView downloadList = (ListView) popView.findViewById(R.id.download_list);
		final TextView downloadAll = (TextView) popView.findViewById(R.id.download_all);
		final TextView download = (TextView) popView.findViewById(R.id.download);
		adapter = new DowloadPopAdapter();
		downloadList.setAdapter(adapter);
		downloadList.setDivider(null);
		downloadTitle.setText(title);

		close.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				downLoadPopup.dismiss();
			}
		});

		downloadAll.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {//全选和反选
				if (isAll) {
					isAll = false;
					for (int i = 0; i < list.size(); i++) {
						list.get(i).setSelectedDownload(false);
					}
					downloadAll.setTextColor(activity.getResources().getColor(R.color.normal_text_color));
					downloadAll.setText(R.string.all_select);
					download.setTextColor(activity.getResources().getColor(R.color.normal_text_color));
					download.setText(R.string.download);
				} else {
					isAll = true;
					for (int i = 0; i < list.size(); i++) {
						list.get(i).setSelectedDownload(true);
					}
					downloadAll.setTextColor(activity.getResources().getColor(R.color.main_color));
					downloadAll.setText(R.string.cancel_select_all);
					download.setTextColor(activity.getResources().getColor(R.color.main_color));
					download.setText(activity.getString(R.string.download_counts, String.valueOf(list.size())));
				}
				Logger.e("list==" + list.toString());
				adapter.notifyDataSetChanged();
			}
		});
		download.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						for (int i = 0; i < list.size(); i++) {
							if (list.get(i).isSelectedDownload()) {
								try {
									selectCount++;
									String target = "/sdcard/OlaAcademy/" + list.get(i).getId() + ".mp4";
									if (TextUtils.isEmpty(list.get(i).getAddress())) {
										ToastUtil.showToastShort(activity, activity.getString(R.string.not_found_download_video_url, list.get(i).getName()));
									} else {
										downloadManager.addNewDownload(
												list.get(i).getAddress(),
												list.get(i).getName(),
												list.get(i).getPic(),
												target,
												true, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将重新下载。
												false, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
												null);
										ToastUtil.showToastLong(activity, "已加入下载列表，我的模块->我的下载,查看下载进度");
									}
								} catch (Exception e) {
									e.printStackTrace();
									Logger.e(e.getMessage());
								}
							}
						}
						if (selectCount == 0) {
							ToastUtil.showToastShort(activity, R.string.no_select_download_video);
						} else {
							selectCount = 0;
							downLoadPopup.dismiss();
						}
					}
				}
		);
		downloadList.setOnItemClickListener(
				new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						if (list.get(position).isSelectedDownload()) {
							list.get(position).setSelectedDownload(false);
						} else {
							list.get(position).setSelectedDownload(true);
						}
						int count = 0;
						for (int i = 0; i < list.size(); i++) {
							if (list.get(i).isSelectedDownload()) {
								count++;
							}
						}
						if (count == 0) {
							download.setTextColor(activity.getResources().getColor(R.color.normal_text_color));
							download.setText(R.string.download);
							downloadAll.setTextColor(activity.getResources().getColor(R.color.normal_text_color));
							downloadAll.setText(R.string.all_select);
						} else {
							download.setTextColor(activity.getResources().getColor(R.color.main_color));
							download.setText(activity.getString(R.string.download_counts, String.valueOf(count)));
							if (count == list.size()) {
								isAll = true;
								downloadAll.setTextColor(activity.getResources().getColor(R.color.main_color));
								downloadAll.setText(R.string.cancel_select_all);
							} else {
								isAll = false;
								downloadAll.setTextColor(activity.getResources().getColor(R.color.normal_text_color));
								downloadAll.setText(R.string.all_select);
							}
						}
						adapter.notifyDataSetChanged();
					}
				}

		);
		downLoadPopup.setAnimationStyle(R.style.AnimBottom);
		// 显示窗口 (设置layout在PopupWindow中显示的位置)
		downLoadPopup.showAtLocation(activity.findViewById(R.id.root),
				Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
	}

	class DowloadPopAdapter extends BaseAdapter {
		public DowloadPopAdapter() {
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = View.inflate(activity, R.layout.video_pop_download_list_item, null);
				holder.title_item_index = (TextView) convertView.findViewById(R.id.title_item_index);
				holder.title_item = (TextView) convertView.findViewById(R.id.title_item);
				holder.download_size = (TextView) convertView.findViewById(R.id.download_size);
				holder.download_img = (ImageView) convertView.findViewById(R.id.download_img);
				holder.root = convertView.findViewById(R.id.root);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.title_item.setText(list.get(position).getName());
			if (list.get(position).isSelectedDownload()) {
				holder.download_img.setSelected(true);
			} else {
				holder.download_img.setSelected(false);
			}
			holder.download_size.setText(list.get(position).getContent());
			return convertView;
		}

		class ViewHolder {
			View root;
			TextView title_item_index;
			TextView title_item;
			TextView download_size;
			ImageView download_img;
		}
	}
}
