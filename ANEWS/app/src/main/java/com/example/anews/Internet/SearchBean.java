package com.example.anews.Internet;

import java.util.List;

public class SearchBean {

    /**
     * status : 0
     * msg : ok
     * result : {"keyword":"姚明",
     *           "num":12,
     *           "list":[{
     *                    "title":"姚明一家三口现身南宁，9岁女儿身高1米7受关注，叶莉穿着很朴素",
     *                    "time":"2019-12-18 17:20:19",
     *                    "src":"爱无法释怀",
     *                    "category":null,
     *                    "pic":"https://p0.ssl.qhimgs4.com/t01980d1b6142a20019.jpg",
     *                    "url":"https://tran.news.so.com/ctranscode?title=%E5%A7%9A%E6%98%8E%E4%B8%80%E5%AE%B6%E4%B8%89%E5%8F%A3%E7%8E%B0%E8%BA%AB%E5%8D%97%E5%AE%81%2C9%E5%B2%81%E5%A5%B3%E5%84%BF%E8%BA%AB%E9%AB%981%E7%B1%B37%E5%8F%97%E5%85%B3%E6%B3%A8%2C%E5%8F%B6%E8%8E%89%E7%A9%BF%E7%9D%80%E5%BE%88%E6%9C%B4%E7%B4%A0&u=https%3A%2F%2Fwww.360kuai.com%2Fpc%2F9b92ea3fee2391081%3Fcota%3D3%26kuai_so%3D1%26sign%3D360_e39369d1%26refer_scene%3Dso_4&m=439166f401e8dc03df80a2d19897e5b140a61795&q=%E5%A7%9A%E6%98%8E&360sodetail=1&src=mnews",
     *                    "weburl":"http://zm.news.so.com/1f5bf41512acc95810684edf31f6297c",
     *                    "content":"<p><img src=\"https://p0.ssl.qhimgs4.com/t01980d1b6142a20019.jpg?size=787x397\"/><br /><\/p><p><img src=\"https://p0.ssl.qhimgs4.com/t014a3d1cfb328eb951.jpg?size=691x341\"/><br /><\/p><p><img src=\"https://p0.ssl.qhimgs4.com/t01f6dd8fd787e94895.jpg?size=838x347\"/><br /><\/p><p>前几天，叶莉还曾带着姚沁蕾去深圳观看了三人篮球赛，当时两人坐在一起。虽然照片中的姚沁蕾只是坐着，但个子已经很高了。和她身边的一位女士对比，姚沁蕾也不比对方矮。从这两次的综合对比来看，保守估计，姚沁蕾的身高已经达到了1米7。<\/p><p>众所周知，姚明的身高是2米26，叶莉的身高是1米9，女儿姚沁蕾往往比同龄人高出很多，因此她的身高经常受到关注。早在3年前，就有人预测过姚沁蕾的身高，估计在1.945米\u20142.085米之间。因此，如果姚沁蕾如果身高超过2米，也不必感到意外。这个身高无论是排球还是篮球，都是不错的选择，就看姚主席如何培养了。<\/p><p>作为中国最知名的篮球运动员，姚明现在担任中国篮协，在国内外都有巨大的影响力，家产也多达几十亿。为了回报社会，姚明很早就开展公益活动，成立了姚基金。妻子叶莉则行事十分低调，无论出席什么活动，穿着都非常朴素。即便是姚基金晚宴，叶莉的穿着也很普通，并无高贵气质，这也是她的一贯作风。<\/p>","gallery":"{\"1\":\"https:\\/\\/p0.ssl.qhimgs4.com\\/t014a3d1cfb328eb951.jpg\",\"2\":\"https:\\/\\/p0.ssl.qhimgs4.com\\/t01f6dd8fd787e94895.jpg\"}",
     *                    "addtime":1576660819}]}
     */

    private int status;
    private String msg;
    private ResultBean result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * keyword : 姚明
         * num : 12
         * list : [{"title":"姚明一家三口现身南宁，9岁女儿身高1米7受关注，叶莉穿着很朴素","time":"2019-12-18 17:20:19","src":"爱无法释怀","category":null,"pic":"https://p0.ssl.qhimgs4.com/t01980d1b6142a20019.jpg","url":"https://tran.news.so.com/ctranscode?title=%E5%A7%9A%E6%98%8E%E4%B8%80%E5%AE%B6%E4%B8%89%E5%8F%A3%E7%8E%B0%E8%BA%AB%E5%8D%97%E5%AE%81%2C9%E5%B2%81%E5%A5%B3%E5%84%BF%E8%BA%AB%E9%AB%981%E7%B1%B37%E5%8F%97%E5%85%B3%E6%B3%A8%2C%E5%8F%B6%E8%8E%89%E7%A9%BF%E7%9D%80%E5%BE%88%E6%9C%B4%E7%B4%A0&u=https%3A%2F%2Fwww.360kuai.com%2Fpc%2F9b92ea3fee2391081%3Fcota%3D3%26kuai_so%3D1%26sign%3D360_e39369d1%26refer_scene%3Dso_4&m=439166f401e8dc03df80a2d19897e5b140a61795&q=%E5%A7%9A%E6%98%8E&360sodetail=1&src=mnews","weburl":"http://zm.news.so.com/1f5bf41512acc95810684edf31f6297c","content":"<p><img src=\"https://p0.ssl.qhimgs4.com/t01980d1b6142a20019.jpg?size=787x397\"/><br /><\/p><p><img src=\"https://p0.ssl.qhimgs4.com/t014a3d1cfb328eb951.jpg?size=691x341\"/><br /><\/p><p><img src=\"https://p0.ssl.qhimgs4.com/t01f6dd8fd787e94895.jpg?size=838x347\"/><br /><\/p><p>前几天，叶莉还曾带着姚沁蕾去深圳观看了三人篮球赛，当时两人坐在一起。虽然照片中的姚沁蕾只是坐着，但个子已经很高了。和她身边的一位女士对比，姚沁蕾也不比对方矮。从这两次的综合对比来看，保守估计，姚沁蕾的身高已经达到了1米7。<\/p><p>众所周知，姚明的身高是2米26，叶莉的身高是1米9，女儿姚沁蕾往往比同龄人高出很多，因此她的身高经常受到关注。早在3年前，就有人预测过姚沁蕾的身高，估计在1.945米\u20142.085米之间。因此，如果姚沁蕾如果身高超过2米，也不必感到意外。这个身高无论是排球还是篮球，都是不错的选择，就看姚主席如何培养了。<\/p><p>作为中国最知名的篮球运动员，姚明现在担任中国篮协，在国内外都有巨大的影响力，家产也多达几十亿。为了回报社会，姚明很早就开展公益活动，成立了姚基金。妻子叶莉则行事十分低调，无论出席什么活动，穿着都非常朴素。即便是姚基金晚宴，叶莉的穿着也很普通，并无高贵气质，这也是她的一贯作风。<\/p>","gallery":"{\"1\":\"https:\\/\\/p0.ssl.qhimgs4.com\\/t014a3d1cfb328eb951.jpg\",\"2\":\"https:\\/\\/p0.ssl.qhimgs4.com\\/t01f6dd8fd787e94895.jpg\"}","addtime":1576660819}]
         */

        private String keyword;
        private int num;
        private List<ListBean> list;

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * title : 姚明一家三口现身南宁，9岁女儿身高1米7受关注，叶莉穿着很朴素
             * time : 2019-12-18 17:20:19
             * src : 爱无法释怀
             * category : null
             * pic : https://p0.ssl.qhimgs4.com/t01980d1b6142a20019.jpg
             * url : https://tran.news.so.com/ctranscode?title=%E5%A7%9A%E6%98%8E%E4%B8%80%E5%AE%B6%E4%B8%89%E5%8F%A3%E7%8E%B0%E8%BA%AB%E5%8D%97%E5%AE%81%2C9%E5%B2%81%E5%A5%B3%E5%84%BF%E8%BA%AB%E9%AB%981%E7%B1%B37%E5%8F%97%E5%85%B3%E6%B3%A8%2C%E5%8F%B6%E8%8E%89%E7%A9%BF%E7%9D%80%E5%BE%88%E6%9C%B4%E7%B4%A0&u=https%3A%2F%2Fwww.360kuai.com%2Fpc%2F9b92ea3fee2391081%3Fcota%3D3%26kuai_so%3D1%26sign%3D360_e39369d1%26refer_scene%3Dso_4&m=439166f401e8dc03df80a2d19897e5b140a61795&q=%E5%A7%9A%E6%98%8E&360sodetail=1&src=mnews
             * weburl : http://zm.news.so.com/1f5bf41512acc95810684edf31f6297c
             * content : <p><img src="https://p0.ssl.qhimgs4.com/t01980d1b6142a20019.jpg?size=787x397"/><br /></p><p><img src="https://p0.ssl.qhimgs4.com/t014a3d1cfb328eb951.jpg?size=691x341"/><br /></p><p><img src="https://p0.ssl.qhimgs4.com/t01f6dd8fd787e94895.jpg?size=838x347"/><br /></p><p>前几天，叶莉还曾带着姚沁蕾去深圳观看了三人篮球赛，当时两人坐在一起。虽然照片中的姚沁蕾只是坐着，但个子已经很高了。和她身边的一位女士对比，姚沁蕾也不比对方矮。从这两次的综合对比来看，保守估计，姚沁蕾的身高已经达到了1米7。</p><p>众所周知，姚明的身高是2米26，叶莉的身高是1米9，女儿姚沁蕾往往比同龄人高出很多，因此她的身高经常受到关注。早在3年前，就有人预测过姚沁蕾的身高，估计在1.945米—2.085米之间。因此，如果姚沁蕾如果身高超过2米，也不必感到意外。这个身高无论是排球还是篮球，都是不错的选择，就看姚主席如何培养了。</p><p>作为中国最知名的篮球运动员，姚明现在担任中国篮协，在国内外都有巨大的影响力，家产也多达几十亿。为了回报社会，姚明很早就开展公益活动，成立了姚基金。妻子叶莉则行事十分低调，无论出席什么活动，穿着都非常朴素。即便是姚基金晚宴，叶莉的穿着也很普通，并无高贵气质，这也是她的一贯作风。</p>
             * gallery : {"1":"https:\/\/p0.ssl.qhimgs4.com\/t014a3d1cfb328eb951.jpg","2":"https:\/\/p0.ssl.qhimgs4.com\/t01f6dd8fd787e94895.jpg"}
             * addtime : 1576660819
             */

            private String title;
            private String time;
            private String src;
            private String pic;
            private String content;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getSrc() {
                return src;
            }

            public void setSrc(String src) {
                this.src = src;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}
