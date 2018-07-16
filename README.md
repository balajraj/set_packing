# Airline Seat Allocation 

 Seat allocation for given a set of passengers is an optimization problem that is normally faced by airlines. The problem belongs to category of set packing problem. The set packing problem in computer science is NP complete, meaning there are no known polynomial runtime algorithm available [1]. In the reference paper researchers at IBM have tried to solve the same problem using set packing approach. So a similar approach has been followed in the current code for seat allocation. Two important constraints that have been used to solve the set packing problem to ensure the overall passenger satisfaction increases and it is achieved by,  

  - Assign the window preference passengers first. 
  - Keeping the group of passengers in a single row. 


### Assumptions 
Here are some of the assumptions have been made while coding this solution. The assumptions help to keep the code simple, though the design of the code is such if the conditions has to be meet in future, the code can be easily modified to satisfy additional required functionality. 
  - There is only one passenger requests for window in a given group. 
  - The max number of passengers in a group is equal to maximum number of seats in a row. 

### Design and responsibility of modules

 One of design thought was not to allow gaps in the seat allocation to keep the solution simple. Hence the groups are assigned continously wihout creating gaps in a row. This aspect of the design can be further improved to achieve a much better customer satisfaction percentage. In the below section we discuss about two core modules which implements  the algorithm. 

> SeatAvailability is mainly responsile for assigning passengers to a given seat while satifying the constraints. The updateSeats method will first try to assign the passengers with window seat perference to window seat. It keeps tracks of seats that are free and seats already occupied. The output is list of integer, for a given group, each integer is number of passengers of group in given row, if the passengers in a group are assigned to more than one row the satisfaction goes below 100%. Also if there are less no of seats available than the list of passengers in the group an exception is thrown. If a window preference passenger cannot be assigned a window then that passenger satisfaction becomes zero and he/she will be assigned a seat like a normal passenger.There are unit test cases to verify the above scenarios. 

> SetPackingAlgo class is mainly responsible for searching the given group and picking right group based on available seats in a row so satisfaction of overal group is maintained. The seatAvailability class provides an api to get number of seats available in a given row. Based on number of seats available a window prefrence or non preference group is chosen and algorithm of seat allocation is carried out. 

### Installation

Please execute the following commands to execute the unit test cases and run the driver program. The assumption is made that mvn/java is already installed in the computer trying to run the code base. 

```sh
$ mvn clean package
$ cat src/main/resources/input.txt | java -jar target/seat_alloc-1.0-SNAPSHOT-jar-with-dependencies.jar
11 9 10 12
1 2 3 8
4 5 6 7
15 16 13 14
100%
```

#### References 

[[1] Using set packing formulation for airline seat allocation
](http://www.orsj.or.jp/~archive/pdf/e_mag/Vol.42_01_032.pdf)


