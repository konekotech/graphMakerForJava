package graphMaker;
import com.aspose.cells.SaveFormat;
import com.aspose.cells.Workbook;

public class ExcelConverter{
	void convert(String filename){
		try {
			System.out.println("Opening xlsx file. Please wait...");
			Workbook book = new Workbook(filename);
			System.out.println("Opened xlsx file!!!!");
			book.save("output.csv", SaveFormat.AUTO);
			System.out.println("Converted xlsx file to CSV!!!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	void convert(String filename, String outputname) {
		try {
			System.out.println("Opening xlsx file. Please wait...");
			Workbook book = new Workbook(filename);
			System.out.println("Opened xlsx file!!!!");
			book.save(outputname, SaveFormat.AUTO);
			System.out.println("Converted xlsx file to CSV!!!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
