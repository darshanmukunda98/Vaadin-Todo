package com.example.application.views.main;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@PageTitle("Main")
@Route(value = "")
public class MainView extends VerticalLayout {

    private TextField searchField;
    private Button searchButton;
    private HorizontalLayout mainApp;
    public VerticalLayout displayTodos;
    private H1 appH1;

    public MainView() {
        initComponents();
        searchButtonListener();
        mainApp.add(searchField,searchButton);
        add(appH1,mainApp,displayTodos);
        this.getStyle().set("background-color","#b3e6ff");
        setSizeFull();
    }
    public void searchButtonListener(){
        searchButton.addClickListener(buttonClickEvent -> {
            if(!searchField.getValue().equals("")) {
                Todo todo = new Todo(searchField.getValue(), false, false);
                String id = TodoModel.insert(todo);
                TodoItem todoItem = new TodoItem(id, todo.isDone(), todo.getTitle(), todo.isDeleted());
                todoItem.setWidth("50%");
                displayTodos.add(todoItem);
                searchField.clear();
            }else {
                Notification.show("Enter Something");
            }
        });
    }
    private void initComponents(){
        appH1 = new H1("Todo");
        setAlignItems(Alignment.CENTER);
        Icon icon = new Icon(VaadinIcon.SEARCH);
        searchButton = new Button(icon);
        searchField = new TextField();
        searchField.setWidthFull();
        searchButton.addClickShortcut(Key.ENTER);
        mainApp = new HorizontalLayout();
        displayTodos = new VerticalLayout();
        displayTodos.setAlignItems(Alignment.CENTER);
    }

}
