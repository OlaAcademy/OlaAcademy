package com.michen.olaxueyuan.protocol.result;

import java.util.List;

/**
 * Created by wangfangming on 2017/8/31.
 */

public class PlanConditionResult {
	/**
	 * message : 成功
	 * result : [{"type":1,"category":"数学","most":134,"least":44},{"type":2,"category":"英语","most":42,"least":14},{"type":3,"category":"逻辑","most":39,"least":13},{"type":4,"category":"写作","most":14,"least":4}]
	 * apicode : 10000
	 */

	private String message;
	private int apicode;
	private List<ResultBean> result;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getApicode() {
		return apicode;
	}

	public void setApicode(int apicode) {
		this.apicode = apicode;
	}

	public List<ResultBean> getResult() {
		return result;
	}

	public void setResult(List<ResultBean> result) {
		this.result = result;
	}

	public static class ResultBean {
		/**
		 * type : 1
		 * category : 数学
		 * most : 134
		 * least : 44
		 */

		private int type;
		private String category;
		private int most;
		private int least;

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

		public String getCategory() {
			return category;
		}

		public void setCategory(String category) {
			this.category = category;
		}

		public int getMost() {
			return most;
		}

		public void setMost(int most) {
			this.most = most;
		}

		public int getLeast() {
			return least;
		}

		public void setLeast(int least) {
			this.least = least;
		}
	}
}
