package com.setpacking;
import java.util.*;

public class InputDriver {

   public static void main(String [] args) {
       Scanner scan = new Scanner(System.in);
       int rowSize = scan.nextInt();
       int numRows = scan.nextInt();
       String line = scan.nextLine();
       Map<Integer,Stack<Group>> winGrpPref = new HashMap<>();
       Map<Integer,Stack<Group>> noGrpPref = new HashMap<>();
       while( line != null ) {
           String [] groupInfo = line.split(" ");
           List<Passenger> passengers = new ArrayList<Passenger>();
           boolean winPerf =false;
           for(int i=0; i < groupInfo.length; ++i) {
               String elem = groupInfo[i];
               if(elem.charAt(elem.length()-1) == 'W') {
                   winPerf =true;
                   Integer id = Integer.parseInt(elem.substring(0,elem.length()-2));
                   Passenger p = new Passenger(winPref,id);
                   passengers.add(p);
               }
               Integer id = Integer.parseInt(elem);
               Passenger p = new Passenger(false,id);
               passengers.add(p);
           }
           Group grp = new Group(passengers);
           if (winPref) {
               addElementsToMap(winGrpPref,grp) ;
           }
           else {
               addElementsToMap(noGrpPref,grp);
           }
           line = scan.nextLine();
       }
       SetPackingAlgo setpack = new SetPackingAlgo(rowSize,numRows,rowSize);
       double satisfaction = setpack.getAllocationSatisfaction(winGrpPref,noGrpPref);
       setpack.printAssignment();
       System.out.println((int)satisfaction * 100+"%");

   }

   public static void addElementsToMap(Map<Integer,Stack<Group>> prefGrp,Group newGroup) {
       if(prefGrp.containsKey(newGroup.getSize())) {
           Stack<Group> sGrp = prefGrp.get(newGroup.getSize());
           sGrp.push(newGroup);
       }
       else{
           Stack<Group> sGrp = new Stack<>();
           sGrp.push(newGroup);
           prefGrp.put(newGroup.getSize(),sGrp);
       }
   }
}
