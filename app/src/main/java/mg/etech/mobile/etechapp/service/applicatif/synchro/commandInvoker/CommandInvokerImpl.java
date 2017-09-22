package mg.etech.mobile.etechapp.service.applicatif.synchro.commandInvoker;

import android.util.Log;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.Stack;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import mg.etech.mobile.etechapp.donnee.dto.OperationDto;
import mg.etech.mobile.etechapp.service.applicatif.operation.commands.OperationCommand;
import mg.etech.mobile.etechapp.service.applicatif.operation.commands.factory.OperationCommandFactory;
import mg.etech.mobile.etechapp.service.applicatif.operation.commands.factory.OperationCommandFactoryImpl;
import mg.etech.mobile.etechapp.service.applicatif.synchro.operationStack.OperationStackSynchroSA;
import mg.etech.mobile.etechapp.service.applicatif.synchro.operationStack.OperationStackSynchroSAImpl;

/**
 * Created by mahery.haja on 22/09/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class CommandInvokerImpl implements CommandInvoker {

    private Stack<OperationDto> operationDtoStack = new Stack<>();

    @Bean(OperationStackSynchroSAImpl.class)
    OperationStackSynchroSA operationStackSynchroSA;

    @Bean(OperationCommandFactoryImpl.class)
    OperationCommandFactory operationCommandFactory;

    @Override
    public void initialize() {
        operationStackSynchroSA
                .getActualList()
                .subscribe(new Consumer<OperationDto>() {
                    @Override
                    public void accept(OperationDto operationDto) throws Exception {
                        operationDtoStack.add(operationDto);
                    }
                });

        operationStackSynchroSA
                .onAddObservable()
                .subscribe(new Consumer<OperationDto>() {
                    @Override
                    public void accept(OperationDto operationDto) throws Exception {
                        operationDtoStack.add(operationDto);
                    }
                });
    }


    private void processOperation(final OperationDto operationDto) {
        final OperationCommand command = operationCommandFactory
                .create(operationDto);

        Observable
                .fromCallable(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        command.execute();
                        return true;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                               // on success
                               @Override
                               public void accept(Boolean aBoolean) throws Exception {
                                   Log.d("mahery-haja", "Operation Process Success");
                                   operationStackSynchroSA.notifySuccess(operationDto);
                               }
                           },
                        // on error
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.d("mahery-haja", "operation process Failed");
                                throwable.printStackTrace();
                                operationStackSynchroSA.notifyError(operationDto);
                            }
                        }
                );

    }

    @Override
    public void processStack() {
        Log.d("mahery-haja", "process Stack " + operationDtoStack.size());
        while (!operationDtoStack.isEmpty()) {
            processOperation(operationDtoStack.pop());
        }
    }
}
