package org.example;

import strategies.SimpleHashMapStrategy;

public class StrategyContext {
    private long lastId = 0L;
    private SimpleHashMapStrategy strategy;

    public SimpleHashMapStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(SimpleHashMapStrategy strategy) {
        this.strategy = strategy;
    }

    public synchronized Long getId(String string) {
        if(strategy.containsValue(string)){
            return strategy.getKey(string);
        }else strategy.put(++lastId,string);
        return lastId;

    }

    public synchronized String getString(Long id) {
        return strategy.getValue(id);
    }
}
