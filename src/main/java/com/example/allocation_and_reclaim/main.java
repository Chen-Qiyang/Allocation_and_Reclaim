package com.example.allocation_and_reclaim;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;

import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Bean.MemoryFactory;
import model.Bean.PCBFactory;
import model.TableList.NewList;
import model.service.service;
import view.methods;
import model.Bean.PCB;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class main {

    @FXML private Button Button_ClearAll;

    @FXML
    private Button Button_CreateCustomProcess;

    @FXML
    private Button Button_CreateManyProcesses;

    @FXML
    private Button Button_NextStep;

    @FXML
    private ListView<PCB> ListView_noAllocateTable;

    @FXML
    private TableView<PCB> TableView_NEW;

    @FXML
    private TableView<PCB> TableView_Ready;

    @FXML
    private TableView<PCB> TableView_Running;

    @FXML
    private TableView<PCB> TableView_Terminated;

    @FXML
    private AnchorPane PANE_SHAPE;

    @FXML
    private Group GROUP_SHAPE;

    boolean initialed = false;

    int createdNum = 0;


    public void nextStep() {
        //更新正在运行的进程，看是否有进程已经完成以及是否有进程新加进来
        service.UpdateRunningList(MemoryFactory.getMemory());

        service.OrderReadyListByPriority();
        List<PCB> readyList = service.getReadyList();
        while (readyList.size() > 0 && service.addableToRun()) {
            PCB PCB = readyList.get(0);
            service.addRunningFromReady(PCB);
        }
        //Run一次，Ready优先级变高
        service.solveHungerByPromoteReadyPriority();

        //将进程从new队列加入到Ready队列
        service.OrderNewListByPriority();
        List<PCB> newList = NewList.getOnlyNew();
        List<PCB> addedList = new ArrayList<>();
        for (int i = 0; i < newList.size(); i++) {
            PCB PCB = newList.get(i);
            if (service.addableToMemory(MemoryFactory.getMemory(), PCB)) {
                addedList.add(PCB);
                PCB.setPCBstate("" + PCB.hashCode());
                try {
                    service.allocateMemory(MemoryFactory.getMemory(), PCB);
                } catch (Exception e) {
                    e.printStackTrace();
                    addedList.remove(PCB);
                    PCB.setPCBstate("分配失败，回到new");
                }
            }
        }
        //将可加入的进程加到内存中
        service.addListToReadyFromNew(addedList);
    }

    @FXML
    void ClearAll(ActionEvent event) {
        service.cleanAll();
        updateView();
        service.printAll();
    }

    @FXML
    void CreateCustomProcess(ActionEvent event) throws IOException {
        if (!initialed) {
            initialAll();
        }
        service.addNew(methods.createNewProcess());
        updateView();
        service.printAll();
    }

    @FXML
    void CreateManyProcesses(ActionEvent event) {
        if (!initialed) {
            initialAll();
        }
        for (int i = 0; i < 6; i++) {
            PCB PCB = PCBFactory.CreateProcess("进程" + createdNum++, (int) (Math.random() * 14) + 5, (int) (Math.random() * 8), (int) (Math.random() * 500) + 1);
            service.addNew(PCB);
        }
        updateView();
        service.printAll();
    }

    private void initialAll() {
        initialOne(TableView_NEW);
        initialOne(TableView_Ready);
        initialOne(TableView_Running);
        initialOne(TableView_Terminated);
    }

    private void initialOne(TableView<PCB> tableView) {
        tableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("pid"));
        tableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("priority"));
        tableView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("begin"));
        tableView.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("size"));
        tableView.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("runTime"));
        tableView.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("PCBstate"));
    }

    @FXML
    void NextStep(ActionEvent event) {
        nextStep();
        updateView();
        service.printAll();
    }

    void updateView() {
        methods.updateAll(TableView_NEW, service.getNewList(), TableView_Ready, service.getReadyList(),
                TableView_Running, service.getRunningList(), TableView_Terminated, service.getTerminatedList(),
                ListView_noAllocateTable, MemoryFactory.getMemory(), GROUP_SHAPE);
    }
}