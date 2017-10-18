package com.michen.olaxueyuan.protocol.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangfangming on 2017/9/9.
 */

public class UserPlanDetailResult implements Serializable{
	@Override
	public String toString() {
		return "UserPlanDetailResult{" +
				"message='" + message + '\'' +
				", result=" + result +
				", apicode=" + apicode +
				'}';
	}

	/**
	 * message : 成功
	 * result : {"id":15,"total":24,"day":1,"beginDate":"2017-08-25","endDate":"2017-09-17","progress":"0.48","statisticsList":[{"sequence":1,"date":"2017-08-21","progress":0},{"sequence":2,"date":"2017-08-22","progress":0},{"sequence":3,"date":"2017-08-23","progress":0},{"sequence":4,"date":"2017-08-24","progress":0},{"sequence":5,"date":"2017-08-25","progress":"0.48"},{"sequence":6,"date":"2017-08-26","progress":"0.06"},{"sequence":7,"date":"2017-08-27","progress":"0.19"}],"planList":[{"id":135,"title":"词汇总论","description":"","item":2,"commonList":[{"id":282,"name":"课时1：基础阶段英语学习计划","time":"0:17:16","url":"http://olamba.ufile.ucloud.cn/english/hj_1_1.mp4","isfree":"1","type":"1","progress":0},{"id":283,"name":"课时2：考研词汇分析","time":"0:25:41","url":"http://olamba.ufile.ucloud.cn/english/hj_1_2.mp4","isfree":"0","type":"1","progress":0},{"id":284,"name":"课时3：词汇备考的三个阶段","time":"0:03:54","url":"http://olamba.ufile.ucloud.cn/english/hj_1_3.mp4","isfree":"0","type":"1","progress":0},{"id":285,"name":"课时4：词汇记忆的高度和层次","time":"0:05:53","url":"http://olamba.ufile.ucloud.cn/english/hj_1_4.mp4","isfree":"0","type":"1","progress":0}]},{"id":136,"title":"构词洞察","description":"","item":2,"commonList":[{"id":286,"name":"课时1：洞察构词规则Ⅰ","time":"0:04:04","url":"http://olamba.ufile.ucloud.cn/english/hj_2_1.mp4","isfree":"1","type":"1","progress":0},{"id":287,"name":"课时2：洞察构词规则Ⅱ","time":"0:08:15","url":"http://olamba.ufile.ucloud.cn/english/hj_2_2.mp4","isfree":"0","type":"1","progress":0},{"id":288,"name":"课时3：洞察构词规则Ⅲ","time":"0:08:41","url":"http://olamba.ufile.ucloud.cn/english/hj_2_3.mp4","isfree":"0","type":"1","progress":0},{"id":289,"name":"课时4：洞察构词规则Ⅳ","time":"0:15:53","url":"http://olamba.ufile.ucloud.cn/english/hj_2_4.mp4","isfree":"0","type":"1","progress":0}]},{"id":177,"title":"逻辑大纲解析","description":"","item":3,"commonList":[{"id":141,"name":"逻辑大纲解析","time":"","url":"http://qz-mba.oss-cn-beijing.aliyuncs.com/luoji/plan/1-1.pdf","isfree":"1","type":"3","progress":1},{"id":1083,"name":"逻辑大纲剖析","time":"0:14:00","url":"http://olamba.ufile.ucloud.cn/xuerui2016/xs_1.mp4","isfree":"1","type":"1","progress":0}]},{"id":178,"title":"形式逻辑的本质","description":"","item":3,"commonList":[{"id":142,"name":"形式逻辑的本质","time":"","url":"http://qz-mba.oss-cn-beijing.aliyuncs.com/luoji/plan/1-2.pdf","isfree":"1","type":"3","progress":1},{"id":1084,"name":"形式逻辑基础","time":"0:08:00","url":"http://olamba.ufile.ucloud.cn/xuerui2016/xs_2.mp4","isfree":"1","type":"1","progress":0},{"id":1085,"name":"形式逻辑考点本质剖析","time":"0:16:00","url":"http://olamba.ufile.ucloud.cn/xuerui2016/xs_3.mp4","isfree":"1","type":"1","progress":0},{"id":283,"name":"知识点专项练习","time":"","url":"2602,2603,2604,2605,2606,2607,2608,2609,2610,2611","isfree":"1","type":"2","progress":"1.00"}]},{"id":217,"title":"全年备考指导","description":"","item":4,"commonList":[]},{"id":218,"title":"考试深度解析","description":"","item":4,"commonList":[]}]}
	 * apicode : 10000
	 */

	private String message;
	private ResultBean result;
	private int apicode;

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

	public static class ResultBean implements Serializable{
		@Override
		public String toString() {
			return "ResultBean{" +
					"id='" + id + '\'' +
					", total=" + total +
					", day=" + day +
					", beginDate='" + beginDate + '\'' +
					", endDate='" + endDate + '\'' +
					", progress='" + progress + '\'' +
					", statisticsList=" + statisticsList +
					", planList=" + planList +
					'}';
		}

		/**
		 * id : 15
		 * total : 24
		 * day : 1
		 * beginDate : 2017-08-25
		 * endDate : 2017-09-17
		 * progress : 0.48
		 * statisticsList : [{"sequence":1,"date":"2017-08-21","progress":0},{"sequence":2,"date":"2017-08-22","progress":0},{"sequence":3,"date":"2017-08-23","progress":0},{"sequence":4,"date":"2017-08-24","progress":0},{"sequence":5,"date":"2017-08-25","progress":"0.48"},{"sequence":6,"date":"2017-08-26","progress":"0.06"},{"sequence":7,"date":"2017-08-27","progress":"0.19"}]
		 * planList : [{"id":135,"title":"词汇总论","description":"","item":2,"commonList":[{"id":282,"name":"课时1：基础阶段英语学习计划","time":"0:17:16","url":"http://olamba.ufile.ucloud.cn/english/hj_1_1.mp4","isfree":"1","type":"1","progress":0},{"id":283,"name":"课时2：考研词汇分析","time":"0:25:41","url":"http://olamba.ufile.ucloud.cn/english/hj_1_2.mp4","isfree":"0","type":"1","progress":0},{"id":284,"name":"课时3：词汇备考的三个阶段","time":"0:03:54","url":"http://olamba.ufile.ucloud.cn/english/hj_1_3.mp4","isfree":"0","type":"1","progress":0},{"id":285,"name":"课时4：词汇记忆的高度和层次","time":"0:05:53","url":"http://olamba.ufile.ucloud.cn/english/hj_1_4.mp4","isfree":"0","type":"1","progress":0}]},{"id":136,"title":"构词洞察","description":"","item":2,"commonList":[{"id":286,"name":"课时1：洞察构词规则Ⅰ","time":"0:04:04","url":"http://olamba.ufile.ucloud.cn/english/hj_2_1.mp4","isfree":"1","type":"1","progress":0},{"id":287,"name":"课时2：洞察构词规则Ⅱ","time":"0:08:15","url":"http://olamba.ufile.ucloud.cn/english/hj_2_2.mp4","isfree":"0","type":"1","progress":0},{"id":288,"name":"课时3：洞察构词规则Ⅲ","time":"0:08:41","url":"http://olamba.ufile.ucloud.cn/english/hj_2_3.mp4","isfree":"0","type":"1","progress":0},{"id":289,"name":"课时4：洞察构词规则Ⅳ","time":"0:15:53","url":"http://olamba.ufile.ucloud.cn/english/hj_2_4.mp4","isfree":"0","type":"1","progress":0}]},{"id":177,"title":"逻辑大纲解析","description":"","item":3,"commonList":[{"id":141,"name":"逻辑大纲解析","time":"","url":"http://qz-mba.oss-cn-beijing.aliyuncs.com/luoji/plan/1-1.pdf","isfree":"1","type":"3","progress":1},{"id":1083,"name":"逻辑大纲剖析","time":"0:14:00","url":"http://olamba.ufile.ucloud.cn/xuerui2016/xs_1.mp4","isfree":"1","type":"1","progress":0}]},{"id":178,"title":"形式逻辑的本质","description":"","item":3,"commonList":[{"id":142,"name":"形式逻辑的本质","time":"","url":"http://qz-mba.oss-cn-beijing.aliyuncs.com/luoji/plan/1-2.pdf","isfree":"1","type":"3","progress":1},{"id":1084,"name":"形式逻辑基础","time":"0:08:00","url":"http://olamba.ufile.ucloud.cn/xuerui2016/xs_2.mp4","isfree":"1","type":"1","progress":0},{"id":1085,"name":"形式逻辑考点本质剖析","time":"0:16:00","url":"http://olamba.ufile.ucloud.cn/xuerui2016/xs_3.mp4","isfree":"1","type":"1","progress":0},{"id":283,"name":"知识点专项练习","time":"","url":"2602,2603,2604,2605,2606,2607,2608,2609,2610,2611","isfree":"1","type":"2","progress":"1.00"}]},{"id":217,"title":"全年备考指导","description":"","item":4,"commonList":[]},{"id":218,"title":"考试深度解析","description":"","item":4,"commonList":[]}]
		 */

		private String id;
		private int total;
		private int day;
		private String beginDate;
		private String endDate;
		private String progress;
		private List<StatisticsListBean> statisticsList;
		private List<PlanListBean> planList;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
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

		public List<PlanListBean> getPlanList() {
			return planList;
		}

		public void setPlanList(List<PlanListBean> planList) {
			this.planList = planList;
		}

		public static class StatisticsListBean {
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
			 * date : 2017-08-21
			 * progress : 0
			 */

			private int sequence;
			private String date;
			private float progress;

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

			public float getProgress() {
				return progress;
			}

			public void setProgress(float progress) {
				this.progress = progress;
			}
		}

		public static class PlanListBean {
			@Override
			public String toString() {
				return "PlanListBean{" +
						"id=" + id +
						", title='" + title + '\'' +
						", description='" + description + '\'' +
						", item=" + item +
						", commonList=" + commonList +
						'}';
			}

			/**
			 * id : 135
			 * title : 词汇总论
			 * description :
			 * item : 2
			 * commonList : [{"id":282,"name":"课时1：基础阶段英语学习计划","time":"0:17:16","url":"http://olamba.ufile.ucloud.cn/english/hj_1_1.mp4","isfree":"1","type":"1","progress":0},{"id":283,"name":"课时2：考研词汇分析","time":"0:25:41","url":"http://olamba.ufile.ucloud.cn/english/hj_1_2.mp4","isfree":"0","type":"1","progress":0},{"id":284,"name":"课时3：词汇备考的三个阶段","time":"0:03:54","url":"http://olamba.ufile.ucloud.cn/english/hj_1_3.mp4","isfree":"0","type":"1","progress":0},{"id":285,"name":"课时4：词汇记忆的高度和层次","time":"0:05:53","url":"http://olamba.ufile.ucloud.cn/english/hj_1_4.mp4","isfree":"0","type":"1","progress":0}]
			 */

			private String id;
			private String title;
			private String description;
			private String item;
			private List<CommonListBean> commonList;

			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}

			public String getTitle() {
				return title;
			}

			public void setTitle(String title) {
				this.title = title;
			}

			public String getDescription() {
				return description;
			}

			public void setDescription(String description) {
				this.description = description;
			}

			public String getItem() {
				return item;
			}

			public void setItem(String item) {
				this.item = item;
			}

			public List<CommonListBean> getCommonList() {
				return commonList;
			}

			public void setCommonList(List<CommonListBean> commonList) {
				this.commonList = commonList;
			}

			public static class CommonListBean {
				@Override
				public String toString() {
					return "CommonListBean{" +
							"id=" + id +
							", name='" + name + '\'' +
							", time='" + time + '\'' +
							", url='" + url + '\'' +
							", isfree='" + isfree + '\'' +
							", type='" + type + '\'' +
							", progress=" + progress +
							'}';
				}

				/**
				 * id : 282
				 * name : 课时1：基础阶段英语学习计划
				 * time : 0:17:16
				 * url : http://olamba.ufile.ucloud.cn/english/hj_1_1.mp4
				 * isfree : 1
				 * type : 1
				 * progress : 0
				 */

				private int id;
				private String name;
				private String time;
				private String url;
				private String isfree;
				private int type;
				private float progress;

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

				public String getTime() {
					return time;
				}

				public void setTime(String time) {
					this.time = time;
				}

				public String getUrl() {
					return url;
				}

				public void setUrl(String url) {
					this.url = url;
				}

				public String getIsfree() {
					return isfree;
				}

				public void setIsfree(String isfree) {
					this.isfree = isfree;
				}

				public int getType() {
					return type;
				}

				public void setType(int type) {
					this.type = type;
				}

				public float getProgress() {
					return progress;
				}

				public void setProgress(float progress) {
					this.progress = progress;
				}
			}
		}
	}
}
