package helloworld.candy;

import com.github.crab2died.ExcelUtils;
import com.google.common.base.Strings;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CandyController {
    @FXML
    private MenuItem excelSplit;
    @FXML
    private MenuItem markWrongData;
    @FXML
    private Parent root;
    @FXML
    private Button chooseSourceFileBtn;
    @FXML
    private Button chooseResultFolderBtn;
    @FXML
    private TextField resultFolderLoc;
    @FXML
    private TextField sourceFileLoc;
    @FXML
    private TextField splitColumn;
    @FXML
    private TextArea note;
    @FXML
    private CheckBox headerCheck;

    private Stage stage;
    private Scene scene;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    @FXML
    protected void closeTheWindow(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    protected void chooseSourceFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            String path = file.getAbsolutePath();
            sourceFileLoc.setText(path);
        } else {
            sourceFileLoc.setText("请选择需要拆分的文件...");
        }
    }

    @FXML
    protected void chooseResultFolder(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(stage);
        if (file != null) {
            String path = file.getAbsolutePath();
            resultFolderLoc.setText(path);
        } else {
            resultFolderLoc.setText("请选择输出目录...");
        }
    }

    @FXML
    protected void startSplit(ActionEvent actionEvent) {
        String sourceFileLocText = sourceFileLoc.getText();
        String resultFolderLocText = resultFolderLoc.getText();
        String splitColumnText = splitColumn.getText();
        boolean selected = headerCheck.isSelected();

        if (Strings.isNullOrEmpty(sourceFileLocText) || Strings.isNullOrEmpty(resultFolderLocText) || Strings.isNullOrEmpty(splitColumnText)) {
            note.setText("所有信息必填！");
        }

        List<String> header = new ArrayList<>();
        Map<String, List<List<String>>> result = new HashMap<>();
        try {
            List<List<String>> readExcel2List = ExcelUtils.getInstance().readExcel2List(sourceFileLocText);

            if (selected) {
                header = readExcel2List.get(0);
                readExcel2List.remove(0);
            }

            for (List<String> list : readExcel2List) {
                result.computeIfAbsent(list.get(Integer.parseInt(splitColumnText) - 1), k -> new ArrayList<>())
                        .add(list);
            }

            if (selected) {
                for (Map.Entry<String, List<List<String>>> entry : result.entrySet()) {
                    ExcelUtils.getInstance().exportObjects2Excel(entry.getValue(), header, resultFolderLocText + File.separator + entry.getKey() + ".xlsx");
                }
            } else {
                for (Map.Entry<String, List<List<String>>> entry : result.entrySet()) {
                    ExcelUtils.getInstance().exportObjects2Excel(entry.getValue(), resultFolderLocText + File.separator + entry.getKey() + ".xlsx");
                }
            }

            note.setText(" ***  Done！ ***");
        } catch (Exception e) {
            note.setText("出错了！");
            e.printStackTrace();
        }
    }
}
