package application;

import java.awt.Desktop;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
//import java.text.SimpleDateFormat;
//import java.util.Date;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;

public class PackingController {

	@FXML
	private VBox Qr;
	@FXML
	private RadioButton dateWiseMon;

	@FXML
	private RadioButton datewise;

	@FXML
	private RadioButton today;

	@FXML
	private HBox hbox;

	@FXML
	private Button cancelBtn;


	final ToggleGroup tg = new ToggleGroup();

	String Data1 = "";

	@FXML
	void initialize() throws FileNotFoundException {
		today.setToggleGroup(tg);
		datewise.setToggleGroup(tg);
		dateWiseMon.setToggleGroup(tg);

		// fetching data from text file

		String LabRecord[][] = new String[500][10];
		File masterfile = new File("Investigation_data.txt");
		int loc = 0;
		try (Scanner myReader = new Scanner(masterfile)) {
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				StringTokenizer st = new StringTokenizer(data, "@");
				while (st.hasMoreTokens()) {
//								LabRecord[loc][0] = "" + st.nextToken();
//								LabRecord[loc][1] = "" + st.nextToken();
//								LabRecord[loc][2] = "" + st.nextToken();
//								LabRecord[loc][3] = "" + st.nextToken();
//								LabRecord[loc][4] = "" + st.nextToken();
//								LabRecord[loc][5] = "" + st.nextToken();
					LabRecord[loc][6] = "" + st.nextToken();
				}

				// System.out.println(LabRecord[loc][6]);
				loc++;
			}

			Label l1 = new Label("From Date");
			l1.setPrefWidth(60);
			l1.setStyle("-fx-font-weight : bold");
			DatePicker d1 = new DatePicker();
			d1.setPrefWidth(180);

			Label l2 = new Label("To Date");
			l2.setPrefWidth(60);
			l2.setStyle("-fx-font-weight : bold");
			DatePicker d2 = new DatePicker();
			d2.setPrefWidth(180);

			hbox.getChildren().addAll(l1, d1, l2, d2);
			hbox.setVisible(false);

			tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

				public void changed(ObservableValue<? extends Toggle> ob, Toggle o, Toggle n) {

					if (tg.getSelectedToggle() == today) {

						{
							hbox.setVisible(false);
						//	Date date = new Date();
						//	String newstring = new SimpleDateFormat("yyyy-MM-dd").format(date);

						}
					} else if (tg.getSelectedToggle() == datewise) {
						hbox.setVisible(true);

					} else {
						hbox.setVisible(true);
					}

				}

			});
		}
	}

	@FXML
	void cancel(ActionEvent event) {
		Stage stage = (Stage) cancelBtn.getScene().getWindow();
		stage.close();
	}

	@FXML
	void GenerateReport(ActionEvent event) {
		String a =null;
		try {
			 String file_name = "F:/generate_pdf/sample4.pdf";
			Document document = new Document();
			try {
				File file = new File(file_name);
				Desktop desktop = Desktop.getDesktop();
				// if(file.exists()) desktop.open(file);
	
				// let's try to open PDF file
				// file = new File("/Users/pankaj/java.pdf");
				if (file.exists())
					desktop.open(file);
	
			} catch (Exception fe) {
				System.out.println(" Unable to Open File");
			}
			
			PdfPTable table = new PdfPTable(new float[] { 0.6f, 1.6f, 1.6f, 1.4f, 1.4f, 1.4f, 1.4f, 0.7f });
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			
			table.setWidthPercentage(100f);
			table.addCell("S.no");
			table.addCell("Cr no.");
			table.addCell("Name");
			table.addCell("Age/Gender");
			table.addCell("Test");
			table.addCell("Sample Number");
			table.addCell("Date");
			table.addCell("QR Code");

			table.setHeaderRows(1);
			
			
			
			

			PdfPCell[] cells = table.getRow(0).getCells();
			for (int j = 0; j < cells.length; j++) {
				cells[j].setBackgroundColor(BaseColor.GRAY);
			}

			String LabRecord[][] = new String[500][10];
			File masterfile = new File("Investigation_data.txt");
			int loc = 0;
			try (Scanner myReader = new Scanner(masterfile)) {
				while (myReader.hasNextLine()) {
					String data = myReader.nextLine();
					a = data;
					StringTokenizer st = new StringTokenizer(data, "@");
					while (st.hasMoreTokens()) {
						
						LabRecord[loc][0] = "" + st.nextToken();
						LabRecord[loc][1] = "" + st.nextToken();
						LabRecord[loc][2] = "" + st.nextToken();
						LabRecord[loc][3] = "" + st.nextToken();
						LabRecord[loc][4] = "" + st.nextToken();
						LabRecord[loc][5] = "" + st.nextToken();
						LabRecord[loc][6] = "" + st.nextToken();
						
					}
					ByteArrayOutputStream out = QRCode.from(a).to(ImageType.PNG).withSize(5, 5).stream();
					ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
					FileOutputStream fos = new FileOutputStream("barcode.png");
					fos.write(out.toByteArray());
					fos.flush();
					fos.close();
					Image img1 = Image.getInstance("barcode.png");
					table.addCell(LabRecord[loc][0]);
					table.addCell(LabRecord[loc][1]);
					table.addCell(LabRecord[loc][2]);
					table.addCell(LabRecord[loc][3]);
					table.addCell(LabRecord[loc][4]);
					table.addCell(LabRecord[loc][5]);
					table.addCell(LabRecord[loc][6]);

					table.addCell(new PdfPCell(img1, true));
					
					

					
					loc++;
				}
				
				
				PdfWriter.getInstance(document, new FileOutputStream("F:/generate_pdf/sample4.pdf"));
				document.open();
				document.add(table);
				document.close();
				System.out.println("Done");

			}
		} catch (Exception e) {
			System.err.println(e);

		}
	}

	

}
