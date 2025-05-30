package sample.hustbookstore.models.addressAPI;

import java.util.List;

public class Province {
    private String level1_id;
    private String name;
    private String type;
    private List<District> level2s;

    public String getLevel1_id() {
        return level1_id;
    }

    public void setLevel1_id(String level1_id) {
        this.level1_id = level1_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<District> getLevel2s() {
        return level2s;
    }

    public void setLevel2s(List<District> level2s) {
        this.level2s = level2s;
    }
    @Override
    public String toString() {
        return name;
    }

}
