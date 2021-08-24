import javafx.scene.shape.Circle;

public class Dijkstra {

    public static final String[] CITIES = {"Boston", "Worcester", "Springfield", "Cambridge",
            "Lowell", "Brockton", "Lynn", "New Bedford"};

    //public static final Circle[] POINTS = {new Circle(475,318)};

    //  0       1          2            3          4       5         6       7
    // Boston, Worcester, Springfield, Cambridge, Lowell, Brockton, Lynn, New Bedford
    public int[][] distances = {
            {0, 47, 91, 3, 30, 25, 11, 59},
            {47, 0, 53, 44, 42, 60, 57, 71},
            {91, 53, 0, 87, 93, 104, 101, 116},
            {3, 44, 87, 0, 27, 29, 15, 63},
            {30, 42, 93, 27, 0, 50, 32, 84},
            {25, 60, 104, 29, 50, 0, 35, 37},
            {11, 57, 101, 15, 32, 35, 0, 69},
            {59, 71, 116, 63, 84, 37, 69, 0}
    };

    public int[] distCompound;          // hold weight for shortest distances - these are compounded distances
    public static int[] distIndex;            // reset the index for dist, used for bubble sort
    public int[] array;                 // part of constructor


    public Dijkstra(String start){
        this.array = determineDistances(start);     // used to pass argument to constructor
    }

    // determine the index of the next smallest value
    private static int minDistance(int[] distance, boolean[] visitedList){
        int min = Integer.MAX_VALUE;
        int min_index = -1;

        for (int i = 0; i < distance.length; i++){
            if (!visitedList[i] && distance[i] <= min){
                min = distance[i];
                min_index = i;
            }
        }
        return min_index;
    }

    // runs the dijkstra algorithm on 2d array; return an array of shortest path
    private static int[] dijkstra(int[][] graph, int src){
        int[] distance = new int[graph.length];
        boolean[] visitedList = new boolean[graph.length];

        // fill in array
        for (int i = 0; i < graph.length; i++){
            distance[i] = Integer.MAX_VALUE;         // artificially high values
            visitedList[i] = false;                  // false for everything
        }

        distance[src] = 0;      // starting point = 0 miles

        for (int count = 0; count < graph.length; count++){
            int u = minDistance(distance, visitedList);      // get the index with the minimum value; must also not be visited
            visitedList[u] = true;                           // make that index true since its now visited

            for (int i = 0; i < graph.length; i++){         // sum up distance traveled so far to next closest value
                if (!visitedList[i] && graph[u][i] != 0 && distance[u] != Integer.MAX_VALUE)
                    distance[i] = distance[u] + graph[u][i];
            }
        }
        return distance;
    }

    public static int getCityIndex(String userInput){
        int cityIndex = -1;
        for (int i = 0; i < CITIES.length; i++){
            if (CITIES[i].toLowerCase().equals(userInput.toLowerCase()))
                cityIndex = i;
        }
        return cityIndex;
    }

    public static String getCity(int index){
        if (index > 7 || index < 0)
            return null;
        else
            return CITIES[index];
    }


    // return element from a 2D array
    public int getDistance(int[][] array, int start, int end){
        return array[start][end];
    }

    // create a shortest path array from algorithm. this array will be part of constructor
    private int[] determineDistances(String start){
        distCompound = dijkstra(distances, getCityIndex(start));    // fill in array using algorithm - this will be unsorted
        distIndex = new int[distCompound.length];                   // build an array for indexing dist array above, used for bubble sort
        for (int i = 0; i < distCompound.length; i++) {
            distIndex[i] = i;
        }
        return distIndex;
    }

    public void printResult(){

        System.out.println();
        // sort data points based on total distance so far
        bubbleSort(distCompound, distIndex);
        String cityStr = "City", compDistStr = "Compounding Distance", nextDistStr = "Distance to next Destination";

        // print out out a header for table
        System.out.println("|--------------------------------------------------------------------------|");
        String format = "| %1$-15s %2$-25s %3$-30s |\n";
        System.out.format(format, cityStr, compDistStr, nextDistStr);
        System.out.println("|--------------------------------------------------------------------------|");

        // print out the data in table format
        for (int i = 0; i < distCompound.length; i++){
            String dataFormat = "| %1$-15s %2$-25d %3$-30s |\n";
            if (i < distCompound.length-1) {
                String tempString = getDistance(distances, distIndex[i], distIndex[i + 1]) + " (to " + getCity(distIndex[i + 1]) + ")";
                System.out.format(dataFormat, getCity(distIndex[i]), distCompound[i], tempString);
            }
            else
                System.out.format(dataFormat, getCity(distIndex[i]), distCompound[i], "Final Destination");
        }
        System.out.println("|--------------------------------------------------------------------------|");

    }

    // sort array primarily on the data for compounding distances;
    // indexArr sorted in same order as distArr
    private static void bubbleSort(int[] distArr, int[] indexArr) {
        int temp;
        int num;
        for (int i = 0; i < distArr.length-1; i++) {
            for (int j = 0; j < distArr.length-i-1; j++) {
                if (distArr[j] > (distArr[j+1])){	// logic based on string array
                    temp = distArr[j];				// sort string array
                    distArr[j] = distArr[j+1];
                    distArr[j+1] = temp;

                    num = indexArr[j];				// sort int array in same pattern
                    indexArr[j] = indexArr[j+1];
                    indexArr[j+1] = num;
                }
            }
        }
    }
}
