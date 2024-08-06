package com.pharma.medicare.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pharma.medicare.constant.ServiceConstants;
import com.pharma.medicare.domain.PDFFileDetails;
import com.pharma.medicare.repository.PDFFileDataRepository;
import com.pharma.medicare.request.CustomerBillingRequests;
import com.pharma.medicare.request.ProductSellingDetails;

@Service
public class PdfBillGenerator {
	
	@Autowired
	PDFFileDataRepository pdfFileSaveRepository;
	
	public String generatePdf(CustomerBillingRequests customerBillingRequests, String userName) {
		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		String response="";
		try {
			PdfWriter writer = PdfWriter.getInstance(document, out);
			BackgroundColorEvent event = new BackgroundColorEvent();
			writer.setPageEvent(event);
			document.open();

			toWritePdfHeader(document, out, ServiceConstants.HEADER_SPACE + ServiceConstants.HEADER_TITLE);
			toWritePdfCustomerDetails(document, out, writer,customerBillingRequests);// need to pass all details of customer name and other
			addMedicineDetailsInTable(document, out,customerBillingRequests);
			addTotalPriceinNumberAndWord(document, out, writer,customerBillingRequests);
			addCustomerAndOwnerSign(document, out, writer);
			document.close();
			response=generateBase64CodeString(out.toByteArray());

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(response);
		return response;
	}
	
	
	 public static String generateBase64CodeString(byte[] pdfByteArray) {
	       
	        // Create a ByteArrayInputStream from the byte array
	        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(pdfByteArray);

	        try {
	           
	            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	            byte[] buffer = new byte[1024];
	            int bytesRead;

	            while ((bytesRead = byteArrayInputStream.read(buffer)) != -1) {
	                byteArrayOutputStream.write(buffer, 0, bytesRead);
	            }
	            byte[] byteArray =byteArrayOutputStream.toByteArray();
	            String base64String = Base64.getEncoder().encodeToString(byteArray);
	            System.out.println(base64String);
	            return base64String;
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			return null;
	    }

	public String createPdfName(CustomerBillingRequests customerBillingRequests) {
        String name="";
       if(CommonUtil.isNotNull(customerBillingRequests.getCustomerName())) {
    	   
    	    name = customerBillingRequests.getCustomerName().replace(" ", "_").toLowerCase()+"_";
       }
        java.util.Date currentDate = new java.util.Date();

        // Get today's date
        String datePattern = "ddMMMyyyy_HHmmss";
        SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
        String todayDate = sdf.format(currentDate);

      String pdfName="Billing_Invoice_"+ name + todayDate + ".pdf";
        // Construct the PDF file name
        return pdfName;
    }

	public void toWritePdfHeader(Document document, ByteArrayOutputStream out, String value) {

		try {
			Font headerFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD);
			document.add(new Paragraph(value, headerFont));
			document.add(new Paragraph("\n"));

		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}

	public void toWritePdfCustomerDetails(Document document, ByteArrayOutputStream out, PdfWriter writer, CustomerBillingRequests customerBillingRequests) {
		Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
		Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
		PdfContentByte content = writer.getDirectContent();

		// Set starting positions
		float xLabel = 65f; // X position for labels
		float xValue = 200f; // X position for values
		float yPosition = 750f; // Starting Y position
		float lineHeight = 20f; // Line height

		// Draw Customer Name & Invoice Number
		drawText(content, headerFont, ServiceConstants.CUSTOMER_NAME, xLabel, yPosition);
		drawText(content, normalFont, customerBillingRequests.getCustomerName(), xValue - 25, yPosition);
		drawText(content, headerFont, ServiceConstants.INVOICE_NUMBER, xLabel + 300, yPosition);
		drawText(content, normalFont, customerBillingRequests.getInvoiceNumber(), xValue + 270, yPosition);
		yPosition -= lineHeight;

		// Draw Mobile Number & Invoice Date
		drawText(content, headerFont, ServiceConstants.MOBILE_NUMBER, xLabel, yPosition);
		drawText(content, normalFont, customerBillingRequests.getMobileNumber(), xValue - 25, yPosition);
		drawText(content, headerFont, ServiceConstants.INVOICE_DATE, xLabel + 300, yPosition);
		drawText(content, normalFont, customerBillingRequests.getInvoiceDate(), xValue + 270, yPosition);
		yPosition -= lineHeight;

		// Draw Address & Amount Type
		drawText(content, headerFont, ServiceConstants.ADDRESS, xLabel, yPosition);
		drawText(content, normalFont, customerBillingRequests.getAddress(), xValue - 25, yPosition);
		drawText(content, headerFont, ServiceConstants.AMOUNT_TYPE, xLabel + 300, yPosition);
		drawText(content, normalFont, customerBillingRequests.getAmountType(), xValue + 270, yPosition);

		// Add some space after the table
		try {
			document.add(new Paragraph("\n\n"));
			document.add(new Paragraph("\n\n"));
		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}

	private void drawText(PdfContentByte content, Font font, String text, float x, float y) {
		content.beginText();
		content.setFontAndSize(font.getBaseFont(), font.getSize());
		content.setTextMatrix(x, y);
		if(CommonUtil.isNotNull(text)) {
			content.showText(text);
		}
		content.endText();
	}

	public void addMedicineDetailsInTable(Document document, ByteArrayOutputStream out,
			CustomerBillingRequests customerBillingRequests) {

		try {
			PdfPTable table = new PdfPTable(6);
			table.setWidthPercentage(100);
			 float[] columnWidths = {2f, 2f, 1.75f, 1.2f, 1f, 1f};
		        table.setWidths(columnWidths);
			Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
			String[] header = { ServiceConstants.PRODUCT_NAME, ServiceConstants.COMPANY_NAME, ServiceConstants.EXP_DATE,
					ServiceConstants.QUANTITY, ServiceConstants.PRICE, ServiceConstants.TOTAL };

			for (int i = 0; i < header.length; i++) {
				PdfPCell tabletitle = new PdfPCell(new Paragraph(header[i], headerFont));
				tabletitle.setBackgroundColor(BaseColor.LIGHT_GRAY);
				tabletitle.setMinimumHeight(30f);
				tabletitle.setPaddingTop(8f);  
		         tabletitle.setPaddingBottom(9f);
				tabletitle.setHorizontalAlignment(Element.ALIGN_CENTER); 
				table.addCell(tabletitle);
			}
			List<ProductSellingDetails> materialSellingDetails = customerBillingRequests.getMaterialSellingDetails();
			for (int i = 0; i < materialSellingDetails.size(); i++) {
				 PdfPCell cell;
				 	//	productName			 
				 	cell = new PdfPCell(new Paragraph(materialSellingDetails.get(i).getProductName()));
		            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            cell.setMinimumHeight(20f);
		            table.addCell(cell);
		            
		            // companuyName
		            cell = new PdfPCell(new Paragraph(materialSellingDetails.get(i).getCompanyName()));
		            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            cell.setMinimumHeight(20f);
		            table.addCell(cell);
		            
		            //exp.date
		            if (CommonUtil.isNotNull(materialSellingDetails.get(i).getExpDate())) {
						SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
						String date = dateFormat.format(materialSellingDetails.get(i).getExpDate());
						cell = new PdfPCell(new Paragraph(date));
			            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			            cell.setMinimumHeight(20f);
			            table.addCell(cell);
					} else {
						table.addCell("");
					}
		            
		            //quantity
		            if (CommonUtil.isNotNull(materialSellingDetails.get(i).getQuantity())) {
		            	cell = new PdfPCell(new Paragraph(Long.toString(materialSellingDetails.get(i).getQuantity())));
			            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			            cell.setMinimumHeight(20f);
			            table.addCell(cell);
					}
		            
		            //price
					if (CommonUtil.isNotNull(materialSellingDetails.get(i).getPrice())) {
						cell = new PdfPCell(new Paragraph(Double.toString(materialSellingDetails.get(i).getPrice())));
			            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			            cell.setMinimumHeight(20f);
			            table.addCell(cell);
					}
					
					//total
					if (CommonUtil.isNotNull(materialSellingDetails.get(i).getTotal())) {	
						cell = new PdfPCell(new Paragraph(Double.toString(materialSellingDetails.get(i).getTotal()),headerFont));
			            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			           
			            cell.setMinimumHeight(20f);
			            table.addCell(cell);
					}
			}

			document.add(table);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void addTotalPriceinNumberAndWord(Document document, ByteArrayOutputStream out, PdfWriter writer, CustomerBillingRequests customerBillingRequests) {
		try {
			// Get current Y position after the table
			float currentY = writer.getVerticalPosition(true) - 20; // Adjust space as needed
			Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD);

			// Convert total price to words (this method needs to be implemented)
			String totalPriceInWords = customerBillingRequests.getTotalAmountInWord();

			
			String totalPriceInNumber = customerBillingRequests.getTotalAmount();

			// Get the direct content
			PdfContentByte content = writer.getDirectContent();

			// Add total price in words and number aligned to the left
			ColumnText.showTextAligned(content, Element.ALIGN_LEFT,
					new Paragraph(ServiceConstants.TOTAL_AMOUNT_IN_WORD + totalPriceInWords,boldFont), 50, currentY, 0);
			ColumnText.showTextAligned(content, Element.ALIGN_RIGHT,
					new Paragraph(ServiceConstants.TOTAL_AMOUNT + totalPriceInNumber,boldFont),
					document.getPageSize().getWidth() - 50, currentY, 0);

			// Add some space after the total price
			document.add(new Paragraph("\n\n"));
			document.add(new Paragraph("\n"));

		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}

	public void addCustomerAndOwnerSign(Document document, ByteArrayOutputStream out, PdfWriter writer) {

		try {

			// Get current Y position after the table
			float currentY = writer.getVerticalPosition(true) - 20; // Adjust space as needed

			Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);

			// Add customer & owner signature dynamically
			PdfContentByte content = writer.getDirectContent();
			ColumnText.showTextAligned(content, Element.ALIGN_LEFT, new Paragraph("Customer Signature", boldFont), 50,
					currentY, 0);
			ColumnText.showTextAligned(content, Element.ALIGN_RIGHT, new Paragraph("Owner Signature", boldFont),
					document.getPageSize().getWidth() - 50, currentY, 0);

			// Add some space after the sign
			document.add(new Paragraph("\n\n"));

			// Add the footer note
			Paragraph footerNote = new Paragraph(ServiceConstants.COMPUTER_GENERATED_INVOICE);
			footerNote.setAlignment(Element.ALIGN_CENTER);
			document.add(footerNote);

		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
}
