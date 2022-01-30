package com.techupstudio.otc_chingy.mychurch.core.utils.general.interfaces;

public interface Joiner<Item, Result> {
    Result join(Item item, Result result);
}