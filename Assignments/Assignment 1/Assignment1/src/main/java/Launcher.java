import controller.LoginController;
import controller.MyComponentFactory;
import view.LoginView;

/**
 * Created by Alex on 18/03/2017.
 */
public class Launcher {

    public static void main(String[] args) {
        MyComponentFactory componentFactory = MyComponentFactory.instance();
        new LoginController(new LoginView(), componentFactory.getAuthenticationService(),
                componentFactory.getClientManagementService(),
                componentFactory.getAccountManagementService(),
                componentFactory.getUserManagementService());
    }

}
