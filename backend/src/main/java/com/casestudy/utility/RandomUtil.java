package com.casestudy.utility;


import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomUtil {

    private Random random = new Random();

    public boolean isFake() {
        return random.nextFloat() > 0.8;
    }

}
