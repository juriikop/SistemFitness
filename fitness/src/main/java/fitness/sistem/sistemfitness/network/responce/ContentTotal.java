package fitness.sistem.sistemfitness.network.responce;

import java.util.ArrayList;
import java.util.List;

public class ContentTotal {
    public int typeInt;
    public String type;
    public String url;
    public String title;
    public String txt1;
    public String txt2;
    public String txt3;
    public List<String> data = new ArrayList<String>();

    public ContentTotal(int typeInt, String type, String tile, String url, String txt1, String txt2, String txt3) {
        this.typeInt = typeInt;
        this.type = type;
        this.title = tile;
        this.url = url;
        this.txt1 = txt1;
        this.txt2 = txt2;
        this.txt3 = txt3;
    }

    public void setValue(int typeInt, String type, String tile, String url, String txt1, String txt2, String txt3) {
        this.typeInt = typeInt;
        this.type = type;
        this.title = tile;
        this.url = url;
        this.txt1 = txt1;
        this.txt2 = txt2;
        this.txt3 = txt3;
    }
}
