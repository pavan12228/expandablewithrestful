package justjava.android.example.packagecom.Expandabale.Pojo;

public class Child {

    private String Name;
    private String price;
    private  boolean checked;
    private  boolean selected;
    private int subCategoryId;

    public int getBikesubCategoryId() {
        return subCategoryId;
    }

    public void setBikesubCategoryId(int bikesubCategoryId) {
        this.subCategoryId = bikesubCategoryId;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getName() {
        return Name;
    }


}