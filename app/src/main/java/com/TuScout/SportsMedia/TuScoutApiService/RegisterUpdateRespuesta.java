package com.TuScout.SportsMedia.TuScoutApiService;

import com.TuScout.SportsMedia.Models.RegisterUpdateAns;
import com.TuScout.SportsMedia.Models.Videos;

import java.util.ArrayList;

public class RegisterUpdateRespuesta {

    private ArrayList<RegisterUpdateAns> data;

    public ArrayList<RegisterUpdateAns> getData() {
        return data;
    }

    public void setData(ArrayList<RegisterUpdateAns> data) {
        this.data = data;
    }

}
