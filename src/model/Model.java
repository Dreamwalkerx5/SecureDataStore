package model;

import encryption.Crypto;

public class Model {
	private int key;
	private String cryptoText;
	
	Model() {
		key = 0;
	}
	
	Model(Password password, String plainText) {
		key = 0;
		cryptoText = Crypto.encrypt(plainText, password.getPassword());
	}
	
	Model(int key, String cryptoText) {

		this.key = key;
		this.cryptoText = cryptoText;
		
	}

	String getText(Password password) {
		return Crypto.decrypt(cryptoText, password.getPassword());
	}
	
	void setText(Password password, String plainText) {
		cryptoText = Crypto.encrypt(plainText, password.getPassword());
	}
	
	String getCryptoText() {
		return cryptoText;
	}
	
	void setKey(int key) {
		this.key = key;
	}
	
	int getKey() {
		return key;
	}
	
}
