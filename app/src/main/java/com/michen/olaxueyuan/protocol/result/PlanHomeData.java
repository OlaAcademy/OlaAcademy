package com.michen.olaxueyuan.protocol.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangfangming on 2017/8/26.
 */

public class PlanHomeData implements Serializable {
	/**
	 * message : 成功
	 * result : {"plan":{"id":1,"content":"学习计划","beginDate":"2017-07-01","endDate":"2017-08-31","total":62,"day":42},"progress":"0","statisticsList":[{"sequence":1,"date":"2017-08-07","progress":0},{"sequence":2,"date":"2017-08-08","progress":0},{"sequence":3,"date":"2017-08-09","progress":0},{"sequence":4,"date":"2017-08-10","progress":0},{"sequence":5,"date":"2017-08-11","progress":0},{"sequence":6,"date":"2017-08-12","progress":0},{"sequence":7,"date":"2017-08-13","progress":0}],"userList":[{"id":754,"name":"微何","avator":"http://qz-picture.oss-cn-beijing.aliyuncs.com/2016/12/c1ff4c1d-e93f-448f-9f23-d225af924706.jpg"}],"collectionList":[{"videoId":662,"videoName":"课时1：因式定理","videoPic":"http://math.ufile.ucloud.com.cn/basicmath4.jpg","videoUrl":"http://olamath.ufile.ucloud.cn/gongyi/z_1_540p.mp4","courseId":185,"coursePic":"http://cospic.ufile.ucloud.com.cn/201708.png","totalTime":"102分钟","subAllNum":11,"type":1},{"videoId":763,"videoName":"课时1：完型填空","videoPic":"http://math.ufile.ucloud.com.cn/guide3.jpg","videoUrl":"http://olamba.ufile.ucloud.cn/daoxue/laojiang_1_2.mp4","courseId":191,"coursePic":"http://cospic.ufile.ucloud.com.cn/201708.png","totalTime":"33分钟","subAllNum":4,"type":1},{"videoId":831,"videoName":"2017年最新数学大纲解析","videoPic":"http://cospic.ufile.ucloud.com.cn/1.jpg","videoUrl":"http://olamba.ufile.ucloud.cn/daoxue/shuxue_360p.mp4","courseId":188,"coursePic":"http://cospic.ufile.ucloud.com.cn/201708.png","totalTime":"40分钟","subAllNum":1,"type":1},{"videoId":787,"videoName":"课时1：强化题型之路程问题","videoPic":"http://math.ufile.ucloud.com.cn/basicmath6.jpg","videoUrl":"http://olamath.ufile.ucloud.cn/2016xt/xt_2_1_480p.mp4","courseId":206,"coursePic":"http://cospic.ufile.ucloud.com.cn/201708.png","totalTime":"300分钟","subAllNum":8,"type":1},{"videoId":231,"videoName":"课时1：排序组队【1】","videoPic":"http://math.ufile.ucloud.com.cn/analysis2.png","videoUrl":"http://olamba.ufile.ucloud.cn/luoji/ywj/jc_2_3.mp4","courseId":92,"coursePic":"http://cospic.ufile.ucloud.com.cn/201702.png","totalTime":"31分钟","subAllNum":3,"type":1},{"videoId":1271,"videoName":"2018年写作总体复习规划","videoPic":"","videoUrl":"http://olamba.ufile.ucloud.cn/daoxue/2018_xiezuo.mp4","courseId":309,"coursePic":"http://cospic.ufile.ucloud.com.cn/201708.png","totalTime":"92分钟","subAllNum":1,"type":1},{"videoId":1150,"videoName":"课时4：绝对值的概念与应用","videoPic":"","videoUrl":"http://mbamath.ufile.ucloud.cn/2017jichu/jc_1_9.mp4","courseId":300,"coursePic":"http://cospic.ufile.ucloud.com.cn/201708.png","totalTime":"240分钟","subAllNum":22,"type":1},{"videoId":797,"videoName":"课时2：联言与选言命题Ⅰ","videoPic":"http://commodity.ufile.ucloud.com.cn/logic2.jpg","videoUrl":"http://olamba.ufile.ucloud.cn/goods/logic/rsz_2.mp4","courseId":17,"coursePic":"http://cospic.ufile.ucloud.com.cn/201701.jpg","totalTime":"960分钟","subAllNum":11,"type":2},{"videoId":1129,"videoName":"课时1：英语真题密训第一讲","videoPic":"","videoUrl":"http://olamba.ufile.ucloud.cn/goods/english/zhenti_1.mp4","courseId":23,"coursePic":"http://cospic.ufile.ucloud.com.cn/90.jpg","totalTime":"1200分钟","subAllNum":12,"type":2}],"recommendList":[{"id":3,"name":"写作","objectId":"","pic":"http://qz-picture.oss-cn-beijing.aliyuncs.com/banner/TIANRAN.png","type":1,"url":"https://detail.tmall.com/item.htm?id=551458132157&price=21.8&sourceType=item&suid=38dd8fd0-420b-40ff-b3b3-7c8a04d564be&ut_sk=1.VyBzak8XJ10DACaJNjYIb48I_12278902_1496629824740.Copy.1&un=a9ddc5c0b925b0d88a195baaa22d8f6a&share_crt_v=1&cpp=1&shareurl=true&spm=a313p.22.1mm.42868104651&short_name=h.ihxxNz&app=chrome"},{"id":4,"name":"逻辑","objectId":"","pic":"http://qz-picture.oss-cn-beijing.aliyuncs.com/banner/tushu.jpg","type":1,"url":"https://weidian.com/?userid=1226047158&wfr=wx_profile&from=groupmessage&isappinstalled=0"}]}
	 * apicode : 10000
	 */

	private String message;
	private ResultBean result;
	private int apicode;

	@Override
	public String toString() {
		return "PlanHomeData{" +
				"message='" + message + '\'' +
				", result=" + result +
				", apicode=" + apicode +
				'}';
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ResultBean getResult() {
		return result;
	}

	public void setResult(ResultBean result) {
		this.result = result;
	}

	public int getApicode() {
		return apicode;
	}

	public void setApicode(int apicode) {
		this.apicode = apicode;
	}

	public static class ResultBean implements Serializable {
		@Override
		public String toString() {
			return "ResultBean{" +
					"plan=" + plan +
					", progress='" + progress + '\'' +
					", statisticsList=" + statisticsList +
					", userList=" + userList +
					", collectionList=" + collectionList +
					", recommendList=" + recommendList +
					'}';
		}

		/**
		 * plan : {"id":1,"content":"学习计划","beginDate":"2017-07-01","endDate":"2017-08-31","total":62,"day":42}
		 * progress : 0
		 * statisticsList : [{"sequence":1,"date":"2017-08-07","progress":0},{"sequence":2,"date":"2017-08-08","progress":0},{"sequence":3,"date":"2017-08-09","progress":0},{"sequence":4,"date":"2017-08-10","progress":0},{"sequence":5,"date":"2017-08-11","progress":0},{"sequence":6,"date":"2017-08-12","progress":0},{"sequence":7,"date":"2017-08-13","progress":0}]
		 * userList : [{"id":754,"name":"微何","avator":"http://qz-picture.oss-cn-beijing.aliyuncs.com/2016/12/c1ff4c1d-e93f-448f-9f23-d225af924706.jpg"}]
		 * collectionList : [{"videoId":662,"videoName":"课时1：因式定理","videoPic":"http://math.ufile.ucloud.com.cn/basicmath4.jpg","videoUrl":"http://olamath.ufile.ucloud.cn/gongyi/z_1_540p.mp4","courseId":185,"coursePic":"http://cospic.ufile.ucloud.com.cn/201708.png","totalTime":"102分钟","subAllNum":11,"type":1},{"videoId":763,"videoName":"课时1：完型填空","videoPic":"http://math.ufile.ucloud.com.cn/guide3.jpg","videoUrl":"http://olamba.ufile.ucloud.cn/daoxue/laojiang_1_2.mp4","courseId":191,"coursePic":"http://cospic.ufile.ucloud.com.cn/201708.png","totalTime":"33分钟","subAllNum":4,"type":1},{"videoId":831,"videoName":"2017年最新数学大纲解析","videoPic":"http://cospic.ufile.ucloud.com.cn/1.jpg","videoUrl":"http://olamba.ufile.ucloud.cn/daoxue/shuxue_360p.mp4","courseId":188,"coursePic":"http://cospic.ufile.ucloud.com.cn/201708.png","totalTime":"40分钟","subAllNum":1,"type":1},{"videoId":787,"videoName":"课时1：强化题型之路程问题","videoPic":"http://math.ufile.ucloud.com.cn/basicmath6.jpg","videoUrl":"http://olamath.ufile.ucloud.cn/2016xt/xt_2_1_480p.mp4","courseId":206,"coursePic":"http://cospic.ufile.ucloud.com.cn/201708.png","totalTime":"300分钟","subAllNum":8,"type":1},{"videoId":231,"videoName":"课时1：排序组队【1】","videoPic":"http://math.ufile.ucloud.com.cn/analysis2.png","videoUrl":"http://olamba.ufile.ucloud.cn/luoji/ywj/jc_2_3.mp4","courseId":92,"coursePic":"http://cospic.ufile.ucloud.com.cn/201702.png","totalTime":"31分钟","subAllNum":3,"type":1},{"videoId":1271,"videoName":"2018年写作总体复习规划","videoPic":"","videoUrl":"http://olamba.ufile.ucloud.cn/daoxue/2018_xiezuo.mp4","courseId":309,"coursePic":"http://cospic.ufile.ucloud.com.cn/201708.png","totalTime":"92分钟","subAllNum":1,"type":1},{"videoId":1150,"videoName":"课时4：绝对值的概念与应用","videoPic":"","videoUrl":"http://mbamath.ufile.ucloud.cn/2017jichu/jc_1_9.mp4","courseId":300,"coursePic":"http://cospic.ufile.ucloud.com.cn/201708.png","totalTime":"240分钟","subAllNum":22,"type":1},{"videoId":797,"videoName":"课时2：联言与选言命题Ⅰ","videoPic":"http://commodity.ufile.ucloud.com.cn/logic2.jpg","videoUrl":"http://olamba.ufile.ucloud.cn/goods/logic/rsz_2.mp4","courseId":17,"coursePic":"http://cospic.ufile.ucloud.com.cn/201701.jpg","totalTime":"960分钟","subAllNum":11,"type":2},{"videoId":1129,"videoName":"课时1：英语真题密训第一讲","videoPic":"","videoUrl":"http://olamba.ufile.ucloud.cn/goods/english/zhenti_1.mp4","courseId":23,"coursePic":"http://cospic.ufile.ucloud.com.cn/90.jpg","totalTime":"1200分钟","subAllNum":12,"type":2}]
		 * recommendList : [{"id":3,"name":"写作","objectId":"","pic":"http://qz-picture.oss-cn-beijing.aliyuncs.com/banner/TIANRAN.png","type":1,"url":"https://detail.tmall.com/item.htm?id=551458132157&price=21.8&sourceType=item&suid=38dd8fd0-420b-40ff-b3b3-7c8a04d564be&ut_sk=1.VyBzak8XJ10DACaJNjYIb48I_12278902_1496629824740.Copy.1&un=a9ddc5c0b925b0d88a195baaa22d8f6a&share_crt_v=1&cpp=1&shareurl=true&spm=a313p.22.1mm.42868104651&short_name=h.ihxxNz&app=chrome"},{"id":4,"name":"逻辑","objectId":"","pic":"http://qz-picture.oss-cn-beijing.aliyuncs.com/banner/tushu.jpg","type":1,"url":"https://weidian.com/?userid=1226047158&wfr=wx_profile&from=groupmessage&isappinstalled=0"}]
		 */

		private PlanBean plan;
		private String progress;
		private List<StatisticsListBean> statisticsList;
		private List<UserListBean> userList;
		private List<CollectionListBean> collectionList;
		private List<RecommendListBean> recommendList;

		public PlanBean getPlan() {
			return plan;
		}

		public void setPlan(PlanBean plan) {
			this.plan = plan;
		}

		public String getProgress() {
			return progress;
		}

		public void setProgress(String progress) {
			this.progress = progress;
		}

		public List<StatisticsListBean> getStatisticsList() {
			return statisticsList;
		}

		public void setStatisticsList(List<StatisticsListBean> statisticsList) {
			this.statisticsList = statisticsList;
		}

		public List<UserListBean> getUserList() {
			return userList;
		}

		public void setUserList(List<UserListBean> userList) {
			this.userList = userList;
		}

		public List<CollectionListBean> getCollectionList() {
			return collectionList;
		}

		public void setCollectionList(List<CollectionListBean> collectionList) {
			this.collectionList = collectionList;
		}

		public List<RecommendListBean> getRecommendList() {
			return recommendList;
		}

		public void setRecommendList(List<RecommendListBean> recommendList) {
			this.recommendList = recommendList;
		}

		public static class PlanBean implements Serializable {
			@Override
			public String toString() {
				return "PlanBean{" +
						"id='" + id + '\'' +
						", content='" + content + '\'' +
						", beginDate='" + beginDate + '\'' +
						", endDate='" + endDate + '\'' +
						", total=" + total +
						", day=" + day +
						", progress=" + progress +
						", pointCount=" + pointCount +
						'}';
			}

			/**
			 * id : 1
			 * content : 学习计划
			 * beginDate : 2017-07-01
			 * endDate : 2017-08-31
			 * total : 62
			 * day : 42
			 */

			private String id;
			private String content;
			private String beginDate;
			private String endDate;
			private int total;
			private int day;
			private float progress;
			private int pointCount;

			public float getProgress() {
				return progress;
			}

			public void setProgress(float progress) {
				this.progress = progress;
			}

			public int getPointCount() {
				return pointCount;
			}

			public void setPointCount(int pointCount) {
				this.pointCount = pointCount;
			}

			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}

			public String getContent() {
				return content;
			}

			public void setContent(String content) {
				this.content = content;
			}

			public String getBeginDate() {
				return beginDate;
			}

			public void setBeginDate(String beginDate) {
				this.beginDate = beginDate;
			}

			public String getEndDate() {
				return endDate;
			}

			public void setEndDate(String endDate) {
				this.endDate = endDate;
			}

			public int getTotal() {
				return total;
			}

			public void setTotal(int total) {
				this.total = total;
			}

			public int getDay() {
				return day;
			}

			public void setDay(int day) {
				this.day = day;
			}
		}

		public static class StatisticsListBean implements Serializable {
			@Override
			public String toString() {
				return "StatisticsListBean{" +
						"sequence=" + sequence +
						", date='" + date + '\'' +
						", progress=" + progress +
						'}';
			}

			/**
			 * sequence : 1
			 * date : 2017-08-07
			 * progress : 0
			 */

			private int sequence;
			private String date;
			private String progress;

			public int getSequence() {
				return sequence;
			}

			public void setSequence(int sequence) {
				this.sequence = sequence;
			}

			public String getDate() {
				return date;
			}

			public void setDate(String date) {
				this.date = date;
			}

			public String getProgress() {
				return progress;
			}

			public void setProgress(String progress) {
				this.progress = progress;
			}
		}

		public static class UserListBean implements Serializable {
			@Override
			public String toString() {
				return "UserListBean{" +
						"id=" + id +
						", name='" + name + '\'' +
						", avator='" + avator + '\'' +
						'}';
			}

			/**
			 * id : 754
			 * name : 微何
			 * avator : http://qz-picture.oss-cn-beijing.aliyuncs.com/2016/12/c1ff4c1d-e93f-448f-9f23-d225af924706.jpg
			 */

			private int id;
			private String name;
			private String avator;

			public int getId() {
				return id;
			}

			public void setId(int id) {
				this.id = id;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public String getAvator() {
				return avator;
			}

			public void setAvator(String avator) {
				this.avator = avator;
			}
		}

		public static class CollectionListBean implements Serializable {
			@Override
			public String toString() {
				return "CollectionListBean{" +
						"videoId=" + videoId +
						", videoName='" + videoName + '\'' +
						", videoPic='" + videoPic + '\'' +
						", videoUrl='" + videoUrl + '\'' +
						", courseId=" + courseId +
						", coursePic='" + coursePic + '\'' +
						", totalTime='" + totalTime + '\'' +
						", subAllNum=" + subAllNum +
						", type=" + type +
						'}';
			}

			/**
			 * videoId : 662
			 * videoName : 课时1：因式定理
			 * videoPic : http://math.ufile.ucloud.com.cn/basicmath4.jpg
			 * videoUrl : http://olamath.ufile.ucloud.cn/gongyi/z_1_540p.mp4
			 * courseId : 185
			 * coursePic : http://cospic.ufile.ucloud.com.cn/201708.png
			 * totalTime : 102分钟
			 * subAllNum : 11
			 * type : 1
			 */

			private int videoId;
			private String videoName;
			private String videoPic;
			private String videoUrl;
			private int courseId;
			private String coursePic;
			private String totalTime;
			private int subAllNum;
			private int type;

			public int getVideoId() {
				return videoId;
			}

			public void setVideoId(int videoId) {
				this.videoId = videoId;
			}

			public String getVideoName() {
				return videoName;
			}

			public void setVideoName(String videoName) {
				this.videoName = videoName;
			}

			public String getVideoPic() {
				return videoPic;
			}

			public void setVideoPic(String videoPic) {
				this.videoPic = videoPic;
			}

			public String getVideoUrl() {
				return videoUrl;
			}

			public void setVideoUrl(String videoUrl) {
				this.videoUrl = videoUrl;
			}

			public int getCourseId() {
				return courseId;
			}

			public void setCourseId(int courseId) {
				this.courseId = courseId;
			}

			public String getCoursePic() {
				return coursePic;
			}

			public void setCoursePic(String coursePic) {
				this.coursePic = coursePic;
			}

			public String getTotalTime() {
				return totalTime;
			}

			public void setTotalTime(String totalTime) {
				this.totalTime = totalTime;
			}

			public int getSubAllNum() {
				return subAllNum;
			}

			public void setSubAllNum(int subAllNum) {
				this.subAllNum = subAllNum;
			}

			public int getType() {
				return type;
			}

			public void setType(int type) {
				this.type = type;
			}
		}

		public static class RecommendListBean implements Serializable {
			@Override
			public String toString() {
				return "RecommendListBean{" +
						"id='" + id + '\'' +
						", introduction='" + introduction + '\'' +
						", name='" + name + '\'' +
						", objectId='" + objectId + '\'' +
						", pic='" + pic + '\'' +
						", position=" + position +
						", type=" + type +
						", url='" + url + '\'' +
						'}';
			}

			/**
			 * id : 3
			 * name : 写作
			 * objectId :
			 * pic : http://qz-picture.oss-cn-beijing.aliyuncs.com/banner/TIANRAN.png
			 * type : 1
			 * url : https://detail.tmall.com/item.htm?id=551458132157&price=21.8&sourceType=item&suid=38dd8fd0-420b-40ff-b3b3-7c8a04d564be&ut_sk=1.VyBzak8XJ10DACaJNjYIb48I_12278902_1496629824740.Copy.1&un=a9ddc5c0b925b0d88a195baaa22d8f6a&share_crt_v=1&cpp=1&shareurl=true&spm=a313p.22.1mm.42868104651&short_name=h.ihxxNz&app=chrome
			 */

			private String id;
			private String introduction;
			private String name;
			private String objectId;
			private String pic;
			private int position;
			private int type;
			private String url;

			public String getIntroduction() {
				return introduction;
			}

			public void setIntroduction(String introduction) {
				this.introduction = introduction;
			}

			public int getPosition() {
				return position;
			}

			public void setPosition(int position) {
				this.position = position;
			}

			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public String getObjectId() {
				return objectId;
			}

			public void setObjectId(String objectId) {
				this.objectId = objectId;
			}

			public String getPic() {
				return pic;
			}

			public void setPic(String pic) {
				this.pic = pic;
			}

			public int getType() {
				return type;
			}

			public void setType(int type) {
				this.type = type;
			}

			public String getUrl() {
				return url;
			}

			public void setUrl(String url) {
				this.url = url;
			}
		}
	}
}
