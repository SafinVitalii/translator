package com.safin.translator;

import java.net.InetAddress;

/**
 * Created by vitalii.safin on 2/2/2017.
 */
public class InternetConnectionQA {
    public boolean checkConnection() {
        try {
            InetAddress add = InetAddress.getByName("www.microsoft.com");
            if (add.isReachable(2000)) {
                System.out.println("----Connected to Translation Server----");
                return true;
            } else {
                System.out.println("----Can't connect to Translation Server----");
                return false;
            }
        } catch (Exception ex) {
            System.out.println("Internet connection test failed!");
            return false;
        }
    }
}
