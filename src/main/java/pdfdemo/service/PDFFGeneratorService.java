package pdfdemo.service;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import pdfdemo.domain.Data;
import pdfdemo.domain.Item;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class PDFFGeneratorService {


    public void export(Data data, HttpServletResponse response) throws IOException {

        Document document = new Document(PageSize.A4);
        //get the document and write it into this outputstream
        PdfWriter.getInstance(document, response.getOutputStream());

        //open it to work with it
        document.open();
        //get the font
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA);
        fontTitle.setSize(18);

        Paragraph creator = new Paragraph(data.getCreator(), fontTitle);
        creator.setAlignment(Paragraph.ALIGN_CENTER);

        Font fontParagraph = FontFactory.getFont(FontFactory.COURIER);
        fontParagraph.setSize(12);

        Paragraph profession = new Paragraph(data.getProfession(), fontParagraph);
        profession.setAlignment(Paragraph.ALIGN_LEFT);
        profession.getSpacingAfter();

        document.add(creator);
        document.add(profession);

        Font font = new Font(Font.HELVETICA, 12, Font.BOLDITALIC);
                PdfPTable table = new PdfPTable(5);
                table.setWidthPercentage(100);
                // setting column widths
                table.setWidths(new float[] {6.0f, 6.0f, 6.0f, 6.0f, 6.0f});
                PdfPCell cell = new PdfPCell();
                // table headers
                cell.setPhrase(new Phrase("Termék megnevezése", font));
                table.addCell(cell);
                cell.setPhrase(new Phrase("Mértékegység", font));
                table.addCell(cell);
                cell.setPhrase(new Phrase("Mennyiség", font));
                table.addCell(cell);
                cell.setPhrase(new Phrase("Egységár", font));
                table.addCell(cell);
                cell.setPhrase(new Phrase("Érték", font));
                table.addCell(cell);

        Double totalSum = 0D;

        for (Item item : data.getItems()) {
            table.addCell(item.getName());
            table.addCell(item.getUnit());
            table.addCell(String.valueOf(item.getQuantity()));
            table.addCell(String.valueOf(item.getUnitPrice()));
            Double sum = item.getQuantity() * item.getUnitPrice();
            table.addCell(String.valueOf(sum));
            totalSum += sum;
        }

        Paragraph totalSumParagraph = new Paragraph("Teljes anyagdíj: " + totalSum, fontParagraph);
        totalSumParagraph.setAlignment(Paragraph.ALIGN_RIGHT);

        Paragraph priceOfWorkParagraph = new Paragraph("Teljes munkadíj: " + data.getPriceOfWork(), fontParagraph);
        priceOfWorkParagraph.setAlignment(Paragraph.ALIGN_RIGHT);

        document.add(table);
        document.add(totalSumParagraph);
        document.add(priceOfWorkParagraph);
        document.close();
        System.out.println("PDF using OpenPDF created successfully");
    }
    /*
    public void export(HttpServletResponse response) throws IOException {

        Document document = new Document(PageSize.A4);
        //get the document and write it into this outputstream
        PdfWriter.getInstance(document, response.getOutputStream());

        //open it to work with it
        document.open();
        //get the font
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA);
        fontTitle.setSize(18);

        Paragraph paragraph = new Paragraph("This is a title", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);

        Font fontParagraph = FontFactory.getFont(FontFactory.COURIER);
        fontParagraph.setSize(12);

        Paragraph paragraph2 = new Paragraph("This is a paragraph", fontParagraph);
        paragraph2.setAlignment(Paragraph.ALIGN_LEFT);

        document.add(paragraph);
        document.add(paragraph2);
        document.close();
    }

     */
}
