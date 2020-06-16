package Networking;

import java.io.Serializable;

public class Response implements Serializable {

    private ResponseType rt;
    private Object obj;

    public ResponseType getRt() {
        return rt;
    }

    public void setRt(ResponseType rt) {
        this.rt = rt;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public static class Builder{

        private Response req = new Response();

        public Builder type(ResponseType rtype){
            req.setRt(rtype);
            return this;
        }

        public Builder object(Object object){
            req.setObj(object);
            return this;
        }

        public Response build(){
            return req;
        }

    }

}
