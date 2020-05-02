package Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Sort {
    public static Direction direction;
    private List<String> fields;
    private List<Sort> sortList;
    private boolean currentDirection;

    public Sort(boolean dir, String ...fields) {
        this.fields=new ArrayList<String>();
        this.fields.addAll(Arrays.asList(fields));
        Collections.reverse(this.fields);
        this.sortList = new ArrayList<>();
        this.sortList.add(this);
        currentDirection=dir;
    }

    public Sort(String ...fields) {
        direction=new Direction(true);
        this.fields=new ArrayList<String>();
        this.fields.addAll(Arrays.asList(fields));
        Collections.reverse(this.fields);
        this.sortList = new ArrayList<>();
        this.sortList.add(this);
        currentDirection=true;
    }

    public Sort and(Sort sort){
        this.sortList.add(0,sort);
        this.sortList.addAll(0,sort.getSortList());
        return this;
    }

    public List<Sort> getSortList(){
        return sortList;
    }

    public List<String> getFields()
    {
        return fields;
    }

    public boolean getCurrentDirection() {
        return currentDirection;
    }
}
