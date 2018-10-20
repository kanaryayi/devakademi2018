# devakademi2018
*Sahibinden.com Software Challenge*
This repo built by the reason of competition of [Sahibinden.com](Sahibinden.com)

**Purpose**

With given advertisements, users and stats data, create a project strictly in 7 hours.

**Code Does**

Code is built to predict the approximate click number of a new advertisement.
1. Evaluate similarity value of "new title" with given "title"s which are obtained from given advertisements. 
2. Store click number of each given advertisements by API,
3. Consider the most similar "K" titles and its advertiments.
4. Make a ceil and floor subarrays from the most similar titles with their ids.
5. Calculate average click numbers of both id arrays.

**Keyboard Parameters**

-New Title-(String)  : The title which will be given on new advertisement.

-KNN value-(Integer) : The value that affects how many "the most similar titles" and "their click numbers" will be considered.

**Run The Code**

It's a ordinary java console application with 2 keyboard mandatory parameters.
Run Main.java without any console parameters.

**Used Algorithms and Data Structures**

[Levenshstein Distance Algorithm] (http://www.wikizeroo.com/index.php?q=aHR0cHM6Ly9lbi53aWtpcGVkaWEub3JnL3dpa2kvTGV2ZW5zaHRlaW5fZGlzdGFuY2U)
TreeMap

**Complexity**
  
  ~(t^2)+2n+c

t -> General title size
n -> Total Number of Advertisements
c -> Total Number of Clicked Advertisements
