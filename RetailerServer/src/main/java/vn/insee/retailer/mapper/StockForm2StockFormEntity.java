package vn.insee.retailer.mapper;

import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import vn.insee.jpa.entity.form.StockFormEntity;
import vn.insee.retailer.controller.form.StockForm;

import java.util.List;

public class StockForm2StockFormEntity extends PropertyMap<StockForm, StockFormEntity> {
    private static final Converter<List<Integer>, Integer[]> list2Array =
            ctx -> ctx.getSource() == null ? null : ctx.getSource().toArray(new Integer[ctx.getSource().size()]);
    @Override
    protected void configure() {
        using(list2Array).map(source.getCements()).setCements(null);
    }
}
