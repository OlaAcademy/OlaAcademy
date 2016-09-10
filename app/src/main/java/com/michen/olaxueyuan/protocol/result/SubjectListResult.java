package com.michen.olaxueyuan.protocol.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mingge on 2016/8/31.
 */
public class SubjectListResult implements Serializable {
    /**
     * message : 成功
     * result : [{"id":36,"article":null,"question":"某家庭在一年总支出中，子女教育支出与生活资料支出的比为3:8，文化娱乐支出与子女教育支出的比为1:2，已知文化娱乐支出占家庭总支出的10.5%，则生活资料支出占家庭总支出的（   ）","type":null,"degree":null,"hint":"统一比例法，以教育支出为基准，教育：生活=3:8=6:16，文化：教育=1:2=3:6;文化：教育：生活=3:6:16．文化占总支出的10.5%，设生活为x，则$\\frac{10.5%}{x}=\\frac{3}{16}$.","allcount":1,"rightcount":0,"avgtime":0,"pic":null,"hintpic":null,"videourl":"","optionList":[{"id":176,"sid":null,"type":null,"content":"40%","isanswer":"0"},{"id":177,"sid":null,"type":null,"content":"42%","isanswer":"0"},{"id":178,"sid":null,"type":null,"content":"48%","isanswer":"0"},{"id":179,"sid":null,"type":null,"content":"56%","isanswer":"1"},{"id":180,"sid":null,"type":null,"content":"64%","isanswer":"0"}]},{"id":37,"article":null,"question":"有一批同规格的正方形瓷砖，用它们铺满整个正方形区域时剩余180块，将此正方形区域的边长增加一块瓷砖的长度时，还需要增加21块瓷砖才能铺满，该批瓷砖共有（   ）","type":null,"degree":null,"hint":"设原来正方形边长为$y$，${{\\left( y+1 \\right)}^{2}}={{y}^{2}}+180+21$，得$y=100$，则该批瓷砖共有：${{y}^{2}}+180={{100}^{2}}+180=10180$","allcount":1,"rightcount":0,"avgtime":0,"pic":null,"hintpic":null,"videourl":"","optionList":[{"id":181,"sid":null,"type":null,"content":"9981块","isanswer":"0"},{"id":182,"sid":null,"type":null,"content":"10000块","isanswer":"0"},{"id":183,"sid":null,"type":null,"content":"10180块","isanswer":"1"},{"id":184,"sid":null,"type":null,"content":"10201块","isanswer":"0"},{"id":185,"sid":null,"type":null,"content":"10222块","isanswer":"0"}]}]
     * apicode : 10000
     */

    private String message;
    private int apicode;
    /**
     * id : 36
     * article : null
     * question : 某家庭在一年总支出中，子女教育支出与生活资料支出的比为3:8，文化娱乐支出与子女教育支出的比为1:2，已知文化娱乐支出占家庭总支出的10.5%，则生活资料支出占家庭总支出的（   ）
     * type : null
     * degree : null
     * hint : 统一比例法，以教育支出为基准，教育：生活=3:8=6:16，文化：教育=1:2=3:6;文化：教育：生活=3:6:16．文化占总支出的10.5%，设生活为x，则$\frac{10.5%}{x}=\frac{3}{16}$.
     * allcount : 1
     * rightcount : 0
     * avgtime : 0
     * pic : null
     * hintpic : null
     * videourl :
     * optionList : [{"id":176,"sid":null,"type":null,"content":"40%","isanswer":"0"},{"id":177,"sid":null,"type":null,"content":"42%","isanswer":"0"},{"id":178,"sid":null,"type":null,"content":"48%","isanswer":"0"},{"id":179,"sid":null,"type":null,"content":"56%","isanswer":"1"},{"id":180,"sid":null,"type":null,"content":"64%","isanswer":"0"}]
     */

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

    public static class ResultBean implements Serializable{
        private int id;
        private Object article;
        private String question;
        private Object type;
        private Object degree;
        private String hint;
        private int allcount;
        private int rightcount;
        private int avgtime;
        private Object pic;
        private Object hintpic;
        private String videourl;
        /**
         * id : 176
         * sid : null
         * type : null
         * content : 40%
         * isanswer : 0
         */

        private List<OptionListBean> optionList;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getArticle() {
            return article;
        }

        public void setArticle(Object article) {
            this.article = article;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }

        public Object getDegree() {
            return degree;
        }

        public void setDegree(Object degree) {
            this.degree = degree;
        }

        public String getHint() {
            return hint;
        }

        public void setHint(String hint) {
            this.hint = hint;
        }

        public int getAllcount() {
            return allcount;
        }

        public void setAllcount(int allcount) {
            this.allcount = allcount;
        }

        public int getRightcount() {
            return rightcount;
        }

        public void setRightcount(int rightcount) {
            this.rightcount = rightcount;
        }

        public int getAvgtime() {
            return avgtime;
        }

        public void setAvgtime(int avgtime) {
            this.avgtime = avgtime;
        }

        public Object getPic() {
            return pic;
        }

        public void setPic(Object pic) {
            this.pic = pic;
        }

        public Object getHintpic() {
            return hintpic;
        }

        public void setHintpic(Object hintpic) {
            this.hintpic = hintpic;
        }

        public String getVideourl() {
            return videourl;
        }

        public void setVideourl(String videourl) {
            this.videourl = videourl;
        }

        public List<OptionListBean> getOptionList() {
            return optionList;
        }

        public void setOptionList(List<OptionListBean> optionList) {
            this.optionList = optionList;
        }

        public static class OptionListBean implements Serializable{
            private int id;
            private Object sid;
            private Object type;
            private String content;
            private String isanswer;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public Object getSid() {
                return sid;
            }

            public void setSid(Object sid) {
                this.sid = sid;
            }

            public Object getType() {
                return type;
            }

            public void setType(Object type) {
                this.type = type;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getIsanswer() {
                return isanswer;
            }

            public void setIsanswer(String isanswer) {
                this.isanswer = isanswer;
            }
        }
    }
}
