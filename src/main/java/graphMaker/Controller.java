package graphMaker;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.List;

public class Controller {
    private String filename;
    private String csvname;
    private String xItem;
    private String yItem;
    private String xLabelName;
    private String yLabelName;
    private List<Double> xList;
    private List<Double> yList;
    private String caption;
    private String label;
    private Boolean zero = false;

    @FXML
    private Button Button2;

    @FXML
    private Button button1;

    @FXML
    private TextArea result1;

    @FXML
    private TextArea result2;

    @FXML
    private Text text1;

    @FXML
    private TextField xLabel;

    @FXML
    private TextField xName;

    @FXML
    private TextField yLabel;

    @FXML
    private TextField yName;

    @FXML
    private TextField captionoflatex;

    @FXML
    private TextField labeloflatex;

    @FXML
    private CheckBox whetherzero;

    @FXML
    private CheckBox rownumber;

    @FXML
    private TextField startofrow;

    @FXML
    private TextField endofrow;


    @FXML
    void handleDragDropped(DragEvent event) {
        boolean success = false;
        // ファイルの場合だけ受け付ける例
        Dragboard db = event.getDragboard();
        if (db.hasFiles()) {
            List<File> list = db.getFiles();
            filename = list.get(0).getPath();
            this.text1.setText(filename);
            success = true;
        }
        event.setDropCompleted(success);
        event.consume();
    }

    @FXML
    void handleDragOver(DragEvent event) {
        // ファイルの場合だけ受け付ける例
        Dragboard db = event.getDragboard();
        if (db.hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    @FXML
    void onButton1Pressed(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("ファイル選択");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excelファイル", "*.xlsx"),
                new FileChooser.ExtensionFilter("すべてのファイル", "*.*")
        );
        fc.setInitialDirectory(new File(System.getProperty("user.home")));
        File file = fc.showOpenDialog(null);
        if(file != null) {
            filename = file.getPath();
            this.text1.setText(filename);
        }
    }

    @FXML
    void onButton2Pressed(ActionEvent event) {
        ExcelConverter converter = new ExcelConverter();
        csvname = filename.substring(0,filename.length() - 5) + ".csv";
        converter.convert(filename, csvname);
        CsvReader reader = new CsvReader(csvname);
        GraphBuilder builder = new GraphBuilder();
        xItem = this.xName.getText();
        yItem = this.yName.getText();
        if(whetherzero.isSelected() == true){
            zero = true;
        }
        if(rownumber.isSelected() == true){
            xList = reader.getColumn(xItem,Integer.parseInt(this.startofrow.getText()), Integer.parseInt(this.endofrow.getText()));
            yList = reader.getColumn(yItem,Integer.parseInt(this.startofrow.getText()), Integer.parseInt(this.endofrow.getText()));
        }else{
            xList = reader.getColumn(xItem);
            yList = reader.getColumn(yItem);
        }
        if ((xList.size() == 0 || yList.size() == 0) || xList.size() != yList.size()){
            this.result1.setText("入力値が不正です");
            this.result2.setText("入力値が不正です");
        }
        xLabelName = this.xLabel.getText();
        yLabelName = this.yLabel.getText();
        caption = this.captionoflatex.getText();
        label = this.labeloflatex.getText();
        List<String> ret = builder.build(xList, yList, xLabelName, yLabelName, caption, label, zero);
        this.result1.setText(ret.get(4));
        this.result2.setText(ret.get(5));
    }

}
