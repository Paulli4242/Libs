package net.foreverdevs.utils;

public class TimeParser {
    private long miliseconds;

    public TimeParser(long miliseconds){
        this.miliseconds = miliseconds;
    }
    public TimeParser(String s) {
        
    }
    public long getMiliseconds() {
        return miliseconds;
    }
    public void setMiliseconds(long miliseconds) {
        this.miliseconds = miliseconds;
    }
}
