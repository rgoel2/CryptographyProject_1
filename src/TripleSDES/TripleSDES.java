package TripleSDES;

import SDES.SDESimplementation;

public class TripleSDES {

// ########################### A FUNCTION TO PRINT OUT THE RESULTS OF ENCRYPTION AND DECRYPTION. #######################################


    public static void printResults(byte[] inputBytes) {
        for (byte input : inputBytes)
            System.out.print(input + " ");
    }

    // ########################### A FUNCTION TO ENCRYPT USING TRIPLESDES ##############################################################
    public static byte[] tripleSDESDecrypt(byte[] plainText, byte[] key_1, byte[] key_2) {
        SDESimplementation sdes = new SDESimplementation();
        return (sdes.Decrypt(key_1, sdes.Encrypt(key_2, sdes.Decrypt(key_1, plainText))));
    }

    // ########################### A FUNCTION TO DECRYPT USING TRIPLESDES ##############################################################
    public static byte[] tripleSDESEncrypt(byte[] cipherText, byte[] key_1, byte[] key_2) {
        SDESimplementation sdes = new SDESimplementation();
        return (sdes.Encrypt(key_1, sdes.Decrypt(key_2, sdes.Encrypt(key_1, cipherText))));
    }

    public static void main(String[] args) {

        byte[] key_1 = {1, 0, 0, 0, 1, 0, 1, 1, 1, 1};
        byte[] key_2 = {0, 1, 1, 0, 1, 0, 1, 1, 1, 0};

        byte[] plainText = {1, 0, 1, 0, 1, 0, 1, 0};
        byte[] encryptPlainText = tripleSDESEncrypt(plainText, key_1, key_2);
        System.out.print("Enryption(Cipher Text)     : ");
        printResults(encryptPlainText);

        System.out.println();
        byte[] cipherText = {1, 1, 1, 0, 0, 1, 1, 0};
        byte[] decryptCipherText = tripleSDESDecrypt(cipherText, key_1, key_2);
        System.out.print("Decryption(Plain Text)     : ");
        printResults(decryptCipherText);


/*
 *         K1              K2            P              Cipher
 * 		0000000000     0000000000     00000000        (11110000)
 *		1000101110     0110101110     11010111        (10111001)
 *		1000101110     0110101110     10101010        (11100100)
 *		1111111111     1111111111     10101010        (00000100)
 *		1000101110     0110101110    (01011000)        11100110
 *  	1011101111     0110101110    (01001111)        01010000
 * 		0000000000     0000000000    (01010010)        10000000
 *  	1111111111     1111111111    (00100101)        10010010
 */

    }

}