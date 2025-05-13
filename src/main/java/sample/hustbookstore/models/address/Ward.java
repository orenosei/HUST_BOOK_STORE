package sample.hustbookstore.models.address;

public class Ward {
    private String level3_id;
    private String name;
    private String type;

    public String getLevel3_id() {
        return level3_id;
    }

    public void setLevel3_id(String level3_id) {
        this.level3_id = level3_id;
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
    @Override
    public String toString() {
        return name;
    }

}
