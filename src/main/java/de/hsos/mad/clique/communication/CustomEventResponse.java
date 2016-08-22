/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.mad.clique.communication;

/**
 *
 * @author zensd
 */
public class CustomEventResponse {
    
    long id;
    long cliqueId;
    String eventName = "";
    String eventStreet = "";
    int eventStreetnumber;
    int eventZip;
    String eventCity = "";
    String eventDescription = "";
    String eventDate = "";
    boolean open;
    boolean accepted;
    boolean canceled;
    
    public CustomEventResponse(){
        
    }
    
    public CustomEventResponse(long id, long cliqueId, String eventName, String eventStreet,
            int eventStreetnumber, int eventZip, String eventCity, String eventDescription, String eventDate,
            boolean open, boolean accepted, boolean canceled){
        this.id = id;
        this.cliqueId = cliqueId;
        this.eventName = eventName;
        this.eventStreet = eventStreet;
        this.eventStreetnumber = eventStreetnumber;
        this.eventZip = eventZip;
        this.eventCity = eventCity;
        this.eventDescription = eventDescription;
        this.eventDate = eventDate;
        this.open = open;
        this.accepted = accepted;
        this.canceled = canceled;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCliqueId() {
        return cliqueId;
    }

    public void setCliqueId(long cliqueId) {
        this.cliqueId = cliqueId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventStreet() {
        return eventStreet;
    }

    public void setEventStreet(String eventStreet) {
        this.eventStreet = eventStreet;
    }

    public int getEventStreetnumber() {
        return eventStreetnumber;
    }

    public void setEventStreetnumber(int eventStreetnumber) {
        this.eventStreetnumber = eventStreetnumber;
    }

    public int getEventZip() {
        return eventZip;
    }

    public void setEventZip(int eventZip) {
        this.eventZip = eventZip;
    }

    public String getEventCity() {
        return eventCity;
    }

    public void setEventCity(String eventCity) {
        this.eventCity = eventCity;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
}
