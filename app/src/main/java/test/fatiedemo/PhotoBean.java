package test.fatiedemo;

import java.util.List;

/**
 * Created by Administrator on 2017/10/12.
 */

public class PhotoBean {

    /**
     * ok : true
     * sourceId : 3b4aca38e86a4fb9816346a152f4da16
     * filelist : [{"SIZE":"2998584","ID":"b18e82bac595489aa17a410afef91fe6","TYPE":"image/jpeg","URL":"/upLoadResource/b18e82bac595489aa17a410afef91fe6/IMG20171012171131.jpg","NAME":"IMG20171012171131.jpg"}]
     */

    private String ok;
    private String sourceId;
    private List<FilelistBean> filelist;

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public List<FilelistBean> getFilelist() {
        return filelist;
    }

    public void setFilelist(List<FilelistBean> filelist) {
        this.filelist = filelist;
    }

    public static class FilelistBean {
        /**
         * SIZE : 2998584
         * ID : b18e82bac595489aa17a410afef91fe6
         * TYPE : image/jpeg
         * URL : /upLoadResource/b18e82bac595489aa17a410afef91fe6/IMG20171012171131.jpg
         * NAME : IMG20171012171131.jpg
         */

        private String SIZE;
        private String ID;
        private String TYPE;
        private String URL;
        private String NAME;

        public String getSIZE() {
            return SIZE;
        }

        public void setSIZE(String SIZE) {
            this.SIZE = SIZE;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getTYPE() {
            return TYPE;
        }

        public void setTYPE(String TYPE) {
            this.TYPE = TYPE;
        }

        public String getURL() {
            return URL;
        }

        public void setURL(String URL) {
            this.URL = URL;
        }

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }
    }
}
