package com.example.anews30.bean;

import java.util.List;

public class NewsGson {

    /**
     * code : 200
     * msg : success
     * newslist : [{"ctime":"2019-07-31 21:38","title":"绿地与白俄罗斯签约三年采购额5亿美元 升温第二届进博会","description":"搜狐房产","picUrl":"","url":"https://gz.focus.cn/zixun/83e225137088d8a0.html"},{"ctime":"2019-07-31 21:01","title":"携\u201c智慧硬核\u201d 美的置业与行业头部企业共建智慧生态","description":"搜狐房产","picUrl":"","url":"https://hf.focus.cn/zixun/b7ea0c54b0031be9.html"},{"ctime":"2019-07-31 21:38","title":"7月上海17盘公证摇号 最火盘当天日光均价5万 ","description":"搜狐房产","picUrl":"https://t.focus-img.cn/sh160x120sh/xf/dt/b5c384f3-2340-4866-b096-2fb6df7bb36e.JPEG","url":"https://sh.focus.cn/zixun/7ad6a5eb6158c32c.html"},{"ctime":"2019-07-31 21:12","title":"真房价,让二手房交易更透明","description":"搜狐房产","picUrl":"https://t.focus-img.cn/sh160x120sh/xf/xw/888b201b-f8f0-44c6-8777-98921900cef5.png","url":"https://cc.focus.cn/zixun/566e9a856a903d29.html"},{"ctime":"2019-07-31 21:38","title":"每经21点丨静待美联储降息,美股期货普涨;暴风集团实控人冯鑫涉嫌对非国家工作人员行贿被拘;乔家大院景区被取消旅游景区质量等级","description":"搜狐房产","picUrl":"https://t.focus-img.cn/sh160x120sh/xf/xw/9399c8c2-7d9d-4393-b72b-9f20bd4e4e0e.JPEG","url":"https://sh.focus.cn/zixun/a533529a475513fc.html"},{"ctime":"2019-07-31 21:27","title":"井喷!10宗地!一图看懂佛山8月卖地清单(亮点全剧透)","description":"搜狐房产","picUrl":"https://t.focus-img.cn/sh160x120sh/xf/xw/c38953bd-1d32-41ab-86ad-46b501029d16.JPEG","url":"https://fs.focus.cn/zixun/7e49a1fa4c8ec978.html"},{"ctime":"2019-07-31 21:38","title":"央视热评:炒房者恐怕今后永无\u201c用武之地\u201d了","description":"搜狐房产","picUrl":"https://t.focus-img.cn/sh160x120sh/zx/smp/592babec26bab0289c136727b1364b93.png","url":"https://nj.focus.cn/zixun/87ad9bad995ef789.html"},{"ctime":"2019-07-31 21:30","title":"\u201c奋斗拾光 幸福启航\u201d绿地泉集团召开改制十周年纪念活动发布会","description":"搜狐房产","picUrl":"https://t.focus-img.cn/sh160x120sh/xf/xw/9c9a6715-2064-44ac-b08b-7b42aba3ab48.gif","url":"https://jn.focus.cn/zixun/2a6034f08bcf41e4.html"},{"ctime":"2019-07-31 21:38","title":"大连发布\u201c房价限涨令\u201d:只准跌不准涨 跌幅不能超5%","description":"搜狐房产","picUrl":"https://t.focus-img.cn/sh160x120sh/xf/xw/4711fa64-5b00-491a-98f4-9686b5288d10.JPEG","url":"https://gz.focus.cn/zixun/ffa02bb037d4c002.html"},{"ctime":"2019-08-06 12:02","title":"合肥市民有望住进\u201c被动式\u201d房屋 冬天不需要开空调","description":"搜狐房产","picUrl":"https://t.focus-img.cn/sh160x120sh/xf/xw/2862fae6-cd3a-4532-85a2-3293633d818e.png","url":"https://zixun.focus.cn/6b1c925fa8cc1919.html"}]
     */

    private int code;
    private String msg;
    private List<NewslistBean> newslist;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<NewslistBean> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<NewslistBean> newslist) {
        this.newslist = newslist;
    }

    public static class NewslistBean {
        /**
         * ctime : 2019-07-31 21:38
         * title : 绿地与白俄罗斯签约三年采购额5亿美元 升温第二届进博会
         * description : 搜狐房产
         * picUrl :
         * url : https://gz.focus.cn/zixun/83e225137088d8a0.html
         */

        private String ctime;
        private String title;
        private String description;
        private String picUrl;
        private String url;

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
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

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
