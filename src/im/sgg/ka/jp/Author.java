package im.sgg.ka.jp;

/**
 * Created by sergiy on 06.03.16.
 * Java Programmer lessons
 * kademika.com
 */
public class Author {
    private String name;

    public Author(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void reName(String name) {
        this.name = name;
    }
}
