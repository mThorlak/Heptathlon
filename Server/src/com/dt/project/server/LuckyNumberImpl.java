package com.dt.project.server;

import com.dt.project.api.LuckyNumber;
import java.rmi.RemoteException;

public class LuckyNumberImpl implements LuckyNumber {
    private int luckyNumber = 100;

    @Override
    public String process(String value) throws RemoteException {
        int num;
        try {
            num = Integer.parseInt(value);
        } catch (Exception e) {
            return "Please input a number";
        }

        if (num == luckyNumber) {
            return "Congratulation !!!";
        }
        else {
            return "Sorry, please try again";
        }
    }
}
