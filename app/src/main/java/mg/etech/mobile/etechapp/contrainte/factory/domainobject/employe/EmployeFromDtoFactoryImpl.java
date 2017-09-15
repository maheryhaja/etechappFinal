package mg.etech.mobile.etechapp.contrainte.factory.domainobject.employe;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import mg.etech.mobile.etechapp.contrainte.factory.BaseFactory;
import mg.etech.mobile.etechapp.contrainte.factory.domainobject.pole.PoleFromDtoFactory;
import mg.etech.mobile.etechapp.contrainte.factory.domainobject.pole.PoleFromDtoFactoryImpl;
import mg.etech.mobile.etechapp.donnee.domainobject.Employe;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;

/**
 * Created by mahery.haja on 06/09/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class EmployeFromDtoFactoryImpl extends BaseFactory<EmployeDto, Employe> implements EmployeFromDtoFactory {


    @Bean(PoleFromDtoFactoryImpl.class)
    PoleFromDtoFactory poleFromDtoFactory;

    @Override
    public Employe getInstance(EmployeDto employeDto) {
        Employe employe = new Employe();
        employe.setId(employeDto.getId());
        employe.setLastName(employeDto.getLastName());
        employe.setFirstName(employeDto.getFirstName());
        employe.setAlias(employeDto.getAlias());
        employe.setMatricule(employeDto.getMatricule());
        employe.setMail(employeDto.getMail());
        employe.setMale(employeDto.isMale());
        employe.setPhoto(employeDto.getPhoto());
        employe.setBirthDate(employeDto.getBirthDate());
        employe.setHiringDate(employeDto.getHiringDate());
        try {
            employe.setPole(poleFromDtoFactory.getInstance(employeDto.getPole()));
        } catch (IllegalArgumentException e) {
            //pole qui n'existe pas
        }
        return employe;
    }
}
