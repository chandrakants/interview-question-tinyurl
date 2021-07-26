package com.tinyurl.app.base;

import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class BaseComponent {

    private static final String base62String = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final char[] allowedCharacters = base62String.toCharArray();
    private final int base62 = allowedCharacters.length;

    public String encode(long input) {
        StringBuilder encodedString = new StringBuilder();

        if (input == 0) {
            return String.valueOf(allowedCharacters[0]);
        }

        while (input > 0) {
            encodedString.append(allowedCharacters[(int) (input % base62)]);
            input = input / base62;
        }

        return Base64.getEncoder().encodeToString(encodedString.reverse().toString().getBytes());

    }

    public long decode(String input1) {

        byte[] decodedBytes = Base64.getDecoder().decode(input1);
        String input = new String(decodedBytes);

        char[] characters = input.toCharArray();
        int length = characters.length;

        int decoded = 0;

        //counter is used to avoid reversing input string
        int counter = 1;
        for (int i = 0; i < length; i++) {
            decoded += base62String.indexOf(characters[i]) * Math.pow(base62, length - counter);
            counter++;
        }

       return decoded;
    }
}
