package sample.hustbookstore.models.address;

import java.util.List;

public class District {
    private String level2_id;
    private String name;
    private String type;
    private List<Ward> level3s;

    public String getLevel2_id() {
        return level2_id;
    }

    public void setLevel2_id(String level2_id) {
        this.level2_id = level2_id;
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

    public List<Ward> getLevel3s() {
        return level3s;
    }

    public void setLevel3s(List<Ward> level3s) {
        this.level3s = level3s;
    }
    @Override
    public String toString() {
        return name;
    }

}
