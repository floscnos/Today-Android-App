package com.example.today.validation;

import java.util.Date;

public class TaskValidator {

    public boolean nameValidator(String taskName) {
        if (taskName == null || taskName.isEmpty() || taskName.length() > 20) {
            return false;
        } else {
            return true;
        }
    }

    public boolean noteValidator(String taskNote){
        if(taskNote == null || taskNote.length() > 280){
            return false;
        }  else {
            return true;
        }
    }

    public boolean dateValidator(Date taskDate){
        if(taskDate == null){
            return false;
        }
        return true;
    }

    public boolean dateInFutureValidator(Date taskDate) {
        Date now = new Date();
        if(!taskDate.after(now)){
            return false;
        }
        return true;
    }
}