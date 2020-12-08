package db;

import shelter.models.ParentInfo;

import java.util.ArrayList;

public class EarthquakeChecker extends Thread{
    DBController dbc = DBController.getDBController();
    @Override
    public void run() {
        while(true) {
            try {
                dbc.insertDummyEarthQuake();
                ArrayList<ParentInfo> parent_in_danger = (ArrayList<ParentInfo>) dbc.getParentInfoNearEarthquake();

                for (ParentInfo info : parent_in_danger) {
                    dbc.getShelterNearParent(info);
                    dbc.getUserInfoFrom(info);
                }
                Thread.sleep(1000 * 15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
