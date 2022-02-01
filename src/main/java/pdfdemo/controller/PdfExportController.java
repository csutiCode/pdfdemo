package pdfdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pdfdemo.domain.Data;
import pdfdemo.service.PDFFGeneratorService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class PdfExportController {

    @Autowired
    private PDFFGeneratorService pdfGeneratorService;
    //the get request triggers the
    /*
    @GetMapping("/pdf/generate")
    public void generatePDF (HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:mm:ss");
        String currentDateTIme = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTIme + ".pdf";

        response.setHeader(headerKey,headerValue);

        this.pdfGeneratorService.export(response);
    }
    */

    @PostMapping("/pdf/generate")
    public void generatePdf (@RequestBody Data data, HttpServletResponse response) throws IOException {


        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:mm:ss");
        String currentDateTIme = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTIme + ".pdf";

        response.setHeader(headerKey,headerValue);

        this.pdfGeneratorService.export(data, response);
    }


}
