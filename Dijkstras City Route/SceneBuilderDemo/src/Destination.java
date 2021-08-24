public class Destination {

    int num, distSoFar;
    String city, toNext;

    Destination (int num, String city, int distSoFar, String toNext){
        this.num = num;
        this.city = city;
        this.distSoFar = distSoFar;
        this.toNext = toNext;
    }

    public void setNum(int i){
        num = i;
    }

    public void setCity(String c){
        city = c;
    }

    public void setDistSoFar(int i){
        distSoFar = i;
    }

    public void setToNext(String s){
        toNext = s;
    }
}
