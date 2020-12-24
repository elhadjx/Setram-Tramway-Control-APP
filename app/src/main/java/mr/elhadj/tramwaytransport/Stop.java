package mr.elhadj.tramwaytransport;

import androidx.annotation.NonNull;

public class Stop {

    private String id;
    private String stop_name;

    public Stop(String id, String stop_name) {
        this.id = id;
        this.stop_name = stop_name;
    }

    public Stop(String stop_name) {
        this.stop_name = stop_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStop_name() {
        return stop_name;
    }

    public void setStop_name(String stop_name) {
        this.stop_name = stop_name;
    }


    @NonNull
    @Override
    public String toString() {
        return stop_name;
    }
}
