import java.util.List;

public class Dokument {
    private String tytul;
    private String tresc;

    public Dokument(String tytul, String tresc)
    {
        this.tytul = tytul;
        this.tresc = tresc;
    }

    public static Dokument fromBuffer(List<String> data) {
        return new Dokument(data.get(0), data.get(1));
    }

    public String save() {
        return this.tytul + '\n' + this.tresc;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }
    public String getTytul() {
        return tytul;
    }
    public void setTresc(String tresc) {
        this.tresc = tresc;
    }
    public String getTresc() {
        return tresc;
    }
}
