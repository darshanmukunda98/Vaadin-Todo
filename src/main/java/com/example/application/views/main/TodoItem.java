package com.example.application.views.main;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class TodoItem extends HorizontalLayout {
    public TextField todoTitleTextField;
    public Checkbox todoDoneCheckBox;
    public Button todoDeletedButton;
    private String id;
    public TodoItem(String id, boolean done, String todoTitle, boolean deleted){
        this.id = id;
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        add(todoDone(done),todoTitle(todoTitle),todoDeleted(deleted));
        setWidthFull();
        setId(id);
        updateTodoItem();
    }
    private TextField todoTitle(String todoTitle){
        todoTitleTextField = new TextField();
        todoTitleTextField.setWidth("50%");
        todoTitleTextField.setValue(todoTitle);
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
    private void updateTodoItem(){
        todoTitleTextField.addValueChangeListener(event->{
            TodoModel.update(id,event.getValue(),"title");
        });
        todoDoneCheckBox.addValueChangeListener(event->{
            TodoModel.update(id, String.valueOf(event.getValue()),"done");
        });
    }
}
