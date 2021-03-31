package azmithabet.com.tam.model;

import java.util.List;

public class HomeBaseResponse extends BaseResponse {
    private List<Product> whatsNew;
    private List<Product> trending;


    public HomeBaseResponse() { }

    public HomeBaseResponse(List<Product> whatsNew, List<Product> trending, int code, String msg) {
        super(code, msg);
        this.whatsNew = whatsNew;
        this.trending = trending;
    }

    public List<Product> getWhatsNew() {
        return whatsNew;
    }

    public List<Product> getTrending() {
        return trending;
    }
}
