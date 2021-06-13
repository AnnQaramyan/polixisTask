#The whole purpose of the program is to extract data from zip file and to store that data in the database efficiently :)


The program has a User class in model package, which includes descriptions of that class, 
and a number of constructors and functions.

The program has service package , which in turn  has a UserService interface  and it's implementation, UserServiceImpl class.

In UserServiceImpl , I open the zip file then unzip it , take the data and save it in the Database, 
where UserRepository interface helps me.

To make filling database more efficient, I used Spring Batch, which allows to take a certain amount of data and save it
at once (the amount of data can be determined by us).

In the program I use MySql Database and H2 Database for test. In both cases, Batch works very well.

Running the program , we can see the statistics of JDBC in the console :) 

To do all this I needed some dependencies, that I added in pom.xml.

   