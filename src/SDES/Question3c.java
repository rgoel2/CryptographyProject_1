package SDES;
import TripleSDES.TripleSDES;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by rohangoel on 10/26/17.
 */
public class Question3c {

    public static void main(String[] args) {
        SDES sdes = new SDES();
        SDESimplementation sdesimp = new SDESimplementation();
        Question3b q = new Question3b();
        byte[] key_1 = new byte[10];
        byte[] key_2 = new byte[10], plaintext;
        byte[] cipherText = new byte[8];
        byte[] plainTextFull = new byte[368];

        String message = "00011111100111111110011111101100111000000011001011110010101010110001011101001101000000110011010111111110000000001010111111000001010010111001111001010101100000110111100011111101011100100100010101000011001100101000000101111011000010011010111100010001001000100001111100100000001000000001101101000000001010111010000001000010011100101111001101111011001001010001100010100000";
        byte cipherTextOutput[] = sdes.stringToByte(message);
        ; // takes 8bit from message each round

        BufferedWriter output=null;
//        try{
//            File file = new File("example.txt");
//            output = new BufferedWriter(new FileWriter(file));


        for (int i = 21; i < 30; i++) {
            key_1 = q.integerToByte(i, 10);
            for (int j = 0; j < 1024; j++) { // key
                int r=0;
                key_2 = q.integerToByte(j, 10);  // takes 10 each round

                for (byte k : key_1)
                    System.out.print(k);
                System.out.print(" ");
//                    output.write(k);
//                    output.write(" ");
                for (byte k : key_2)
                    System.out.print(k);
              //      output.write(k);
                System.out.println();
                //output.write(System.lineSeparator());
                for (int k = 0; k < cipherTextOutput.length; k += 8) {
                    for (int m = 0, l = k; m < cipherText.length; m++, l++)
                        cipherText[m] = cipherTextOutput[l];

                     cipherText = TripleSDES.tripleSDESDecrypt(cipherText, key_1, key_2);

                    for (int z = 0; z < 8; z++) {
                        plainTextFull[r] = cipherText[z];
                        r++;
                    }
                }
                System.out.println();
                System.out.println(CASCII.toString(plainTextFull));System.out.println();
//                output.write(System.lineSeparator());
//                output.write(CASCII.toString(plainTextFull));
//                output.write(System.lineSeparator());


            }
        }
//        output.close();
//        }catch(IOException e){
//            e.printStackTrace();
//        }

    }
}

