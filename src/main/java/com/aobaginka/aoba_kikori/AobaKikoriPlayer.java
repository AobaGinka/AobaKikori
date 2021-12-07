package com.aobaginka.aoba_kikori;

public class AobaKikoriPlayer {
    private String name;
    private Boolean status_kikori;
    private Boolean status_ore;


    AobaKikoriPlayer(String name){
        this.name = name;
        this.status_kikori = false;
        this.status_ore = false;
    }


    public Boolean getStatusOfKikori(){
        return this.status_kikori;
    }


    public void setStatusOfKikori(Boolean status){
        this.status_kikori = status;
    }


    public Boolean getStatusOfOre(){
        return this.status_ore;
    }


    public void setStatusOfOre(Boolean status){
        this.status_ore = status;
    }

    public String  getName(){
        return this.name;
    }
}
