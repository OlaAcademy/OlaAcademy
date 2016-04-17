package com.snail.olaxueyuan.protocol.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by tianxiaopeng on 16/3/19.
 */
public class MCQuestion implements Serializable {

    public String id;
    public String question;
    public String allcount;
    public String rightcount;
    public String avgtime;
    public ArrayList<MCOption> optionList;

}
