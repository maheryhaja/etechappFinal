package mg.etech.mobile.etechapp.contrainte.factory;

import org.modelmapper.ModelMapper;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by mahery.haja on 31/08/2017.
 */
public class BaseFactory<SOURCE, DESTINATION> implements DtoFactory<SOURCE, DESTINATION> {

    private ModelMapper modelMapper = new ModelMapper();

    @Override

    public DESTINATION getInstance() {
        return null;
    }

    @Override
    public DESTINATION getInstance(SOURCE dObj) {
        return (DESTINATION) modelMapper.map(dObj, getClassName(1));
    }

    @Override
    public List<DESTINATION> getInstance(Collection<SOURCE> dObjs) {
        List<DESTINATION> reps = new ArrayList<>();
        for(SOURCE elem: dObjs)
            reps.add(getInstance(elem));
        return reps;
    }

    private Class getClassName(int i) {
        return ((Class<DESTINATION>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[i]);
    }
}
