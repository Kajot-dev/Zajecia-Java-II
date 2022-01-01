import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.*;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Dokument {
	private String tytul;
	private String tresc;
	
	public Dokument(String tytul, String tresc)
	{
		this.tytul = tytul;
		this.tresc = tresc;
	}

	public void saveMeToPDF(File file) {
		//delete file if exists
		if (file.exists()) file.delete();
		var pdfDocument = new Document();
		try {
			PdfWriter.getInstance(pdfDocument, new BufferedOutputStream(new FileOutputStream(file)));
			pdfDocument.open();
			pdfDocument.add(new Phrase(
					this.getTytul(),
					FontFactory.getFont(
							FontFactory.HELVETICA,
							24,
							Font.BOLD,
							Color.BLACK)
			));
			pdfDocument.add(new Paragraph(this.getTresc()));
			pdfDocument.close();
		} catch (DocumentException|FileNotFoundException e) {
			e.printStackTrace();
		}
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
