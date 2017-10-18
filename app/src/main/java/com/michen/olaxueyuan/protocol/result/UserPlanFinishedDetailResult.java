package com.michen.olaxueyuan.protocol.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangfangming on 2017/9/23.
 */

public class UserPlanFinishedDetailResult implements Serializable{
	@Override
	public String toString() {
		return "UserPlanFinishedDetailResult{" +
				"message='" + message + '\'' +
				", result=" + result +
				", apicode=" + apicode +
				'}';
	}

	/**
	 * message : 成功
	 * result : {"statisticsList":[{"id":4,"planId":"1","plandate":"2017-08-01","progress":"1","userId":754},{"id":5,"planId":"1","plandate":"2017-08-02","progress":"0.94","userId":754},{"id":6,"planId":"11","plandate":"2017-08-05","progress":"0.00","userId":754},{"id":7,"planId":"11","plandate":"2017-08-09","progress":"1.44","userId":754},{"id":8,"planId":"11","plandate":"2017-08-11","progress":"0.05","userId":754},{"id":9,"planId":"11","plandate":"2017-08-04","progress":"0.09","userId":754},{"id":10,"planId":"12","plandate":"2017-08-27","progress":"0.00","userId":754},{"id":11,"planId":"12","plandate":"2017-08-15","progress":"0.00","userId":754},{"id":12,"planId":"12","plandate":"2017-08-16","progress":"0.21","userId":754},{"id":13,"planId":"12","plandate":"2017-08-12","progress":"0.12","userId":754},{"id":14,"planId":"12","plandate":"2017-08-17","progress":"0.53","userId":754},{"id":15,"planId":"11","plandate":"2017-08-08","progress":"0.18","userId":754},{"id":16,"planId":"12","plandate":"2017-08-14","progress":"0.00","userId":754},{"id":17,"planId":"12","plandate":"2017-08-18","progress":"0.04","userId":754},{"id":18,"planId":"12","plandate":"2017-08-19","progress":"0.10","userId":754}],"dailyList":[{"detailNumber":"1","studyDate":"2017-08-05","subjectNumber":"0","userId":754},{"detailNumber":"2","studyDate":"2017-08-09","subjectNumber":"3","userId":754},{"detailNumber":"1","studyDate":"2017-08-10","subjectNumber":"10","userId":754},{"detailNumber":"1","studyDate":"2017-08-12","subjectNumber":"0","userId":754},{"detailNumber":"1","studyDate":"2017-08-15","subjectNumber":"0","userId":754},{"detailNumber":"2","studyDate":"2017-08-16","subjectNumber":"6","userId":754},{"detailNumber":"2","studyDate":"2017-08-17","subjectNumber":"17","userId":754},{"detailNumber":"3","studyDate":"2017-08-18","subjectNumber":"0","userId":754},{"detailNumber":"2","studyDate":"2017-08-19","subjectNumber":"2","userId":754},{"detailNumber":"2","studyDate":"2017-08-20","subjectNumber":"2","userId":754}]}
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
		private List<StatisticsListBean> statisticsList;
		private List<DailyListBean> dailyList;

		@Override
		public String toString() {
			return "ResultBean{" +
					"statisticsList=" + statisticsList +
					", dailyList=" + dailyList +
					'}';
		}

		public List<StatisticsListBean> getStatisticsList() {
			return statisticsList;
		}

		public void setStatisticsList(List<StatisticsListBean> statisticsList) {
			this.statisticsList = statisticsList;
		}

		public List<DailyListBean> getDailyList() {
			return dailyList;
		}

		public void setDailyList(List<DailyListBean> dailyList) {
			this.dailyList = dailyList;
		}

		public static class StatisticsListBean implements Serializable{
			/**
			 * id : 4
			 * planId : 1
			 * plandate : 2017-08-01
			 * progress : 1
			 * userId : 754
			 */

			private int id;
			private String planId;
			private String plandate;
			private String progress;
			private int userId;

			public int getId() {
				return id;
			}

			public void setId(int id) {
				this.id = id;
			}

			public String getPlanId() {
				return planId;
			}

			public void setPlanId(String planId) {
				this.planId = planId;
			}

			public String getPlandate() {
				return plandate;
			}

			public void setPlandate(String plandate) {
				this.plandate = plandate;
			}

			public String getProgress() {
				return progress;
			}

			public void setProgress(String progress) {
				this.progress = progress;
			}

			public int getUserId() {
				return userId;
			}

			public void setUserId(int userId) {
				this.userId = userId;
			}
		}

		public static class DailyListBean implements Serializable{
			/**
			 * detailNumber : 1
			 * studyDate : 2017-08-05
			 * subjectNumber : 0
			 * userId : 754
			 */

			private String detailNumber;
			private String studyDate;
			private String subjectNumber;
			private int userId;

			public String getDetailNumber() {
				return detailNumber;
			}

			public void setDetailNumber(String detailNumber) {
				this.detailNumber = detailNumber;
			}

			public String getStudyDate() {
				return studyDate;
			}

			public void setStudyDate(String studyDate) {
				this.studyDate = studyDate;
			}

			public String getSubjectNumber() {
				return subjectNumber;
			}

			public void setSubjectNumber(String subjectNumber) {
				this.subjectNumber = subjectNumber;
			}

			public int getUserId() {
				return userId;
			}

			public void setUserId(int userId) {
				this.userId = userId;
			}
		}
	}
}
