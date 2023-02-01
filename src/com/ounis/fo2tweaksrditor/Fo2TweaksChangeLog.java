/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ounis.fo2tweaksrditor;

import java.util.ArrayList;
import java.util.List;



class Fo2TweaksChange {
    int numLine;
    int getNumLine() {
        return this.numLine;
    }
    private String section;
    private String key;
    private String value;
    
    private List<String> lstValueChanges;
    
    Fo2TweaksChange(int aNumLine, String aSection, String aKey, String aValue) {
        this.numLine = aNumLine;
        this.section = aSection;
        this.key = aKey;
        this.value = aValue;
        
        lstValueChanges = new ArrayList<>();
    }
    
    
    void addFo2TweaksChangeValue(int aNumLine, String aNewValue) {
        if (this.numLine == aNumLine) {
            this.lstValueChanges.add(aNewValue);
        }
    }
}

public class Fo2TweaksChangeLog {
    private List<Fo2TweaksChange> values;
    
    Fo2TweaksChangeLog() {
        values = new ArrayList<Fo2TweaksChange>();
    }
    
    public boolean add(Fo2TweaksChange aValue) {
        if (!isValueOnList(aValue)) {
            values.add(aValue);
            return true;
        }
        else
            return false;
    }
    
    public boolean add(int aNumLine, String aSection, String aKey, String aValue) {
        return add(new Fo2TweaksChange(aNumLine, aSection, aKey, aValue));
    }
    
    private boolean isValueOnList(Fo2TweaksChange aValue) {
        boolean found = false;
        if (aValue != null) {
            for(Fo2TweaksChange value: values) {
                if (value.getNumLine() == aValue.getNumLine()) {
                    found = true;
                    break;
                }
            }
        }
        return found;
    }
    
    public void addValueChange(int aNumLine, String aNewValue) {
        
    }
}
