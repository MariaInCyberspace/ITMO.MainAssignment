package com.mariaincyberspace.lostandfound_1.domain.repository;

import com.mariaincyberspace.lostandfound_1.domain.model.Item;
import java.util.List;

public interface OnCallBack {
    public void onCallBack(List<Item> items);
}
