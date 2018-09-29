package model;

import encryption.Crypto;
import encryption.Encryption;

public class Model {
	private int key;
	private String cryptoText;
	
	Model() {
		key = 0;
	}
	
	Model(Encryption encryption, String plainText) {
		key = 0;
		cryptoText = encryption.encryptOrNull(plainText);
		System.out.println(cryptoText);
	}
	
	Model(int key, String cryptoText) {

		this.key = key;
		this.cryptoText = cryptoText;
		System.out.println(cryptoText);
	}

	String getText(Encryption encryption) {
		return encryption.decryptOrNull(cryptoText);
	}
	
	void setText(Encryption encryption, String plainText) {
		cryptoText = encryption.encryptOrNull(plainText);

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
