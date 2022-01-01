/**
 * Ten projekt zawiera rozwiązania wszystkich podpunktow zadania domowego (1, 2, 3)
 * @author Jakub Jaruszewski
 * Projekt oryginalnie tworzony w InteliJ IDEA i importowany do Eclipse
 */

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.*;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class Runner {

    //Directory for saving csv files
    private static final File SAVE_DIR = new File("documents");
    //This just should print related error messages, nothing complicated
    protected static ErrorHandler errorHandler = (current, e) -> {
        Runner.printYellow("Something went wrong!");
        Runner.printYellow(e.getMessage());
        e.printStackTrace();
    };

    static {
        if (!SAVE_DIR.exists() && !SAVE_DIR.mkdir()) {
            System.err.println("Could not create documents folder!");
            System.exit(-1);
        }
    }

    private final HashMap<String, Product> productMap = new HashMap<>();
    private final HashMap<String, PlainDocument> documentMap = new HashMap<>();
    //This object is responsible for displaying menu
    private final OptionRunner options = new OptionRunner();

    public Runner() {

        //we have to define local variable referencing current Runner
        //because `this` keyword inside an Option implementation would reference to that Option and not to the Runner
        var self = this;

        this.options.setDefaultHandler(Runner.errorHandler);

        this.options.defineOption("Create a document", () -> {
            final String title = OptionRunner.askString("Enter title", s -> !self.documentMap.containsKey(s), "This document already exists!");
            final String content = OptionRunner.askString("Enter content");
            var doc = new PlainDocument(title, content);
            self.documentMap.put(title, doc);
        });

        this.options.defineOption("Show documents", new Option() {
            @Override
            public void run() {
                for (var doc : self.documentMap.values()) {
                    System.out.println(doc);
                }
            }

            @Override
            public boolean accept() {
                return !self.documentMap.isEmpty();
            }
        });

        this.options.defineOption("Save documents", new Option() {
            @Override
            public void run() throws IOException{
                var file = new File(Runner.SAVE_DIR, "plain_documents.txt");
                try (
                        var writer = new BufferedWriter(new FileWriter(file))
                ) {
                    for (var doc : self.documentMap.values()) {
                        writer.write(doc.serialise());
                        writer.write('\n');
                    }
                    Runner.printGreen("Saved documents!");
                }
            }

            @Override
            public boolean accept() {
                return !self.documentMap.isEmpty();
            }
        });

        this.options.defineOption("Load documents", new Option() {
            private final File saveFile = new File(Runner.SAVE_DIR, "plain_documents.txt");
            @Override
            public void run() throws IOException{
                try (
                        var stream = new BufferedInputStream(new FileInputStream(saveFile));
                        var sc = new Scanner(stream)
                ) {
                    var buff = new ArrayList<String>();
                    while (sc.hasNextLine()) {
                        var line = sc.nextLine();
                        if (line.isBlank()) continue;
                        buff.add(line);
                        if (buff.size() == 2) {
                            var doc = PlainDocument.deserialise(buff);
                            self.documentMap.put(doc.getTitle(), doc);
                            buff.clear();
                        }
                    }
                    Runner.printGreen("Loaded documents!");
                }
            }

            @Override
            public boolean accept() {
                return saveFile.exists();
            }
        });

        this.options.defineOption("Create product", () -> {
            final String name = OptionRunner.askString("Enter name", s -> !self.documentMap.containsKey(s), "This document already exists!");
            final float price = OptionRunner.askFloat(0, Float.MAX_VALUE, "Enter price");
            var prod = new Product(name, price);
            self.productMap.put(name, prod);
            Runner.printGreen("Product created!");
        });

        this.options.defineOption("Show products", new Option() {
            @Override
            public void run() {
                for (var prod : self.productMap.values()) {
                    System.out.println(prod);
                }
            }

            @Override
            public boolean accept() {
                return !self.productMap.isEmpty();
            }
        });

        this.options.defineOption("Save products", new Option() {
            @Override
            public void run() throws IOException{
                var file = new File(Runner.SAVE_DIR, "products.txt");
                try (
                        var writer = new BufferedWriter(new FileWriter(file))
                ) {
                    for (var prod : self.productMap.values()) {
                        writer.write(prod.serialise());
                        writer.write('\n');
                    }
                    Runner.printGreen("Saved products!");
                }
            }

            @Override
            public boolean accept() {
                return !self.productMap.isEmpty();
            }
        });

        this.options.defineOption("Load products", new Option() {
            private final File saveFile = new File(Runner.SAVE_DIR, "products.txt");
            @Override
            public void run() throws IOException {
                try (
                        var stream = new BufferedInputStream(new FileInputStream(saveFile));
                        var sc = new Scanner(stream)
                ) {
                    var buff = new ArrayList<String>();
                    while (sc.hasNextLine()) {
                        var line = sc.nextLine();
                        if (line.isBlank()) continue;
                        buff.add(line);
                        if (buff.size() == 2) {
                            var prod = Product.deserialise(buff);
                            self.productMap.put(prod.getName(), prod);
                            buff.clear();
                        }
                    }
                    Runner.printGreen("Loaded products!");
                }
            }

            @Override
            public boolean accept() {
                return saveFile.exists();
            }
        });

        this.options.defineOption("Sample PDF", () -> {
            Document doc = new Document();
            File file = new File(Runner.SAVE_DIR, "Sample.pdf");
            PdfWriter.getInstance(doc, new FileOutputStream(file));
            doc.open();
            doc.add(new Paragraph("Hello World"));
            doc.close();
            Runner.printGreen("Generated sample PDF!");
        });


        this.options.defineOption("Sample PDF sizes", () -> {
            Document doc = new Document();
            File file = new File(Runner.SAVE_DIR, "SampleSizes.pdf");
            PdfWriter.getInstance(doc, new FileOutputStream(file));
            doc.open();
            doc.add(new Paragraph("The default PageSize is DIN A4."));
            doc.setPageSize(PageSize.A3);
            doc.newPage();
            doc.add(new Paragraph("This PageSize is DIN A3."));
            doc.setPageSize(PageSize.A2);
            doc.newPage();
            doc.add(new Paragraph("This PageSize is DIN A2."));
            doc.setPageSize(PageSize.A1);
            doc.newPage();
            doc.add(new Paragraph("This PageSize is DIN A1."));
            doc.setPageSize(PageSize.A0);
            doc.newPage();
            doc.add(new Paragraph("This PageSize is DIN A0."));
            doc.setPageSize(PageSize.A5);
            doc.newPage();
            doc.add(new Paragraph("This PageSize is DIN A5."));
            doc.setPageSize(PageSize.A6);
            doc.newPage();
            doc.add(new Paragraph("This PageSize is DIN A6."));
            doc.setPageSize(PageSize.A7);
            doc.newPage();
            doc.add(new Paragraph("This PageSize is DIN A7."));
            doc.setPageSize(PageSize.A8);
            doc.newPage();
            doc.add(new Paragraph("This PageSize is DIN A8."));
            doc.setPageSize(PageSize.LETTER);
            doc.newPage();
            doc.add(new Paragraph("This PageSize is LETTER."));
            doc.add(new Paragraph("A lot of other standard PageSizes are available."));
            doc.close();
            Runner.printGreen("Generated sample sizes PDF!");
        });

        this.options.defineOption("Sample PDF margins", () -> {
            Document doc = new Document(PageSize.A5, 100f, 100f, 100f, 100f);
            File file = new File(Runner.SAVE_DIR, "SampleMargins.pdf");
            PdfWriter.getInstance(doc, new FileOutputStream(file));
            doc.open();
            doc.add(new Paragraph("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas porttitor auctor ipsum. Ut et nibh. Praesent facilisis quam non est. Donec massa. In accumsan nunc nec metus pharetra dapibus. In nunc. Quisque commodo, elit id fermentum adipiscing, turpis dui ornare tortor, eu interdum metus nulla vitae dolor. Morbi adipiscing, nibh sed luctus feugiat, libero mi mattis sapien, vel tristique nisl metus id lorem. Cras nunc tellus, tempor quis, ultrices sodales, pretium sit amet, est. Donec rhoncus tempus sapien. Aliquam sagittis feugiat arcu. Aenean pulvinar ultricies nunc. Mauris rhoncus, pede ac dapibus ornare, augue ipsum varius ipsum, ut ornare tellus erat quis est. Aliquam metus tellus, vestibulum quis, porta non, aliquam ut, pede. Phasellus lobortis nulla eget sem. Aliquam bibendum lectus non orci."));
            doc.close();
            Runner.printGreen("Generated sample margins PDF!");
        });

        this.options.defineOption("Sample PDF metadata", () -> {
            Document doc = new Document();
            File file = new File(Runner.SAVE_DIR, "SampleMetadata.pdf");
            PdfWriter.getInstance(doc, new FileOutputStream(file));
            doc.open();
            doc.addTitle("Hello World example with metadata");
            doc.addAuthor("Adam Wójtowicz");
            doc.addSubject("This example explains how to add metadata.");
            doc.addKeywords("iText, Hello World, metadata");
            doc.addCreator("My program using iText");
            doc.add(new Paragraph("Hello World with metadata"));
            doc.close();
        });

        this.options.defineOption("Sample PDF graphics", () -> {
            Document doc = new Document();
            File file = new File(Runner.SAVE_DIR, "Graphics.pdf");
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(file));
            doc.open();

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
            doc.close();
            Runner.printGreen("Generated sample PDF graphics!");
        });

        this.options.defineOption("Save documents to 1 PDF", new Option() {
            @Override
            public void run() throws DocumentException, FileNotFoundException {
                var file = new File(Runner.SAVE_DIR, "documents.pdf");
                var saveDocument = new Document();
                PdfWriter.getInstance(saveDocument, new BufferedOutputStream(new FileOutputStream(file)));
                saveDocument.open();
                for (var doc : self.documentMap.values()) {
                    saveDocument.add(new Paragraph("Title: " + doc.getTitle()));
                    saveDocument.add(new Paragraph("Contents: " + doc.getContent()));
                    saveDocument.add(Chunk.NEWLINE);
                }
                saveDocument.close();
                Runner.printGreen("Saved all documents into single PDF!");
            }

            @Override
            public boolean accept() {
                return !self.documentMap.isEmpty();
            }
        });

        this.options.defineOption("Save documents to many PDFs", new Option() {
            @Override
            public void run() throws DocumentException, FileNotFoundException{
                int i = 0;

                for (var doc : self.documentMap.values()) {
                    var file = new File(Runner.SAVE_DIR, (i + 1) + "_" + doc.getTitle() + ".pdf");
                    doc.saveMeToPDF(file);
                    i++;
                }
                Runner.printGreen("Saved documents to separate pdf files!");
            }

            @Override
            public boolean accept() {
                return !self.documentMap.isEmpty();
            }
        });

        this.options.defineOption("Save documents to many TXTs", new Option() {
            @Override
            public void run() throws IOException {
                int i = 0;

                for (var doc : self.documentMap.values()) {
                    var file = new File(Runner.SAVE_DIR, (i + 1) + "_" + doc.getTitle() + ".txt");
                    doc.saveMeToTXT(file);
                    i++;
                }

                Runner.printGreen("Saved documents to separate txt files!");
            }

            @Override
            public boolean accept() {
                return !self.documentMap.isEmpty();
            }
        });

        this.options.defineOption("Generate invoice", new Option() {
            @Override
            public void run() throws Exception {
                Document doc = new Document();
                File file = new File(Runner.SAVE_DIR, "Invoice.pdf");
                PdfWriter.getInstance(doc, new FileOutputStream(file));
                doc.open();
                // create content
                Phrase fTitle = new Phrase(
                        "Faktura nr 1234/IOZ/2009",
                        FontFactory.getFont(
                                FontFactory.HELVETICA,
                                24,
                                Font.BOLD,
                                Color.RED));
                Image logo = Image.getInstance("assets/logo_placeholder.gif");
                Phrase fBuyer = new Phrase(
                        "Kupiec sp. z o.o.\nJan Kowalski\nul. Miodna 33\n12-345 Gdziekolwiek",
                        FontFactory.getFont(
                                FontFactory.HELVETICA,
                                14,
                                Font.NORMAL,
                                Color.BLACK));
                PdfPTable fTable = new PdfPTable(2);
                PdfPCell cell = new PdfPCell(
                        new Paragraph(
                                "Pozycje na fakturze",
                                FontFactory.getFont(
                                        FontFactory.HELVETICA,
                                        12,
                                        Font.NORMAL,
                                        Color.BLACK)));
                cell.setColspan(2);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setMinimumHeight(20);
                fTable.addCell(cell);
                //use class, not primitive type
                Float sum = 0f;
                for (var prod : self.productMap.values()) {
                    Float price = prod.getPrice();
                    sum += price;
                    fTable.addCell(prod.getName());
                    fTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
                    fTable.addCell(price.toString());
                    fTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                }

                Paragraph fTotal = new Paragraph(sum.toString(),
                        FontFactory.getFont(
                                FontFactory.HELVETICA,
                                18,
                                Font.BOLDITALIC,
                                Color.BLUE));
                fTotal.setAlignment(Element.ALIGN_RIGHT);

                // put content into the document
                doc.add(fTitle);
                doc.add(Chunk.NEWLINE);
                doc.add(Chunk.NEWLINE);
                doc.add(fBuyer);
                doc.add(Chunk.NEWLINE);
                doc.add(Chunk.NEWLINE);
                doc.add(Chunk.NEWLINE);

                int[] widths = {475, 75};
                fTable.setWidths(widths);
                fTable.setTotalWidth(550);
                fTable.setLockedWidth(true);
                doc.add(fTable);

                doc.add(fTotal);

                logo.setAbsolutePosition(400, 650);
                doc.add(logo);

                doc.close();
                Runner.printGreen("Generated invoice!");
            }

            @Override
            public boolean accept() {
                return !self.productMap.isEmpty();
            }
        });

    }

    public static void main(String[] args) {
        var player = new Runner();
        player.enable();
    }

    protected static void printYellow(Object o) {
        System.out.println("\u001B[33m" + o + "\u001B[0m");
    }

    protected static void printGreen(Object o) {
        System.out.println("\u001B[32m" + o + "\u001B[0m");
    }

    public void enable() {
        this.options.cyclingMenu();
    }
}
