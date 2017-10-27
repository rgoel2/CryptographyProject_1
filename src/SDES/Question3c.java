package SDES;
import TripleSDES.TripleSDES;
/**
 * Created by rohangoel on 10/26/17.
 */
public class Question3c {

    public static void main(String[] args) {
        SDES sdes = new SDES();
        Question3b q = new Question3b();
        byte[] key_1, key_2,plainText;
        byte[] cipherText = new byte[8];
        byte[] plainTextFull = new byte[368];

        String message = "00011111100111111110011111101100111000000011001011110010101010110001011101001101000000110011010111111110000000001010111111000001010010111001111001010101100000110111100011111101011100100100010101000011001100101000000101111011000010011010111100010001001000100001111100100000001000000001101101000000001010111010000001000010011100101111001101111011001001010001100010100000";
        byte cipherTextOutput[] = sdes.stringToByte(message);

        for (int i = 0; i < 1024; i++) {
            key_1 = q.integerToByte(i, 10);
            for (int j = 0; j < 1024; j++) { // key
                int r=0;
                key_2 = q.integerToByte(j, 10);  // takes 10 each round

                // printing both Keys combination on console
                for (byte k : key_1)
                    System.out.print(k);
                System.out.print(" ");
                for (byte k : key_2)
                    System.out.print(k);
                System.out.println();


                for (int k = 0; k < cipherTextOutput.length; k += 8) {

                    for (int m = 0, l = k; m < cipherText.length; m++, l++)
                        cipherText[m] = cipherTextOutput[l];
                        plainText = TripleSDES.tripleSDESDecrypt(cipherText, key_1, key_2);

                    for (int z = 0; z < 8; z++) {
                        plainTextFull[r] = plainText[z];
                        r++;
                    }
                }
                System.out.println();
                System.out.println(CASCII.toString(plainTextFull));
                System.out.println();
            }
        }
    }
}

