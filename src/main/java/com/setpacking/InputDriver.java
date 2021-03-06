package com.setpacking;
import java.util.*;

public class InputDriver {

   public static void main(String [] args) {
       Scanner scan = new Scanner(System.in);
       int rowSize = 0;
       int numRows = 0;
       Map<Integer,Stack<Group>> winGrpPref = new HashMap<>();
       Map<Integer,Stack<Group>> noGrpPref = new HashMap<>();
       int lineCnt=0;
       while( scan.hasNextLine() ) {
           String line = scan.nextLine();
           String [] groupInfo = line.split(" ");
           if (lineCnt == 0 ) {
               rowSize = Integer.parseInt(groupInfo[0]);
               numRows = Integer.parseInt(groupInfo[1]);
               lineCnt++;
           }
           else {
               List<Passenger> passengers = new ArrayList<Passenger>();
               boolean windowPerf =false;
               for(int i=0; i < groupInfo.length; ++i) {
                   String elem = groupInfo[i];
                   if(elem.charAt(elem.length()-1) == 'W') {
                       windowPerf =true;
                       int len = elem.length()-1;
                       String digit =elem.substring(0,len);
                       addPassengers(digit,passengers,windowPerf);
                   }
                   else {
                       addPassengers(elem,passengers,false);
                   }
               }
               Group grp = new Group(passengers);
               if (windowPerf) {
                   addElementsToMap(winGrpPref,grp) ;
               }
               else {
                   addElementsToMap(noGrpPref,grp);
               }
           }
       }
       SetPackingAlgo setpack = new SetPackingAlgo(rowSize,numRows,rowSize);
       double satisfaction = setpack.getAllocationSatisfaction(winGrpPref,noGrpPref);
       setpack.printAssignment();
       System.out.println((int)satisfaction * 100+"%");

   }

   public static void addPassengers(String elem,List<Passenger> passengers, boolean winPref) {
       Integer id = Integer.parseInt(elem);
       Passenger p = new Passenger(winPref,id);
       passengers.add(p);
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
