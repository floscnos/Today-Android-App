package com.example.today;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.today.validation.TaskValidator;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskValidatorTest {
    @Test
    public void taskName_should_not_be_null(){
        TaskValidator taskValidator = new TaskValidator();
        String taskName = null;
        boolean result = taskValidator.nameValidator(taskName);
        assertFalse(result);
    }

    @Test
    public void taskName_should_not_be_too_long(){
        TaskValidator taskValidator = new TaskValidator();
        String taskNametooLong = "ThisTaskNameIsTooLongToUseForInput!"; //characters <= 20.
        boolean result = taskValidator.nameValidator(taskNametooLong);
        assertFalse(result);
    }

    @Test
    public void taskName_should_not_be_empty() {
        TaskValidator taskValidator = new TaskValidator();
        String emptyName = "";
        boolean result = taskValidator.nameValidator(emptyName);
        assertFalse(result);
    }

    @Test
    public void taskName_should_be_valid(){
        TaskValidator taskValidator = new TaskValidator();
        String taskName = "Deze taak is goed.";
        boolean result = taskValidator.nameValidator(taskName);
        assertTrue(result);
    }

    @Test
    public void taskNote_should_not_be_null(){
        TaskValidator taskValidator = new TaskValidator();
        String emptyNote = null;
        boolean result = taskValidator.noteValidator(emptyNote);
        assertFalse(result);
    }

    @Test
    public void taskNote_should_not_be_to_long(){
        TaskValidator taskValidator = new TaskValidator();
        String taskNoteTolong = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam dolor nisl, luctus nec tristique ut, pulvinar id sapien. Duis vel sollicitudin lectus. Sed sed enim consectetur, maximus diam non, cursus nibh.   Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos";
        boolean result = taskValidator.noteValidator(taskNoteTolong);
        assertFalse(result);
    }

    @Test
    public void taskNote_should_be_valid(){
        TaskValidator taskValidator = new TaskValidator();
        String taskNote = "De notitie past perfect.";
        boolean result = taskValidator.noteValidator(taskNote);
        assertTrue(result);
    }

    @Test
    public void taskDate_should_not_be_null(){
        TaskValidator taskValidator = new TaskValidator();
        Date emptyDate = null;
        boolean result = taskValidator.dateValidator(emptyDate);
        assertFalse(result);
    }

    @Test
    public void taskDate_should_not_be_in_past() {
        TaskValidator taskValidator = new TaskValidator();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        Date testDate = new Date();
        try {
            testDate = dateFormat.parse("01-02-2000 13:46");
        }
        catch (Exception e) {
            System.out.println(e);
        }
        boolean result = taskValidator.dateInFutureValidator(testDate);
        assertFalse(result);
    }

    @Test
    public void taskDate_should_be_in_future() {
        TaskValidator taskValidator = new TaskValidator();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        Date testDate = new Date();
        try {
            testDate = dateFormat.parse("01-02-2099 13:46");
        }
        catch (Exception e) {
            System.out.println(e);
        }
        boolean result = taskValidator.dateInFutureValidator(testDate);
        assertTrue(result);
    }

}
