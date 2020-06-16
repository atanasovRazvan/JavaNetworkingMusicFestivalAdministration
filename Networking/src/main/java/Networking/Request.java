package Networking;

import java.io.Serializable;

public class Request implements Serializable {

    private RequestType rt;
    private Object obj;

    public RequestType getRt() {
        return rt;
    }

    public void setRt(RequestType rt) {
        this.rt = rt;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public static class Builder{

        private Request req = new Request();

        public Builder type(RequestType rtype){
            req.setRt(rtype);
            return this;
        }

        public Builder object(Object object){
            req.setObj(object);
            return this;
        }

        public Request build(){
            return req;
        }

    }

}
