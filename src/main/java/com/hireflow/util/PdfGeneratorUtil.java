package com.hireflow.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Component;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

@Component
public class PdfGeneratorUtil {

    public String generateOfferLetterPdf(Long offerLetterId,
                                         String content) throws IOException {

        String folder = "uploads/offers";

        Files.createDirectories(Paths.get(folder));

        String filePath =
                folder + "/offer_letter_" + offerLetterId + ".pdf";

        PDDocument document = new PDDocument();

        PDPage page = new PDPage();

        document.addPage(page);

        PDPageContentStream stream =
                new PDPageContentStream(document, page);

        stream.setFont(
        	    new PDType1Font(Standard14Fonts.FontName.HELVETICA),
        	    12
        	);
        stream.beginText();

        stream.newLineAtOffset(50, 750);

        String[] lines = content.split("\n");

        for (String line : lines) {

            stream.showText(line);

            stream.newLineAtOffset(0, -20);
        }

        stream.endText();

        stream.close();

        document.save(filePath);

        document.close();

        return filePath;
    }
}