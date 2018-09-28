package model;

import Utilities.FXDialogs;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

class Password {
	private String password;
	private boolean passwordSet;
	
	Password() {
		password = "";
		passwordSet = false;
		
		getNewPassword();
	}
	
	String getPassword() {
		return password;
	}
	
	boolean passwordValid() {
		if (passwordSet) return true;
		else return false;
	}
	
	void getNewPassword() {

		passwordSet = false;
		while(!passwordSet) {
			String password = FXDialogs.passwordDialog("Password", "Please enter Database password.");

			if (password != null && password.length() != 0) {
				if (password != null && password.length() > 16) {
					password = password.substring(0, 16);

				}

				if (password != null && password.length() < 16) {
					password = String.format("%1$-" + 16 + "s", password);

				}

				passwordSet = true;
				this.password = password;
			} else {
				System.out.println("Invalid password");
			}
		}

	}
	

}
