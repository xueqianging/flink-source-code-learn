package com.lingxing.bean;

public class ItemViewCount {
    public    Long itemId;
    public    Long windowEnd;
    public    Long count;


    public ItemViewCount(Long itemId, Long windowEnd, Long count) {
        this.itemId = itemId;
        this.windowEnd = windowEnd;
        this.count = count;
    }



    public ItemViewCount() {
    }

}
