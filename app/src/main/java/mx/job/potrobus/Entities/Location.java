package mx.job.potrobus.Entities;

public class Location {
    private int id;
    private double lat;
    private double lng;

    public Location(double lat, double lng){
        this.lat = lat;
        this.lng = lng;
    }

    public int getId() {
        return id;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
