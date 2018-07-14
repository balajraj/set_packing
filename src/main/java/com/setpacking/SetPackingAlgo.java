package com.setpacking;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class SetPackingAlgo {

    private List<Group> winPrefgroup  = null;
    private List<Group> noPrefGroup = null;
    private Map<Group,Boolean> winPrefPrcoessed = null;
    private Map<Group,Boolean> noPrefProceeded = null;
    private Map<Integer,List<Group>> winPrefGrpMap = null;
    private Map<Integer,List<Group>> noPrefGrpMap = null;

    public SetPackingAlgo(Map<Integer,List<Group>> winPrefgroup,Map<Integer,List<Group>> noPrefGroup) {
        this.winPrefGrpMap = winPrefgroup;
        this.noPrefGrpMap = noPrefGroup;
    }






    public double getAllocationSatisfaction(int prevParition) {


        Group first = winPrefgroup.get(n);



        return 0.0;
    }
}
