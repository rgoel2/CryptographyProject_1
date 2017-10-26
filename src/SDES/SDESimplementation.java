package SDES;

public class SDESimplementation {

    public static byte[] Encrypt(byte[] rawkey, byte[] plaintext){
		SDES sdes = new SDES();
		sdes.keyGenerator(rawkey);

		return (sdes.finalPermutation(sdes.fK(sdes.switchFunction(sdes.fK(
		        sdes.initialPermutation(plaintext),sdes.K1)),sdes.K2)));
	}

	public static byte[] Decrypt(byte[] rawkey, byte[] ciphertext){
        SDES sdes = new SDES();
        sdes.keyGenerator(rawkey);

        return (sdes.finalPermutation(sdes.fK(sdes.switchFunction(sdes.fK(
                sdes.initialPermutation(ciphertext),sdes.K2)),sdes.K1)));
	}

    public static void main(String[] args) {
        byte key[] = {1,1,1,0,0,0,1,1,1,0};
        byte plaintext[] = {0,1,0,1,0,1,0,1};
        byte CasciiText[] = new byte[64];
        byte Output[] = new byte[64];

        int k=0;
        for(int i=0;i<CasciiText.length;i+=8) {
            for(int j=0;j<plaintext.length;i++)
                plaintext[j]=CasciiText[i];
                byte cipherText[] = Encrypt(key, plaintext);

                for(int l=0;l<8;l++) {
                    Output[k]=cipherText[l];
                    k++;
                }

        }

        System.out.println("Cipher text after Enryption");
        byte cipherText[] = Encrypt(key, plaintext);
        for (byte cipher:cipherText)
            System.out.print(cipher+" ");
            System.out.println();

        System.out.println("Plain text after Decryption");
        byte plainText[] = Decrypt(key,cipherText);
        for (byte plain:plainText) {
            System.out.print(plain+" ");
        }
    }

}
