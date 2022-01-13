package vn.insee.util.insee;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CementManager {
    private static List<Product> DATA;
    public static int EVERY_TREE_HELP_TO_REDUCE_CO2 = 22;
    static  {
        DATA = new ArrayList<>();
        DATA.add(new Product(1,"INSEE Power-S", 612, 750, 50, Segment.HOUSING_SOLUTION));
        DATA.add(new Product(2,"INSEE Wall Pro", 517, 750, 40, Segment.HOUSING_SOLUTION));
        DATA.add(new Product(3,"Lavilla Xtra", 472, 750, 50, Segment.HOUSING_SOLUTION));
        DATA.add(new Product(4,"INSEE Extra Durable", 368, 750, 50, Segment.HOUSING_SOLUTION));
        DATA.add(new Product(5,"INSEE Power Fast ", 612, 750, 50, Segment.HOUSING_SOLUTION));
        DATA.add(new Product(14,"ECO Multi-application", 612, 750, 50, Segment.HOUSING_SOLUTION));
        DATA.add(new Product(6,"INSEE Quick Cast ", 756, 850, 50, Segment.BUILDING_INFRASTRUCTURE));
        DATA.add(new Product(7,"INSEE Stable Soil ", 368, 850, 50, Segment.BUILDING_INFRASTRUCTURE));
        DATA.add(new Product(8,"INSEE Compact Rock ", 368, 850, 50, Segment.BUILDING_INFRASTRUCTURE));
        DATA.add(new Product(9,"INSEE Extra Durable ", 368, 850, 50, Segment.BUILDING_INFRASTRUCTURE));
        DATA.add(new Product(10,"INSEE Mass Pour", 368, 850, 50, Segment.BUILDING_INFRASTRUCTURE));
        DATA.add(new Product(11,"INSEE Easy Flow", 756, 850, 50, Segment.BUILDING_INFRASTRUCTURE));

//        DATA.add(new Product(12,"INSEE Power Cast", 0, 850, 50, Segment.BUILDING_INFRASTRUCTURE));
//        DATA.add(new Product(13,"INSEE Pitu Fill ", 0, 850, 50, Segment.BUILDING_INFRASTRUCTURE));

    }


    public static Product findById(int id) {
        Optional<Product> product = DATA.stream().filter(p -> p.getId() == id).findFirst();
        return product.isPresent() ? product.get()  : null;
    }

    public static List<Product> getList() {
        return DATA;
    }



}
