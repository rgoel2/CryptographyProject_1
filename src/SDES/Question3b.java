package SDES;

import java.util.Arrays;

/**
 * Created by rohangoel on 10/25/17.
 */
public class Question3b {

    public byte[] integerToByte(int input, int length) {
        byte convert[] = new byte[length];
        SDES sdes = new SDES();
        String binaryString = Integer.toBinaryString(input);
        byte convertInt[] = sdes.stringToByte(binaryString);
        int len = convertInt.length;
        for (int j = length - len, l = 0; l < len; j++, l++) {
            convert[j] = convertInt[l];
        }
        return convert;
    }

    public static void main(String[] args) {
        SDES sdes = new SDES();
        SDESimplementation sdesimp = new SDESimplementation();
        Question3b q = new Question3b();
        byte[] key = new byte[10], plaintext, cipher;
        byte[] plain2 = new byte[8];
        byte[] plain = new byte[952];

        String message = "1011011001111001001011101111110000111110100000000001110111010001111011111101101100010011000000101101011010101000101111100011101011010111100011101001010111101100101110000010010101110001110111011111010101010100001100011000011010101111011111010011110111001001011100101101001000011011111011000010010001011101100011011110000000110010111111010000011100011111111000010111010100001100001010011001010101010000110101101111111010010110001001000001111000000011110000011110110010010101010100001000011010000100011010101100000010111000000010101110100001000111010010010101110111010010111100011111010101111011101111000101001010001101100101100111001110111001100101100011111001100000110100001001100010000100011100000000001001010011101011100101000111011100010001111101011111100000010111110101010000000100110110111111000000111110111010100110000010110000111010001111000101011111101011101101010010100010111100011100000001010101110111111101101100101010011100111011110101011011";
        byte cipherTextOutput[] = sdes.stringToByte(message);
        ; // takes 8bit from message each round


        for (int a = 0; a < 1024; a++) { // key
            int r = 0, flag = 0;
            key = q.integerToByte(a, 10);  // takes 10 each round
            for (byte k : key)
                System.out.print(k);
            System.out.println();
            for (int i = 0; i < cipherTextOutput.length; i += 8) {
                for (int j = 0, l = i; j < plain2.length; j++, l++)
                    plain2[j] = cipherTextOutput[l];

                byte cipherText[] = sdesimp.Decrypt(key, plain2);

                for (int z = 0; z < 8; z++) {
                    plain[r] = cipherText[z];
                    r++;
                }


            }
            System.out.println();
            System.out.println(CASCII.toString(plain));
            System.out.println();

        }
    }
}

