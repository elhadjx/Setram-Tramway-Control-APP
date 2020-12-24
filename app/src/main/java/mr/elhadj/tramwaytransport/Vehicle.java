package mr.elhadj.tramwaytransport;

import androidx.annotation.NonNull;

public class Vehicle {

    private String id;
    private String vehicle_code;

    public Vehicle(String id, String vehicle_code) {
        this.id = id;
        this.vehicle_code = vehicle_code;
    }

    public Vehicle(String vehicle_code) {
        this.vehicle_code = vehicle_code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVehicle_code() {
        return vehicle_code;
    }

    public void setVehicle_code(String vehicle_code) {
        this.vehicle_code = vehicle_code;
    }

    @NonNull
    @Override
    public String toString() {
        return vehicle_code;
    }
}
