package view;

import com.example.allocation_and_reclaim.allocation;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Bean.Memory;
import model.Bean.NoAllocateItem;
import model.Bean.PCB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class methods {
    private static final Stack<PCB> PCBS = new Stack<>();

    public static void updateAll(TableView<PCB> tableView_new, List<PCB> newList, TableView<PCB> tableView_ready, List<PCB> readyList, TableView<PCB> tableView_running, List<PCB> runningList, TableView<PCB> tableView_terminated, List<PCB> terminatedList, ListView<PCB> listView_noAllocateTable, Memory memory, Group PANE_SHAPE) {
        update(tableView_new, newList);
        update(tableView_ready, readyList);
        update(tableView_running, runningList);
        update(tableView_terminated, terminatedList);
        update(listView_noAllocateTable, memory);
        update(PANE_SHAPE, memory);
    }

    private static void update(Group group, Memory memory) {
        group.getChildren().clear();
        group.getChildren().add(new Rectangle(0,0,200,670));
        for (NoAllocateItem item : memory.getNoAllocateTable()) {
            Rectangle rectangle = new Rectangle(0, (int) (item.getBegin() * 670.0 / 4096), 200, (int) (item.getSize() * 670.0 / 4096));
            rectangle.setFill(Color.BLUE);
            group.getChildren().add(rectangle);
        }
    }


    public static void update(TableView<PCB> tableView, List<PCB> list) {
        tableView.getItems().clear();
        for (PCB PCB : list) {
            tableView.getItems().add(PCB);
        }
    }

    public static void update(ListView<PCB> listView, Memory memory) {
        memory.order();
        listView.getItems().clear();
        List<NoAllocateItem> noAllocateTable = memory.getNoAllocateTable();
        List strings = new ArrayList();
        for (NoAllocateItem item : noAllocateTable) {
            strings.add(item.toString() + "\t\tsize:" + (item.getSize()));
        }
        listView.setItems(FXCollections.observableList(strings));
    }

    public static PCB createNewProcess() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(allocation.class.getResource("new.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 360, 290);
        Stage stage = new Stage();
        stage.setTitle("创建新进程");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        return getFromNewStack();
    }
    public static PCB getFromNewStack() {
        return PCBS.pop();
    }
    public static void addToNewStack(PCB PCB) {
        PCBS.push(PCB);
    }
}
