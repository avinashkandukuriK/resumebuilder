package com.spring.coverletter.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Controller
@RequestMapping("/")
public class CoverLetterController {

    private String generateCoverLetterText(String companyName, String hrDetails, String companyAddress) {
        // Your cover letter generation logic here
        return "Dear " + hrDetails + ",\n\n" +
                "I am writing to express my interest in a position at " + companyName + ".\n\n" +
                "Company Address: " + companyAddress + "\n\n" +
                "Sincerely,\nYour Name";
    }

    @PostMapping("/api/generateCoverLetter")
    public String generateCoverLetter(
            @RequestParam String companyName,
            @RequestParam String hrDetails,
            @RequestParam String companyAddress,
            Model model) {
        String coverLetter = generateCoverLetterText(companyName, hrDetails, companyAddress);
        model.addAttribute("coverLetter", coverLetter);
        return "coverLetterForm";
    }

    @PostMapping("/api/convertToPdf")
    public void convertToPdf(@RequestParam String coverLetter, HttpServletResponse response) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD,  12);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 700);

                String[] lines = coverLetter.split("\n");
                for (String line : lines) {
                    contentStream.showText(line);
                    contentStream.newLine();
                }

                contentStream.endText();
            }

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.save(byteArrayOutputStream);
            document.close();

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=cover_letter.pdf");
            response.getOutputStream().write(byteArrayOutputStream.toByteArray());
            response.getOutputStream().flush();
        }
    }

    @PostMapping("/api/convertToWord")
    public void convertToWord(@RequestParam String coverLetter, HttpServletResponse response) throws IOException {
        XWPFDocument document = new XWPFDocument();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        document.createParagraph().createRun().setText(coverLetter);

        document.write(byteArrayOutputStream);
        document.close();

        response.setContentType("application/msword");
        response.setHeader("Content-Disposition", "attachment; filename=cover_letter.docx");
        response.getOutputStream().write(byteArrayOutputStream.toByteArray());
        response.getOutputStream().flush();
    }
}
