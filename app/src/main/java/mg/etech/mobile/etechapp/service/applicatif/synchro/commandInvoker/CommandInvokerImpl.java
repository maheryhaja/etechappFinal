package mg.etech.mobile.etechapp.service.applicatif.synchro.commandInvoker;

import android.util.Log;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.Stack;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
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
    private int stackSize;
    private boolean isRunning = false;


    private PublishSubject<OperationDto> processingSubject = PublishSubject.create();
    private PublishSubject<OperationDto> errorSubject = PublishSubject.create();

    @Bean(OperationStackSynchroSAImpl.class)
    OperationStackSynchroSA operationStackSynchroSA;

    @Bean(OperationCommandFactoryImpl.class)
    OperationCommandFactory operationCommandFactory;
    private boolean isRequestQueueing = false;

    @Override
    public void initialize() {

    }


    private void processOperation(final OperationDto operationDto) {
        OperationCommand command = operationCommandFactory
                .create(operationDto);

        Observable
                .just(command)
                .map(new Function<OperationCommand, OperationCommand>() {
                    @Override
                    public OperationCommand apply(@NonNull OperationCommand operationCommand) throws Exception {
                        processingSubject.onNext(operationDto);
                        operationCommand.execute();
                        return operationCommand;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<OperationCommand>() {
                               // on success
                               @Override
                               public void accept(OperationCommand operationCommand) throws Exception {
                                   operationCommand.onSuccess();


                                   stackSize--;
                                   isRunning = (stackSize != 0);
                                   if (stackSize == 0) {
                                       Log.d("mahery-haja", "all operation processed");
                                       processQueue();
                                   }
                               }
                           },
                        // on error
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                throwable.printStackTrace();
                                errorSubject.onNext(operationDto);
                                stackSize--;
                                isRunning = (stackSize != 0);
                                if (stackSize == 0) {
                                    Log.d("mahery-haja", "all operation processed");
                                    processQueue();

                                }
                            }
                        }
                );

    }

    protected void processQueue() {
        if (isRequestQueueing)
            launch();
        isRequestQueueing = false;
    }

    @Override
    public void processStack() {
        isRunning = true;
        Log.d("mahery-haja", "process Stack " + operationDtoStack.size());
        stackSize = operationDtoStack.size();
        while (!operationDtoStack.isEmpty()) {
            processOperation(operationDtoStack.pop());
        }
    }

    public int getStackSize() {
        return stackSize;
    }

    @Override
    public void launch() {
        if (!isRunning) {
            operationStackSynchroSA
                    .getActualList()
                    .subscribe(new Consumer<OperationDto>() {
                                   @Override
                                   public void accept(OperationDto operationDto) throws Exception {
                                       operationDtoStack.add(operationDto);
                                   }
                               },
                            new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {

                                }

                            },
                            new Action() {
                                @Override
                                public void run() throws Exception {
                                    // on complete
                                    processStack();
                                }
                            }
                    );
        } else {
            isRequestQueueing = true;
        }
    }

    @Override
    public Observable<OperationDto> onProceessingObservable() {
        return processingSubject;
    }

    @Override
    public Observable<OperationDto> onErrorObservable() {
        return errorSubject;
    }
}
