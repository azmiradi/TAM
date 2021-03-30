package azmithabet.com.tam.model;

import java.util.List;

public class HomeResponse {
    private List<Product> whatsNew;
    private List<Product> trending;
    private int code;
    private String msg;

    public HomeResponse() { }

    public HomeResponse(List<Product> whatsNew, List<Product> trending, int code, String msg) {
        this.whatsNew = whatsNew;
        this.trending = trending;
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public List<Product> getWhatsNew() {
        return whatsNew;
    }

    public List<Product> getTrending() {
        return trending;
    }
}
