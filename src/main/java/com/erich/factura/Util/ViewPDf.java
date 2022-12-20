package com.erich.factura.Util;

import com.erich.factura.Entity.Invoice;
import com.erich.factura.Entity.InvoiceDetail;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfImage;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import java.awt.*;
import java.util.Map;

@Component("invoice/ver")
public class ViewPDf extends AbstractPdfView {


    @Override
    protected void buildPdfDocument(Map<String, Object> model,
                                    Document document,
                                    PdfWriter writer,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        Invoice invoice = (Invoice) model.get("invoice");

        PdfPTable pdfPTable = new PdfPTable(1);
        pdfPTable.addCell(Image.getInstance("https://logisber.com/contenido/subidas/2020/04/factura-comercial.png"));
        pdfPTable.setSpacingAfter(40L);
        PdfPCell cell ;
        cell = new PdfPCell(new Phrase("Client Data"));
        cell.setBackgroundColor(new Color(238,81,47));
        cell.setPadding(8F);
        pdfPTable.addCell(cell);

        pdfPTable.addCell(invoice.getClient().getName() + " " + invoice.getClient().getLastName());
        pdfPTable.addCell(invoice.getClient().getMail());

        PdfPTable pdfPTable2 = new PdfPTable(1);

        pdfPTable2.setSpacingAfter(20L);
        cell = new PdfPCell(new Phrase("Client Data"));
        cell.setBackgroundColor(new Color(229,238,47));
        cell.setPadding(8F);
        pdfPTable2.addCell(cell);

        pdfPTable2.addCell("ID : " + invoice.getId());
        pdfPTable2.addCell("DESCRIPTION : " + invoice.getDescription());
        pdfPTable2.addCell("DATE : " + invoice.getDate());

        document.add(pdfPTable);
        document.add(pdfPTable2);

        PdfPTable pdfPTable3 = new PdfPTable(4);
        pdfPTable3.setWidths(new float[]{3.5F,1,1,1});
        pdfPTable3.addCell("Product");
        pdfPTable3.addCell("Price");
        pdfPTable3.addCell("Quantity");
        pdfPTable3.addCell("Amount");

        for (InvoiceDetail detail: invoice.getDetails()) {
            pdfPTable3.addCell(detail.getProduct().getName());
            pdfPTable3.addCell(detail.getProduct().getPrice().toString());
            pdfPTable3.addCell(detail.getQuantity().toString());
            pdfPTable3.addCell("S/"+ detail.calculateAmount().toString());
        }
        cell = new PdfPCell();
        cell.setColspan(3);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        pdfPTable3.addCell(cell);
        pdfPTable3.addCell("S/"+ invoice.calculateTotal().toString());
        document.addAuthor("ByErich");
        document.add(pdfPTable3);


    }

}
