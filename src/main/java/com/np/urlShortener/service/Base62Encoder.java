package com.np.urlShortener.service;

import org.springframework.stereotype.Service;

@Service
public class Base62Encoder {

    String charSet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public String encode(long id){
        long num = id;
        StringBuilder sb = new StringBuilder();

        while(num>0){
            int rem = (int) (num%62);
            sb.append(charSet.charAt(rem));
            num/=62;
        }
        return sb.reverse().toString();
    }
}
