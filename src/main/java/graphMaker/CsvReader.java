package graphMaker;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvReader {
	private final String filename;
	private final Path path;
	private List<String> lines = new ArrayList<String>(Arrays.asList("0"));

	//コンストラクタ
	CsvReader(String filename){
		this.filename  = filename;
		this.path = Paths.get(this.filename);
		try {//CSVfileの読み込み
			this.lines = Files.readAllLines(path, Charset.forName("UTF-8"));
		} catch (IOException e) {//入出力の例外処理
			e.printStackTrace();
		}
	}
	List<String> getAll(){
		return lines;
	}
	List<Double> getColumn(String wantedItem){
		int i, j;
		String[] items = lines.get(0).split(",");
		List<Double> column = new ArrayList<Double>();
		for(i = 0; i < items.length; i++){
			if (items[i].equals(wantedItem)){
				for (j = 1; j < lines.size() - 1; j++){
					String[] numbers = lines.get(j).split(",");
					column.add(Double.parseDouble(numbers[i]));//StringをDouble型に変換して挿入
				}
			}
		}
		return column;
	}
	List<Double> getColumn(String wantedItem, int start, int end) {//a,bはExcelでみたときの行番号
		if(start < 1){
			start = 1;
		}else if(end > lines.size() - 1){
			end = lines.size() - 1;
		}
		int i, j;
		String[] items = lines.get(0).split(",");
		List<Double> column = new ArrayList<Double>();
		for (i = 0; i < items.length; i++) {
			if (items[i].equals(wantedItem)) {
				for (j = start - 1; j < end; j++) {//ここがさっきと違う
					String[] numbers = lines.get(j).split(",");
					column.add(Double.parseDouble(numbers[i]));// StringをDouble型に変換して挿入
				}
			}
		}
		return column;
	}

}
