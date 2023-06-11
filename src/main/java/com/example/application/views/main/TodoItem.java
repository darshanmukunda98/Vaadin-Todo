package com.example.application.views.main;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.datepicker.DatePickerVariant;
import com.vaadin.flow.component.html.Div;
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

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class TodoItem extends VerticalLayout {
    public TextField todoTitleTextField;
    public Checkbox todoDoneCheckBox;
    public Button todoDeletedButton;
    public TextArea todoNotes;
    public Button todoToday;
    public Button todoTomorrow;
    public DatePicker todoDate;
    public Select todoPriority;
    public HorizontalLayout todoDetails;
    public HorizontalLayout mainTodo;
    public Div expand;
    private String id;
    public TodoItem(String id, boolean done, String todoTitle, boolean deleted,String notes,String date,String priority){
        this.id = id;
        setAlignItems(Alignment.CENTER);
        add(mainTodo(done,todoTitle,deleted),todoDetails(notes,date,priority));
        setWidthFull();
//        this.getStyle().set("background-color","#808080");
        setId(id);
        getStyle().set("border","1px solid");
        setSpacing(false);
        setPadding(false);
    }
    private TextField todoTitle(String todoTitle){
        todoTitleTextField = new TextField();
        todoTitleTextField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        todoTitleTextField.setWidth("50%");
        todoTitleTextField.setValue(todoTitle);
        todoTitleTextField.addValueChangeListener(event->{
            TodoModel.update(id,event.getValue(),"title");
        });
        return todoTitleTextField;
    }
    private Checkbox todoDone(boolean done){
        todoDoneCheckBox = new Checkbox();
        todoDoneCheckBox.setValue(done);
        todoDoneCheckBox.addValueChangeListener(event ->{
            if(event.getValue()) {
                todoTitleTextField.getStyle().set("text-decoration", "line-through");
                todoTitleTextField.setReadOnly(true);
            }
            else {
                todoTitleTextField.getStyle().set("text-decoration", "none");
                todoTitleTextField.setReadOnly(false);
            }
            TodoModel.update(id, String.valueOf(event.getValue()),"done");
        });
        return todoDoneCheckBox;
    }
    private Button todoDeleted(boolean deleted){
        todoDeletedButton = new Button(new Icon(VaadinIcon.CLOSE));
        todoDeletedButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        todoDeletedButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        todoDeletedButton.addClickListener(buttonClickEvent -> {
            TodoModel.update(id, String.valueOf(true),"deleted");
            this.removeFromParent();
        });
        return todoDeletedButton;
    }
    private TextArea todoNotes(String notes){
        todoNotes = new TextArea("Notes");
        todoNotes.addThemeVariants(TextAreaVariant.LUMO_SMALL);
        todoNotes.setWidthFull();
        todoNotes.setValue(notes);
        todoNotes.addValueChangeListener(event->{
           TodoModel.updateNotes(id,event.getValue());
        });
        return todoNotes;
    }
    private Button todoToday(){
        todoToday = new Button("Today");
        todoToday.addThemeVariants(ButtonVariant.LUMO_SMALL);
        todoToday.addClickListener(buttonClickEvent -> {
           TodoModel.updateDate(id,LocalDate.now().toString());
           todoDate.setValue(LocalDate.now());
        });
        return todoToday;
    }
    private Button todoTomorrow(){
        todoTomorrow = new Button("Tomorrow");
        todoTomorrow.addThemeVariants(ButtonVariant.LUMO_SMALL);
        todoTomorrow.addClickListener(buttonClickEvent -> {
            TodoModel.updateDate(id,LocalDate.now().plusDays(1).toString());
            todoDate.setValue(LocalDate.now().plusDays(1));
        });
        return todoTomorrow;
    }
    private DatePicker todoDate(String date){
        todoDate = new DatePicker("Due Date");
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
        todoPriority = new Select<>();
        todoPriority.addThemeVariants(SelectVariant.LUMO_SMALL);
        todoPriority.setLabel("Priority");
        todoPriority.setItems(List.of("None","High","Medium","Low"));
        todoPriority.setValue(priority);
        todoPriority.addValueChangeListener(event->{
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
        mainTodo.setWidthFull();
        mainTodo.setPadding(false);
        mainTodo.setSpacing(false);
        expand = new Div();
        expand.addClickListener((divClickEvent -> {
            if (!todoDetails.isVisible()) {
                todoDetails.setVisible(true);
                todoDetails.getStyle().set("border-top", "1px solid");
            } else{
                todoDetails.setVisible(false);
                todoDetails.getStyle().set("border-top","none");
            }
        }));
        expand.setWidth("43%");
        mainTodo.add(todoDone(done),todoTitle(todoTitle),expand,todoDeleted(deleted));
        return mainTodo;
    }
}
