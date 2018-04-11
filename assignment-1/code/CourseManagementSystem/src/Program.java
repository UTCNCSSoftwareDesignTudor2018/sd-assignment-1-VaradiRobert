import java.sql.SQLException;

import service.facade.ApplicationFacade;
import view.views.LoginView;

public class Program {
	
	public static void main(String[] args) throws SQLException {
		
		new LoginView(new ApplicationFacade());	
	}
	
}
