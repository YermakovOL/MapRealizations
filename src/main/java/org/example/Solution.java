package org.example;

import strategies.HashMapWrapperStrategy;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Solution {
    public static void main(String[] args) {
    StrategyContext context = new StrategyContext();
    context.setStrategy(new HashMapWrapperStrategy());
    testStrategy(context,10000);

    }
    public static void testStrategy(StrategyContext context, long elementsNumber) {
        Helper.printMessage(context.getStrategy().getClass().getSimpleName() + ":");

        Set<String> origStrings = new HashSet<>();

        for (int i = 0; i < elementsNumber; ++i) {
            origStrings.add(Helper.generateRandomString());
        }
        Date startTimestamp = new Date();
        Set<Long> keys = getIds(context, origStrings);
        Date endTimestamp = new Date();
        long time = endTimestamp.getTime() - startTimestamp.getTime();
        Helper.printMessage("Время получения идентификаторов для " + elementsNumber + " строк: " + time);

        startTimestamp = new Date();
        Set<String> strings = getStrings(context, keys);
        endTimestamp = new Date();
        time = endTimestamp.getTime() - startTimestamp.getTime();
        Helper.printMessage("Время получения строк для " + elementsNumber + " идентификаторов: " + time);

        if (origStrings.equals(strings))
            Helper.printMessage("Тест пройден.");
        else
            Helper.printMessage("Тест не пройден.");

        Helper.printMessage("");
    }

    public static Set<Long> getIds(StrategyContext context, Set<String> strings) {
        Set<Long> keys = new HashSet<>();
        for (String s : strings) {
            keys.add(context.getId(s));
        }
        return keys;
    }

    public static Set<String> getStrings(StrategyContext context, Set<Long> keys) {
        Set<String> strings = new HashSet<>();
        for (Long k : keys) {
            strings.add(context.getString(k));
        }
        return strings;
    }
}
