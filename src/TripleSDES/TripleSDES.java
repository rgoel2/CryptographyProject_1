package TripleSDES;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class TripleSDES {

    // Values for the Initial Permutation (IP) step.
    private final static byte[] IP =
            {
                    2,6,3,1,4,8,5,7
            };


    // Values for the Final Permutation (FP) step.

    private final static byte[] FP =
            {
                    4,1,3,5,7,2,8,6
            };

    // Values for Key Generation permutation P10

    private final static byte[] P10 =
            {
                    3,5,2,7,4,10,1,9,8,6
            };

    // Values for Key Generation permutation P8

    private final static byte[] P8 =
            {
                    6,3,7,4,8,5,10,9
            };

    //Values for expansion/permutation EP
    private final static byte[] EP=
            {
                    4,1,2,3,2,3,4,1
            };

    //Values for permutation P4
    private final static byte[] P4=
            {
                    2,4,3,1
            };

    //Values for S-Box S0
    private final static byte[][] S0=
            {
                    {1,0,3,2},
                    {3,2,1,0},
                    {0,2,1,3},
                    {3,1,3,2}
            };

    //Values for S-Box S1
    private final static byte[][] S1=
            {
                    {0,1,2,3},
                    {2,0,1,3},
                    {3,0,1,0},
                    {2,1,0,3}
            };


//################################## THIS METHOD APPLY THE REGULAR SDES TO ONE KEY EACH TIME ###############################

    public static byte[] EncryptSDES( byte[] key, byte[] plaintext ){

        Implementation sdes = new Implementation();

        sdes.keyGenerator(key);

        return (sdes.finalPermutation(sdes.fK(sdes.switchFunction(sdes.fK(
                sdes.initialPermutation(plaintext),sdes.K1)),sdes.K2)));

    }

    public static byte[] Decrypt( byte[] key, byte[] ciphertext ){

        Implementation sdes = new Implementation();

        sdes.keyGenerator(key);

        return (sdes.finalPermutation(sdes.fK(sdes.switchFunction(sdes.fK(
                sdes.initialPermutation(ciphertext),sdes.K2)),sdes.K1)));

    }


// ########################### A FUNCTION TO PRINT OUT THE RESULTS OF ENCRYPTION AND DECRYPTION. #######################################

    public static void printResultsEncryption(byte[] resultAfterEncryption){


        System.out.print("Enryption(Cipher)     : ");
        for (byte cipher:resultAfterEncryption)
            System.out.print(cipher+" ");
        System.out.println();

    }
    public static void printResultsDecryption(byte[] resultAfterDecryption){


        System.out.print("Decryption(Plain Text): ");
        for (byte plain:resultAfterDecryption)
            System.out.print(plain+" ");


    }

    public static byte[] tripleSDESEncrypt(byte[] plainText, byte[] key_1, byte[] key_2) {
        return (EncryptSDES(key_1, Decrypt(key_2, EncryptSDES(key_1, plainText))));
    }

    public static byte[] tripleSDESDecrypt(byte[] cipher, byte[] key_1, byte[] key_2) {
        return(Decrypt(key_1, EncryptSDES(key_2, Decrypt(key_1 , cipher))));
    }

    public static void main(String[] args){

        byte[] key_1 = {1,1,1,1,1,1,1,1,1,1};
        byte[] key_2 = {1,1,1,1,1,1,1,1,1,1};


        byte[] plainText = {1,0,1,0,1,0,1,0};
       // byte[] encryptPlainText = tripleSDESEncrypt(plainText,key_1,key_2);
       // printResultsEncryption(encryptPlainText);


        byte[] cipher = {1,0,0,1,0,0,1,0};
        byte[]  decryptCipherText = tripleSDESDecrypt(cipher,key_1,key_2);
        printResultsDecryption(decryptCipherText);


/*
 *         K1              K2            P              Cipher
 * 		0000000000     0000000000     00000000        (11110000)
 *		1000101110     0110101110     11010111        (10111001)
 *		1000101110     0110101110     10101010        (11100100)
 *		1111111111     1111111111     10101010        (00000100)
 *		1000101110     0110101110    (11111101)        11100110
 *  	1011101111     0110101110    (01001111)        01010000
 * 		0000000000     0000000000    (01010010)        10000000
 *  	1111111111     1111111111    (00100101)        10010010
 */

    }



// #################################### AN INNER CLASS FOR ALL THE IMPLEMENTATION OF 3SDES. ###########################################

    static class Implementation {

        public byte[] K1,K2;


        // Permutations Implementation
        public byte[] executePermutation(byte[] P, byte[] input) {
            int length = P.length;
            byte[] output=new byte[length];
            for(int i=0;i<length;i++)
                output[i]=input[(P[i]-1)];
            return output;
        }

        // S-BOX Implementation
        public int sBoxValue(byte[][] S, byte[] input) {
            String r = String.valueOf(input[0])+ String.valueOf(input[3]);
            String c = String.valueOf(input[1])+ String.valueOf(input[2]);
            int row = Integer.parseInt(r,2);
            int col = Integer.parseInt(c,2);
            return S[row][col];
        }

        // Left Shift
        public byte[] leftShift(byte[] input, int i) throws UnsupportedEncodingException {
            byte[] output = new byte[input.length];
            for(int j = 0; j < input.length-i; j++)
                output[j] = input[j+i];
            int k=0;
            for (int j = input.length-i; j < input.length; j++) {
                output[j] = input[k];
                k++;
            }
            return output;
        }

        public byte[] stringToByte(String input){
            byte[] output = new byte[input.length()];
            for (int i=0; i<input.length(); i++) {
                output[i]= input.charAt(i)=='1' ? (byte)1 : (byte)0;
            }
            return output;
        }

        // XOR Implementation
        public byte[] executeXOR(byte[] left, byte[] right) {
            for (int i = 0; i < left.length; i++) {
                left[i]= (byte) (left[i] ^ right[i]);
            }
            return left;
        }

        // Left Shift Process Implementation.
        public byte[] leftShiftProcess(byte[] initialP10, int i) {
            byte[] L0 = Arrays.copyOfRange(initialP10, 0,(initialP10.length/2));
            byte[] R0 = Arrays.copyOfRange(initialP10, initialP10.length/2 , initialP10.length);

            try {
                L0 = leftShift(L0,i);
                R0 = leftShift(R0,i);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return concatArrays(L0,R0);
        }

        // Left Bits and Right Bits Concatenation
        public byte[] concatArrays(byte[] L, byte[] R) {
            byte[] concatenated = new byte[L.length+R.length];
            for (int i = 0; i < L.length; i++) {
                concatenated[i] = L[i];
                concatenated[i+L.length] = R[i];
            }
            return concatenated;
        }

        // Key Generation Implementation
        public void keyGenerator(byte[] rawkey) {
            byte[] initialP10 = executePermutation(P10, rawkey);
            initialP10 = leftShiftProcess(initialP10,1);
            K1 = executePermutation(P8,initialP10);
            initialP10 = leftShiftProcess(initialP10,2);
            K2 = executePermutation(P8,initialP10);
        }

        // Initial Permutation Implementation
        public byte[] initialPermutation(byte[] input) {
            return executePermutation(IP, input);
        }

        // f(k) function Implementation.
        public byte[] fK(byte[] input, byte[] key) {
            int length=input.length;
            byte[] L = Arrays.copyOfRange(input,0,length/2);
            byte[] R = Arrays.copyOfRange(input,length/2, length);
            try {
                L = executeXOR(L,F(R,key));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return concatArrays(L,R);
        }


        // F mapping implementation.
        public byte[] F(byte[] R, byte[] key) throws UnsupportedEncodingException {
            byte[] ep= executePermutation(EP,R);
            ep = executeXOR(ep,key);
            byte[] l = Arrays.copyOfRange(ep,0,ep.length/2);
            byte[] r = Arrays.copyOfRange(ep,ep.length/2, ep.length);
            String AfterSBOX1 = Integer.toBinaryString(sBoxValue(S0,l));
            AfterSBOX1 = AfterSBOX1.length()==2 ? AfterSBOX1 : "0"+AfterSBOX1;
            String AfterSBOX2 = Integer.toBinaryString(sBoxValue(S1,r));
            AfterSBOX2 = AfterSBOX2.length()==2 ? AfterSBOX2 : "0"+AfterSBOX2;
            byte [] AfterSBOXBytes = stringToByte(AfterSBOX1+AfterSBOX2);
            return executePermutation(P4,AfterSBOXBytes);
        }

        // Switch Function Implementation.
        public byte[] switchFunction(byte[] input) {
            byte[] L = Arrays.copyOfRange(input, 0, input.length/2);
            byte[] R = Arrays.copyOfRange(input, input.length/2 , input.length);
            return concatArrays(R,L);
        }

        // Final Permutation Implementation.
        public byte[] finalPermutation(byte[] input) {
            return executePermutation(FP, input);
        }

    }
}
