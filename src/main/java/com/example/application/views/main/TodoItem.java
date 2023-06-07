package com.example.application.views.main;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

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
    public TodoItem(String id, boolean done, String todoTitle, boolean deleted){
        this.id = id;
//        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        expand = new Div();
        expand.addClickListener((divClickEvent -> {
            if(!todoDetails.isVisible())
                todoDetails.setVisible(true);
            else
                todoDetails.setVisible(false);
        }));
        expand.setWidth("36%");
        setAlignItems(Alignment.CENTER);
        add(mainTodo(done,todoTitle,deleted),todoDetails());
        setWidthFull();
        this.getStyle().set("border-style","solid");
        setId(id);
    }
    private TextField todoTitle(String todoTitle){
        todoTitleTextField = new TextField();
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
        todoDeletedButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        todoDeletedButton.addClickListener(buttonClickEvent -> {
            TodoModel.update(id, String.valueOf(true),"deleted");
            this.removeFromParent();
        });
        return todoDeletedButton;
    }
    private HorizontalLayout todoDetails(){
        todoDetails = new HorizontalLayout();
        todoDetails.setWidth("50%");
        todoDetails.setVisible(false);
        todoNotes = new TextArea("Notes");
        todoNotes.setWidthFull();
        todoToday = new Button("Today");
        todoTomorrow = new Button("Tomorrow");
        todoDate = new DatePicker("No Date Set");
        todoPriority = new Select("Priority",(event)->{
            System.out.println(event.getValue());},"None","High","Medium","Low");
        VerticalLayout vlayout = new VerticalLayout();
        vlayout.setAlignItems(FlexComponent.Alignment.END);
        HorizontalLayout hLayout = new HorizontalLayout(todoToday,todoTomorrow,todoDate);
        hLayout.setAlignItems(Alignment.BASELINE);
        vlayout.add(hLayout,todoPriority);
        todoDetails.add(todoNotes,vlayout);
        return todoDetails;
    }
    private HorizontalLayout mainTodo(boolean done,String todoTitle,boolean deleted){
        mainTodo = new HorizontalLayout();
        mainTodo.setWidth("50%");
        mainTodo.add(todoDone(done),todoTitle(todoTitle),expand,todoDeleted(deleted));
        return mainTodo;
    }
}
