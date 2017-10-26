package SDES;

/**
 * Created by rohangoel on 10/25/17.
 */
public class Question3 {
    public static void main(String[] args) {
        byte casciiText[] = CASCII.Convert("CRYPTOGRAPHY");
        byte key[] = {0,1,1,1,0,0,1,1,0,1};
        byte plaintext[] = new byte[8];
        byte output[]= new byte[64];

        SDESimplementation sdes = new SDESimplementation();
        int k=0;
        for(int i=0;i<casciiText.length;i+=8) {

            System.out.println();
            for(int j=0,l=i;j<plaintext.length;j++,l++) {
                plaintext[j] = casciiText[l];
                System.out.print(plaintext[j] + " ");
            }

            byte cipherText[] = sdes.Encrypt(key, plaintext);

            for(int z=0;z<8;z++) {
                output[k]=cipherText[z];
                k++;
            }

        }
        System.out.println();
        for (byte out:output) {
            System.out.print(out+" ");
        }
    }
}
