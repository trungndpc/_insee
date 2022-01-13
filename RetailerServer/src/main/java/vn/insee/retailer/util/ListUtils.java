package vn.insee.retailer.util;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {

    public static List<String> putWithMaximumSize(String item, List<String> lst) {
        if (lst == null) {
            lst = new ArrayList<>();
        }
        if(lst.size() >=5){
            lst.remove(0);
        }
        return lst;
    }
}
