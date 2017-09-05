package mg.etech.mobile.etechapp.contrainte.factory;

import org.modelmapper.ModelMapper;
import org.modelmapper.internal.typetools.TypeResolver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by mahery.haja on 31/08/2017.
 */
public class BaseFactory<SOURCE, DESTINATION> implements DtoFactory<SOURCE, DESTINATION> {

    private ModelMapper modelMapper = new ModelMapper();
    protected Class<SOURCE> sourceClass;
    protected Class<DESTINATION> destinationClass;

    public BaseFactory() {
        Class<?>[] typeArguments = TypeResolver.resolveRawArguments(BaseFactory.class, getClass());
        this.sourceClass = (Class<SOURCE>) typeArguments[0];
        this.destinationClass = (Class<DESTINATION>) typeArguments[1];
    }

    @Override
    public DESTINATION getInstance() {
        DESTINATION destination = null;
        try {
            destination = (DESTINATION) Class.forName(destinationClass.toString()).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return destination;
    }

    @Override
    public DESTINATION getInstance(SOURCE dObj) {
        return modelMapper.map(dObj, destinationClass);
    }

    @Override
    public List<DESTINATION> getInstance(Collection<SOURCE> dObjs) {
        List<DESTINATION> reps = new ArrayList<>();
        for (SOURCE elem : dObjs)
            reps.add(getInstance(elem));
        return reps;
    }

}
