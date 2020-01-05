/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bot.models;

import bot.logger.DatabaseLogger;
import java.util.Date;

/**
 *
 * @author Erica Moisei
 */
public class HellModeState {

    private int Id;
    private Date dateTime;
    private String state;

    public HellModeState() {
    }
//<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    public int getId() {
        return Id;
    }
    
    public void setId(int Id) {
        this.Id = Id;
    }
    
    public Date getDateTime() {
        return dateTime;
    }
    
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
//</editor-fold>
    
    public boolean saveNewState(){
        DatabaseLogger db = new DatabaseLogger();
        return db.newHellMode(this);
    }
}
