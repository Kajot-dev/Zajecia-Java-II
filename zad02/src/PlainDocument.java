import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.*;
import java.io.*;
import java.util.List;

public class PlainDocument {
    private String tytul;
    private String tresc;

    public PlainDocument(String tytul, String tresc) {
        this.tytul = tytul;
        this.tresc = tresc;
    }

    public static PlainDocument deserialise(List<String> data) {
        return new PlainDocument(data.get(0), data.get(1));
    }

    @Override
    public String toString() {
        return "PlainDocument{" +
                "title='" + tytul + '\'' +
                ", content='" + tresc + '\'' +
                '}';
    }

    public String serialise() {
        var sb = new StringBuilder();
        sb.append(this.tytul).append('\n').append(this.tresc);
        return sb.toString();
    }

    public void saveMeToTXT(File file) throws IOException {
        try (
                var buff = new BufferedWriter(new FileWriter(file))
        ) {
            buff.write(this.serialise());
        }
    }

    public void saveMeToPDF(File file) throws DocumentException, FileNotFoundException {
        //delete file if exists
        if (file.exists()) file.delete();
        var pdfDocument = new Document();
        PdfWriter.getInstance(pdfDocument, new BufferedOutputStream(new FileOutputStream(file)));
        pdfDocument.open();
        pdfDocument.addTitle(this.tytul);
        pdfDocument.add(new Phrase(
                this.tytul,
                FontFactory.getFont(
                        FontFactory.HELVETICA,
                        24,
                        Font.BOLD,
                        Color.BLACK)
        ));
        pdfDocument.add(new Paragraph(this.tresc));
        pdfDocument.close();
    }

    public String getTitle() {
        return tytul;
    }

    public void setTitle(String tytul) {
        this.tytul = tytul;
    }

    public String getContent() {
        return tresc;
    }

    public void setContent(String tresc) {
        this.tresc = tresc;
    }
}
