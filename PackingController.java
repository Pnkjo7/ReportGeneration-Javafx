package application;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;

import java.util.Scanner;
import java.util.StringTokenizer;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class PackingController {
	
	@FXML
	void GenerateReport(ActionEvent event) {
		try {
			String file_name = "F:\\generate_pdf\\test.pdf";
			Document document = new Document();
			
			PdfWriter.getInstance(document, new FileOutputStream(file_name));
			document.open();
			
			try {
			File file = new File(file_name);
			Desktop desktop = Desktop.getDesktop();
		      //  if(file.exists()) desktop.open(file);

		        //let's try to open PDF file
		        //file = new File("/Users/pankaj/java.pdf");
		        if(file.exists()) desktop.open(file);

		        }catch(Exception fe)
		        {
		            System.out.println(" Unable to Open File");
		        }
			
			
			
			Paragraph paragraph = new Paragraph("Packing List Generation Report");
			float[] columnWidths = {0.2f, 0.2f, 0.1f, 0.2f, 0.1f, 0.2f};
			PdfPTable table = new PdfPTable(columnWidths);
			table.setWidthPercentage(100f);
			 Font bfBold12 = new Font(FontFamily.TIMES_ROMAN, 8, Font.BOLD, new BaseColor(0, 0, 0)); 
			 Font bf12 = new Font(FontFamily.TIMES_ROMAN, 8); 
			//insert column headings
			   insertCell(table, "CR. No", Element.ALIGN_CENTER, 1, bfBold12);
			   insertCell(table, "Patient Name", Element.ALIGN_CENTER, 1, bfBold12);
			   insertCell(table, "Age/Gender", Element.ALIGN_CENTER, 1, bfBold12);
			   insertCell(table, "Test Name", Element.ALIGN_CENTER, 1, bfBold12);
			   insertCell(table, "Sample Number", Element.ALIGN_CENTER, 1, bfBold12);
			   insertCell(table, "Sample Collection Date", Element.ALIGN_CENTER, 1, bfBold12);

			   table.setHeaderRows(1);
			   
			   
			   //fetching data from text file
			   
			   String LabRecord[] = new String[500];
			   File masterfile = new File("Investigation_data.txt");
			   int loc =0;
			   try(Scanner myReader = new Scanner(masterfile)){
					   while(myReader.hasNextLine()) 
					   {
						   String data = myReader.nextLine();
						   StringTokenizer st = new StringTokenizer(data,"@");
						   while(st.hasMoreTokens()) 
						   {
							   LabRecord[loc]= st.nextToken();
							   insertCell(table, LabRecord[loc], Element.ALIGN_LEFT, 1, bf12);
							   loc++;
							  						   
						   }

						   }

					   System.out.println("Report Generated Successfully");
					   }
			   
			 
			  
			   //add the PDF table to the paragraph 
			   paragraph.add(table);
			   document.add(paragraph);
			   document.close();
			
		}catch(Exception e){
			System.err.println(e);
			
		}
	}

	 private void insertCell(PdfPTable table, String text, int align, int colspan, Font font){
		   
		  //create a new cell with the specified Text and Font
		  PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
		  //set the cell alignment
		  cell.setHorizontalAlignment(align);
		  //set the cell column span in case you want to merge two or more cells
		  cell.setColspan(colspan);
		  //in case there is no text and you wan to create an empty row
		  if(text.trim().equalsIgnoreCase("")){
		   cell.setMinimumHeight(10f);
		  }
		  //add the call to the table
		  table.addCell(cell);
		   
		 }

}
