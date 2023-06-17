package com.example.application.views.main;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.datepicker.DatePickerVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.select.SelectVariant;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextAreaVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.shared.Registration;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class TodoItem extends VerticalLayout {
    public TextField todoTitleTextField;
    public Span todoTitleSpan;
    public Checkbox todoDoneCheckBox;
    public Button todoDeletedButton;
    public TextArea todoNotes;
    public Button todoToday;
    public Button todoTomorrow;
    public DatePicker todoDate;
    public Select todoPriority;
    public HorizontalLayout todoDetails;
    public HorizontalLayout mainTodo;
    public HorizontalLayout expand;
    public HorizontalLayout nonExpand;
    private String id;
    public TodoItem(String id, boolean done, String todoTitle, boolean deleted,String notes,String date,String priority){
        this.id = id;
        getStyle().set("border","1px solid");
        initComponents();
        add(mainTodo(done,todoTitle,deleted),todoDetails(notes,date,priority));
        setWidthFull();
        setId(id);
        setSpacing(false);
        setPadding(false);
    }
    private TextField todoTitleNotCompleted(String todoTitle) {
            todoTitleTextField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
            todoTitleTextField.setWidth("50%");
            todoTitleTextField.setValue(todoTitle);
            todoTitleTextField.addValueChangeListener(event -> {
                TodoModel.update(id, event.getValue(), "title");
            });
            return todoTitleTextField;

    }
    private Span todoTitleCompleted(String todoTitle){

        todoTitleSpan.setText(todoTitle);
        todoTitleSpan.getStyle().set("text-decoration", "line-through");
        todoTitleSpan.setWidth("50%");
        return todoTitleSpan;
    }
    private Checkbox todoDone(boolean done){

        todoDoneCheckBox.setValue(done);
        todoDoneCheckBox.addValueChangeListener(event ->{
            if(event.getValue()) {
                mainTodo.remove(todoTitleTextField);
                mainTodo.addComponentAtIndex(1,todoTitleCompleted(todoTitleTextField.getValue()));
                expand.setEnabled(false);
            }
            else {
                mainTodo.remove(todoTitleSpan);
                mainTodo.addComponentAtIndex(1,todoTitleNotCompleted(todoTitleSpan.getText()));
                expand.setEnabled(true);
            }
            TodoModel.update(id, String.valueOf(event.getValue()),"done");
        });
        return todoDoneCheckBox;
    }
    private Button todoDeleted(boolean deleted){

        todoDeletedButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        todoDeletedButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        todoDeletedButton.addClickListener(buttonClickEvent -> {
            TodoModel.update(id, String.valueOf(true),"deleted");
            this.removeFromParent();
        });
        return todoDeletedButton;
    }
    private TextArea todoNotes(String notes){

        todoNotes.addThemeVariants(TextAreaVariant.LUMO_SMALL);
        todoNotes.setWidthFull();
        todoNotes.setValue(notes);
        todoNotes.addValueChangeListener(event->{
           TodoModel.updateNotes(id,event.getValue());
        });
        return todoNotes;
    }
    private Button todoToday(){

        todoToday.addThemeVariants(ButtonVariant.LUMO_SMALL);
        todoToday.addClickListener(buttonClickEvent -> {
           TodoModel.updateDate(id,LocalDate.now().toString());
           todoDate.setValue(LocalDate.now());
        });
        return todoToday;
    }
    private Button todoTomorrow(){

        todoTomorrow.addThemeVariants(ButtonVariant.LUMO_SMALL);
        todoTomorrow.addClickListener(buttonClickEvent -> {
            TodoModel.updateDate(id,LocalDate.now().plusDays(1).toString());
            todoDate.setValue(LocalDate.now().plusDays(1));
        });
        return todoTomorrow;
    }
    private DatePicker todoDate(String date){

        todoDate.addThemeVariants(DatePickerVariant.LUMO_SMALL);
        if (date.equals("")) {
            todoDate.setPlaceholder("No Date Set");
        }else {
            todoDate.setValue(LocalDate.parse(date));
        }
        todoDate.addValueChangeListener(dateValue -> {
            TodoModel.updateDate(id,dateValue.getValue().toString());
        });
        return todoDate;
    }
    private Select<String> todoPriority(String priority){

        todoPriority.addThemeVariants(SelectVariant.LUMO_SMALL);
        todoPriority.setLabel("Priority");
        todoPriority.setItems(List.of("None","High","Medium","Low"));
        todoPriority.setValue(priority);
        getStyle().set("border-left-style","solid");
        getStyle().set( "border-left-width","thick");
        switch( priority ){
            case "High"-> getStyle().set("border-left-color","red");
            case "Medium" -> getStyle().set("border-left-color","orange");
            case "Low" -> getStyle().set("border-left-color","blue");
            case "None" -> {
                getStyle().set("border-left-color","unset");
                getStyle().set( "border-left-width","thin");
            }
        }
        todoPriority.addValueChangeListener(event->{
            getStyle().set("border-left-style","solid");
            getStyle().set( "border-left-width","thick");
            switch( (String) event.getValue()){
                case "High"-> getStyle().set("border-left-color","red");
                case "Medium" -> getStyle().set("border-left-color","orange");
                case "Low" -> getStyle().set("border-left-color","blue");
                case "None" -> {
                    getStyle().set("border-left-color","unset");
                    getStyle().set( "border-left-width","thin");
                }
            }
            TodoModel.updatePriority(id, (String) event.getValue());
        });
        return todoPriority;
    }
    private HorizontalLayout todoDetails(String notes, String date,String priority){

        HorizontalLayout dateLayout = new HorizontalLayout(todoToday(),todoTomorrow(),todoDate(date));
        dateLayout.setAlignItems(Alignment.BASELINE);
        dateLayout.setSpacing(false);
        dateLayout.setPadding(false);
        VerticalLayout date_PriorityLayout = new VerticalLayout(dateLayout,todoPriority(priority));
        date_PriorityLayout.setSpacing(false);
        date_PriorityLayout.setPadding(false);
        date_PriorityLayout.setAlignItems(Alignment.CENTER);
        todoDetails = new HorizontalLayout(todoNotes(notes),date_PriorityLayout);
        todoDetails.setPadding(false);
        todoDetails.setSpacing(false);
        todoDetails.setWidthFull();
        todoDetails.setVisible(false);
        return todoDetails;
    }
    private HorizontalLayout mainTodo(boolean done,String todoTitle,boolean deleted){
        mainTodo = new HorizontalLayout();
        mainTodo.setAlignSelf(Alignment.CENTER,todoDoneCheckBox,todoTitleTextField,todoTitleSpan);
        mainTodo.setWidthFull();
        mainTodo.setPadding(false);
        mainTodo.setSpacing(false);

        if(done)
            mainTodo.add(todoDone(done),todoTitleCompleted(todoTitle),getExpand(),todoDeleted(deleted));
        else
            mainTodo.add(todoDone(done),todoTitleNotCompleted(todoTitle),getExpand(),todoDeleted(deleted));


        return mainTodo;
    }
    private void initComponents(){
        todoDoneCheckBox = new Checkbox();
        todoTitleSpan = new Span();
        todoTitleTextField = new TextField();
        todoDeletedButton = new Button(new Icon(VaadinIcon.CLOSE));
        todoNotes = new TextArea("Notes");
        todoToday = new Button("Today");
        todoTomorrow = new Button("Tomorrow");
        todoDate = new DatePicker("Due Date");
        todoPriority = new Select<>();
        expand = new HorizontalLayout(new Icon(VaadinIcon.CHEVRON_DOWN_SMALL));
        nonExpand = new HorizontalLayout(new Icon(VaadinIcon.CHEVRON_UP_SMALL));
    }
    private HorizontalLayout getNonExpand(){
        nonExpand.setJustifyContentMode(JustifyContentMode.END);
        nonExpand.setAlignItems(Alignment.CENTER);
        nonExpand.setWidth("43%");
        nonExpand.addClickListener((divClickEvent -> {
            if (!todoDetails.isVisible()) {
                todoDetails.setVisible(true);
                todoDetails.getStyle().set("border-top", "1px solid");
                mainTodo.addComponentAtIndex(2,getNonExpand());
                mainTodo.remove(expand);
            } else{
                todoDetails.setVisible(false);
                todoDetails.getStyle().set("border-top","none");
                mainTodo.addComponentAtIndex(2,getExpand());
                mainTodo.remove(nonExpand);
            }
        }));
        return nonExpand;
    }
    private HorizontalLayout getExpand(){
        expand.setJustifyContentMode(JustifyContentMode.END);
        expand.setAlignItems(Alignment.CENTER);
        expand.setWidth("43%");
        expand.addClickListener((divClickEvent -> {
            if (!todoDetails.isVisible()) {
                todoDetails.setVisible(true);
                todoDetails.getStyle().set("border-top", "1px solid");
                mainTodo.addComponentAtIndex(2,getNonExpand());
                mainTodo.remove(expand);
            } else{
                todoDetails.setVisible(false);
                todoDetails.getStyle().set("border-top","none");
                mainTodo.addComponentAtIndex(2,getExpand());
                mainTodo.remove(nonExpand);
            }
        }));
        return expand;
    }
}
