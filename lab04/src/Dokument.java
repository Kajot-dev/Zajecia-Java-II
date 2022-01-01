public class Dokument {
    private String tytul;
    private String tresc;

    public Dokument(String tytul, String tresc)
    {
        this.tytul = tytul;
        this.tresc = tresc;
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
