package com.TuScout.SportsMedia.TuScoutApiService;

import com.TuScout.SportsMedia.Models.User;

import java.util.ArrayList;

public class LoginRespuesta {

    private ArrayList<User> data;

    public ArrayList<User>  getData() {
        return data;
    }

    public void setData(ArrayList<User>  data) {
        this.data = data;
    }

}
