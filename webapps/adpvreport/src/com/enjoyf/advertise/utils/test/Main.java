package com.enjoyf.advertise.utils.test;

import com.enjoyf.advertise.utils.IPSeeker;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  14-3-19 下午12:51
 * Description:
 */
public class Main {

    public static void main(String[] args) {
        System.out.println(IPSeeker.getInstance().getAddress("106.3.78.243"));
    }
}
