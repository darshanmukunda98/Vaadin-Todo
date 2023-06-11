package com.example.application.views.main;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.charts.themes.LumoDarkTheme;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;

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
        loadTodos();
        searchButtonListener();
        mainApp.add(searchField,searchButton);
        add(appH1,mainApp,displayTodos);
//        this.getStyle().set("background-color","#b3e6ff");
        setSizeFull();
    }
    public void searchButtonListener(){
        searchButton.addClickListener(buttonClickEvent -> {
            if(!searchField.getValue().equals("")) {
                Todo todo = new Todo( false,searchField.getValue(), false,"","","");
                String id = TodoModel.insert(todo);
                TodoItem todoItem = new TodoItem(id, todo.isDone(), todo.getTitle(), todo.isDeleted(),todo.getNotes(),todo.getDate(),todo.getPriority());
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
        searchButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        searchField = new TextField();
        searchField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        searchField.setWidthFull();
        searchButton.addClickShortcut(Key.ENTER);
        mainApp = new HorizontalLayout();
        displayTodos = new VerticalLayout();
        displayTodos.setAlignItems(Alignment.CENTER);
    }
    public void loadTodos(){
        for(Todo todo:TodoModel.findAll()){
          displayTodos.add();
            TodoItem todoItem = new TodoItem(todo.getId(), todo.isDone(), todo.getTitle(), todo.isDeleted(),todo.getNotes(),todo.getDate(),todo.getPriority());
            todoItem.setWidth("50%");
            displayTodos.add(todoItem);
        }

    }

}
