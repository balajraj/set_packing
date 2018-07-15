# Airline Seat Allocation 

 Seat allocation for given a set of passengers is an optimization problem that is normally faced by airlines. The problem belongs to category of set packing problem. The set packing problem in computer science is NP complete, meaning there are no known polynomial runtime algorithm available [1]. In the refernce paper researchers at IBM have tried to solve the same problem using set packing approach. So a similar approach has been followed in the current code for seat allocation. Two important constraints that have been used to solve the set packing problem to ensure the overall satisfaction increases is achieved by,  

  - Assign the window preference passengers first. 
  - Keep the group of passengers in a single row. 


# Assumptions 
Here some of the assumptions have been made while coding this solution. The assumptions help to give the code simple, though the design of the code is such if the conditions has to be relaxed in future, the code can be easily modified to satisfy additional functionality. 
  - There is only one passenger requests for window in a given group. 
  - The max numbner of passengers in a group is equal to maximum number of seats in a row. 

### Design and responsibility of modules

> SeatAvailability is mainly responsile for assigning passengers to a given seat while satifying the constraints. 

> SetPackingAlgo class is mainly responsible for searching the given group and picking right group so satisfaction of overal group is mainted.  syntax is to make it as readable

### Installation

Please execute the following commands to execute the unit test cases and run the driver program. The assumption is made is mvn/java is already installed in the computer trying to run the problem. 

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

[[1] Using set packing Formulation for airline seat allocation
](http://www.orsj.or.jp/~archive/pdf/e_mag/Vol.42_01_032.pdf)


