import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.io.*;
import java.util.*;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.DefaultFontMapper;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;


public class Runner {

    private static File docDir;

    static {
        Runner.docDir = new File("docs");
        if (!docDir.exists() || !docDir.isDirectory()) {
            docDir.mkdir();
        }
    }

    public static void main(String[] args) {
        ArrayList<Dokument> lista = new ArrayList<>();
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String tmp = "";

        System.out.println();
        System.out.println("What do you want to do?");
        System.out.println("create - utworz dokument");
        System.out.println("show - wyswietl dokumenty");
        System.out.println("save - zapis do pliku tekstowego");
        System.out.println("load - odczyt z pliku tekstowego");
        System.out.println("exit - wyjscie z aplikacji");
        System.out.println("sample - przyklad dokumentu PDF");
        System.out.println("sizes - rozmiary dokumentu PDF");
        System.out.println("margins - marginesy dokumentu PDF");
        System.out.println("metadata - metadane dokumentu PDF");
        System.out.println("invoice - faktura w dokumencie PDF");
        System.out.println("graphics - grafika w dokumencie PDF");
        boolean go = true;
        while (go) {
            try {
                // read the user input
                tmp = input.readLine();

                // act accordingly

                switch (tmp) {
                    case "saveTo1PDF":
                        saveTo1PDF(lista);
                        break;
                    case "saveToManyPDFs":
                        saveToManyPDFs(lista);
                        break;
                    case "create":
                        create(lista, input);
                        break;
                    case "show":
                        show(lista);
                        break;
                    case "save":
                        save(lista);
                        break;
                    case "load":
                        load(lista);
                        break;
                    case "exit":
                        go = false;
                        break;
                    case "sample":
                        pdfSample();
                        break;
                    case "sizes":
                        pdfSizes();
                        break;
                    case "margins":
                        pdfMargins();
                        break;
                    case "metadata":
                        pdfMetadata();
                        break;
                    case "invoice":
                        pdfInvoice();
                        break;
                    case "graphics":
                        pdfGraphics();
                        break;
                    default:
                        System.out.println("Invalid command!");
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void create(ArrayList<Dokument> lista, BufferedReader input) throws IOException {
        System.out.println("Enter title: ");

        String title = input.readLine();

        System.out.println("Enter content: ");

        String content = input.readLine();
        Dokument mojDokumentTekstowy = new Dokument(title, content);
        lista.add(mojDokumentTekstowy);
        System.out.println("The document has been created and added to the list");
    }

    private static void show(ArrayList<Dokument> lista) throws IOException {
        for (int i = 0; i < lista.size(); i++) {
            System.out.print("Dokument\n");
            System.out.print(lista.get(i).getTytul() + "\n");
            System.out.print(lista.get(i).getTresc() + "\n");
        }
    }

    private static void save(ArrayList<Dokument> lista) throws IOException {
        File file = new File(Runner.docDir, "dokumenty.txt");
        try (
                FileWriter fwriter = new FileWriter(file);
                BufferedWriter bwriter = new BufferedWriter(fwriter);
        ) {
            for (int i = 0; i < lista.size(); i++) {
                bwriter.write("Dokument\n");
                bwriter.write(lista.get(i).getTytul() + "\n");
                bwriter.write(lista.get(i).getTresc() + "\n");
            }
            System.out.println("Documents have been saved to the file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void load(ArrayList<Dokument> lista) throws IOException {
        File file = new File(Runner.docDir, "dokumenty.txt");
        try (
                FileReader freader = new FileReader(file);
                BufferedReader breader = new BufferedReader(freader);
        ) {
            lista.clear();
            String line;
            while ((line = breader.readLine()) != null) {
                if (line.equals("Dokument")) {
                    String tytul = breader.readLine();
                    String tresc = breader.readLine();
                    Dokument mojDokumentTekstowy = new Dokument(tytul, tresc);
                    lista.add(mojDokumentTekstowy);
                }
            }
            System.out.println("Documents have been loaded from the file");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    private static void pdfInvoice() {
        try {
            Document document = new Document();
            File file = new File(Runner.docDir, "Faktura.pdf");
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            // create content
            Phrase fTitle = new Phrase(
                    "Faktura nr 1234/IOZ/2009",
                    FontFactory.getFont(
                            FontFactory.HELVETICA,
                            24,
                            Font.BOLD,
                            Color.RED));
            Image logo = Image.getInstance("src/logo_placeholder.gif");
            Phrase fBuyer = new Phrase(
                    "Kupiec sp. z o.o.\nJan Kowalski\nul. Miodna 33\n12-345 Gdziekolwiek",
                    FontFactory.getFont(
                            FontFactory.HELVETICA,
                            14,
                            Font.NORMAL,
                            Color.BLACK));
            PdfPTable fTable = new PdfPTable(3);
            PdfPCell cell = new PdfPCell(
                    new Paragraph(
                            "Pozycje na fakturze",
                            FontFactory.getFont(
                                    FontFactory.HELVETICA,
                                    12,
                                    Font.NORMAL,
                                    Color.BLACK)));
            cell.setColspan(3);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setMinimumHeight(20);
            fTable.addCell(cell);
            fTable.addCell("Lorem ipsum dolor sit amet");
            fTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            fTable.addCell("1234,56");
            fTable.addCell("22%");
            fTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
            fTable.addCell("Aliquam euismod est suscipit mauris");
            fTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            fTable.addCell("345,88");
            fTable.addCell("7%");

            Paragraph fTotal = new Paragraph("1580,44",
                    FontFactory.getFont(
                            FontFactory.HELVETICA,
                            18,
                            Font.BOLDITALIC,
                            Color.BLUE));
            fTotal.setAlignment(Element.ALIGN_RIGHT);

            // put content into the document
            document.add(fTitle);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            document.add(fBuyer);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);

            int[] szerokosci = {400, 75, 75};
            fTable.setWidths(szerokosci);
            fTable.setTotalWidth(550);
            fTable.setLockedWidth(true);
            document.add(fTable);

            document.add(fTotal);

            logo.setAbsolutePosition(400, 650);
            document.add(logo);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void pdfSample() {
        try {
            Document document = new Document();
            File file = new File(Runner.docDir, "HelloWorld1.pdf");
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            document.add(new Paragraph("Hello World"));
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void pdfSizes() {
        try {
            Document document = new Document();
            File file = new File(Runner.docDir, "HelloWorld2.pdf");
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            document.add(new Paragraph("The default PageSize is DIN A4."));
            document.setPageSize(PageSize.A3);
            document.newPage();
            document.add(new Paragraph("This PageSize is DIN A3."));
            document.setPageSize(PageSize.A2);
            document.newPage();
            document.add(new Paragraph("This PageSize is DIN A2."));
            document.setPageSize(PageSize.A1);
            document.newPage();
            document.add(new Paragraph("This PageSize is DIN A1."));
            document.setPageSize(PageSize.A0);
            document.newPage();
            document.add(new Paragraph("This PageSize is DIN A0."));
            document.setPageSize(PageSize.A5);
            document.newPage();
            document.add(new Paragraph("This PageSize is DIN A5."));
            document.setPageSize(PageSize.A6);
            document.newPage();
            document.add(new Paragraph("This PageSize is DIN A6."));
            document.setPageSize(PageSize.A7);
            document.newPage();
            document.add(new Paragraph("This PageSize is DIN A7."));
            document.setPageSize(PageSize.A8);
            document.newPage();
            document.add(new Paragraph("This PageSize is DIN A8."));
            document.setPageSize(PageSize.LETTER);
            document.newPage();
            document.add(new Paragraph("This PageSize is LETTER."));
            document.add(new Paragraph("A lot of other standard PageSizes are available."));
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void pdfMargins() {
        try {
            Document document = new Document(PageSize.A5, 100f, 100f, 100f, 100f);
            File file = new File(Runner.docDir, "HelloWorld3.pdf");
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            document.add(new Paragraph("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas porttitor auctor ipsum. Ut et nibh. Praesent facilisis quam non est. Donec massa. In accumsan nunc nec metus pharetra dapibus. In nunc. Quisque commodo, elit id fermentum adipiscing, turpis dui ornare tortor, eu interdum metus nulla vitae dolor. Morbi adipiscing, nibh sed luctus feugiat, libero mi mattis sapien, vel tristique nisl metus id lorem. Cras nunc tellus, tempor quis, ultrices sodales, pretium sit amet, est. Donec rhoncus tempus sapien. Aliquam sagittis feugiat arcu. Aenean pulvinar ultricies nunc. Mauris rhoncus, pede ac dapibus ornare, augue ipsum varius ipsum, ut ornare tellus erat quis est. Aliquam metus tellus, vestibulum quis, porta non, aliquam ut, pede. Phasellus lobortis nulla eget sem. Aliquam bibendum lectus non orci."));
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void pdfMetadata() {
        try {
            Document document = new Document();
            File file = new File(Runner.docDir, "HelloWorld4.pdf");
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            document.addTitle("Hello World example with metadata");
            document.addAuthor("Adam Wójtowicz");
            document.addSubject("This example explains how to add metadata.");
            document.addKeywords("iText, Hello World, metadata");
            document.addCreator("My program using iText");
            document.add(new Paragraph("Hello World with metadata"));
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void saveTo1PDF(List<Dokument> lista) {
        var file = new File(Runner.docDir, "dokumenty.pdf");
        var saveDocument = new Document();
        try {
            PdfWriter.getInstance(saveDocument, new BufferedOutputStream(new FileOutputStream(file)));
            saveDocument.open();
            for (var doc : lista) {
                saveDocument.add(new Paragraph("Tytul: " + doc.getTytul()));
                saveDocument.add(new Paragraph("Tresc: " + doc.getTresc()));
                saveDocument.add(Chunk.NEWLINE);
            }
            saveDocument.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Saved all documents to a single pdf!");
    }

    private static void saveToManyPDFs(List<Dokument> lista) {
        for (int i = 0; i < lista.size(); i++) {
            var doc = lista.get(i);
            var file = new File(Runner.docDir, (i + 1) + "_" + doc.getTytul() + ".pdf");
            doc.saveMeToPDF(file);
        }
        System.out.println("Saved documents to separate pdf files!");
    }

    private static void pdfGraphics() {
        try {
            Document document = new Document();
            File file = new File(Runner.docDir, "Graphics.pdf");
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            // we create a fontMapper and read all the fonts in the font directory
            DefaultFontMapper mapper = new DefaultFontMapper();
            FontFactory.registerDirectories();
            mapper.insertDirectory("c:\\windows\\fonts");

            // we create a template and a Graphics2D object that corresponds with it
            int w = 150;
            int h = 150;
            PdfContentByte cb = writer.getDirectContent();
            PdfTemplate tp = cb.createTemplate(w, h);
            Graphics2D g2 = tp.createGraphics(w, h, mapper);
            tp.setWidth(w);
            tp.setHeight(h);
            double ew = w / 2d;
            double eh = h / 2d;
            Ellipse2D.Double circle, oval, leaf, stem;
            Area circ, ov, leaf1, leaf2, st1, st2;
            circle = new Ellipse2D.Double();
            oval = new Ellipse2D.Double();
            leaf = new Ellipse2D.Double();
            stem = new Ellipse2D.Double();
            g2.setColor(Color.green);

            // Creates the first leaf by filling the intersection of two Area objects created from an ellipse.
            leaf.setFrame(ew - 16, eh - 29, 15.0, 15.0);
            leaf1 = new Area(leaf);
            leaf.setFrame(ew - 14, eh - 47, 30.0, 30.0);
            leaf2 = new Area(leaf);
            leaf1.intersect(leaf2);
            g2.fill(leaf1);

            // Creates the second leaf.
            leaf.setFrame(ew + 1, eh - 29, 15.0, 15.0);
            leaf1 = new Area(leaf);
            leaf2.intersect(leaf1);
            g2.fill(leaf2);

            g2.setColor(Color.black);

            // Creates the stem by filling the Area resulting from the subtraction of two Area objects created from an ellipse.
            stem.setFrame(ew, eh - 42, 40.0, 40.0);
            st1 = new Area(stem);
            stem.setFrame(ew + 3, eh - 47, 50.0, 50.0);
            st2 = new Area(stem);
            st1.subtract(st2);
            g2.fill(st1);

            g2.setColor(Color.yellow);

            // Creates the pear itself by filling the Area resulting from the union of two Area objects created by two different ellipses.
            circle.setFrame(ew - 25, eh, 50.0, 50.0);
            oval.setFrame(ew - 19, eh - 20, 40.0, 70.0);
            circ = new Area(circle);
            ov = new Area(oval);
            circ.add(ov);
            g2.fill(circ);

            g2.setColor(Color.black);
            java.awt.Font thisFont = new java.awt.Font("Times New Roman", java.awt.Font.PLAIN, 18);
            g2.setFont(thisFont);
            String pear = "Pear";
            FontMetrics metrics = g2.getFontMetrics();
            int width = metrics.stringWidth(pear);
            g2.drawString(pear, (w - width) / 2, 20);
            g2.dispose();
            cb.addTemplate(tp, 50, 600);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
