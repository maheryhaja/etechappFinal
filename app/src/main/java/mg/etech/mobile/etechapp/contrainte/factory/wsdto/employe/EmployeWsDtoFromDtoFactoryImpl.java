package mg.etech.mobile.etechapp.contrainte.factory.wsdto.employe;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.io.IOException;

import mg.etech.mobile.etechapp.commun.base64.Base64Utils;
import mg.etech.mobile.etechapp.contrainte.factory.BaseFactory;
import mg.etech.mobile.etechapp.contrainte.factory.wsdto.poste.HistoryPosteWsDtoFromDtoFactory;
import mg.etech.mobile.etechapp.contrainte.factory.wsdto.poste.HistoryPosteWsDtoFromDtoFactoryImpl;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
import mg.etech.mobile.etechapp.donnee.wsdto.EmployeWsDto;

/**
 * Created by mahery.haja on 15/09/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class
EmployeWsDtoFromDtoFactoryImpl extends BaseFactory<EmployeDto, EmployeWsDto> implements EmployeWsDtoFromDtoFactory {

    @Bean(HistoryPosteWsDtoFromDtoFactoryImpl.class)
    HistoryPosteWsDtoFromDtoFactory historyPosteWsDtoFromDtoFactory;

    @RootContext
    Context context;

    @Override
    public EmployeWsDto getInstance(EmployeDto employeDto) {

        Log.d("mahery-haja", "transformation begin");

        EmployeWsDto employeWsDto = new EmployeWsDto();
        employeWsDto.setId(employeDto.getId());
        employeWsDto.setLastName(employeDto.getLastName());
        employeWsDto.setFirstName(employeDto.getFirstName());
        employeWsDto.setAlias(employeDto.getAlias());
        employeWsDto.setMatricule(employeDto.getMatricule());
        employeWsDto.setMail(employeDto.getMail());
        employeWsDto.setMale(employeDto.isMale());
        employeWsDto.setPhoto(employeDto.getPhoto());
        employeWsDto.setBirthDate(employeDto.getBirthDate());
        employeWsDto.setHiringDate(employeDto.getHiringDate());
        employeWsDto.setPostes(historyPosteWsDtoFromDtoFactory.getInstance(employeDto.getPostes()));
        employeWsDto.setPole(employeDto.getPole().getId());
        try {
            employeWsDto.setEncodedPhoto(Base64Utils.convertToBase64(Uri.parse(employeDto.getEncodedPhoto()), context));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {

        }
        Log.d("mahery-haja", "transformation " + employeWsDto.getPole());
        return employeWsDto;
    }
}
