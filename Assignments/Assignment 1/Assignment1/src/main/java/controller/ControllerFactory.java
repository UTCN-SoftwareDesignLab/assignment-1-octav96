package controller;



import view.AccountView;
import view.ClientView;
import view.UserView;

public class ControllerFactory {

    private final AdministratorController administratorController;

    private final EmployeeController employeeController;

    private final MyComponentFactory myComponentFactory;

    private static ControllerFactory instance;


    public static ControllerFactory instance(){
        if(instance == null){
            instance = new ControllerFactory();
        }
        return instance;
    }

    private ControllerFactory(){
        this.myComponentFactory = MyComponentFactory.instance();
        ClientView clientView = new ClientView();
        AccountView accountView = new AccountView();
        UserView userView = new UserView();
        this.employeeController = new EmployeeController(clientView, accountView,
                myComponentFactory.getClientManagementService(), myComponentFactory.getAccountManagementService());
        this.administratorController = new AdministratorController(userView, myComponentFactory.getUserManagementService());


    }

    public AdministratorController getAdministratorController() {
        return administratorController;
    }

    public EmployeeController getEmployeeController() {
        return employeeController;
    }
}
