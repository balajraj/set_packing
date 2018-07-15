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
           //System.out.println(line);
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
                   //System.out.println(elem+ " " + elem.length());
                   if(elem.charAt(elem.length()-1) == 'W') {
                       windowPerf =true;
                       int len = elem.length()-1;
                       //System.out.println("inside w "+len);
                       String digit ="";
                       if(len == 0 ) {
                           digit = ""+elem.charAt(0);
                       }
                       else {
                           digit = elem.substring(0,len);
                       }
                       //System.out.println(digit);
                       Integer id = Integer.parseInt(digit);
                       Passenger p = new Passenger(windowPerf,id);
                       passengers.add(p);
                   }
                   else {
                       Integer id = Integer.parseInt(elem);
                       Passenger p = new Passenger(false,id);
                       passengers.add(p);
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
