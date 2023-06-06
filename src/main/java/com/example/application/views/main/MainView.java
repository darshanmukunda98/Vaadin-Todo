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
public class MainView extends HorizontalLayout {

    private TextField searchField;
    private Button searchButton;
    private HorizontalLayout mainApp;
    public VerticalLayout displayTodos;
    private List<Todo> todos = new ArrayList<>();

    public MainView() {
        initComponents();
        searchButtonListener();
        mainApp.add(searchField,searchButton);
    }
    public void searchButtonListener(){
        searchButton.addClickListener(buttonClickEvent -> {
            Todo todo = new Todo(String.valueOf(Math.random()), searchField.getValue(),false, false);
            todos.add(todo);
        });
    }
    private void initComponents(){
        H1 appH1 = new H1("Todo");
        setAlignItems(Alignment.CENTER);
        Icon icon = new Icon(VaadinIcon.SEARCH);
        searchButton = new Button(icon);
        searchField = new TextField();
        searchField.setWidthFull();
        searchButton.addClickShortcut(Key.ENTER);
        mainApp = new HorizontalLayout();
        displayTodos = new VerticalLayout();
    }

}
