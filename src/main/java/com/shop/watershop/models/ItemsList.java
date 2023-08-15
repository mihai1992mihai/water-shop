package com.shop.watershop.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Future;
import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
public class ItemsList {

    private ArrayList<Item> items;

    @Future(message = "Choose a date that is after today")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    public ItemsList(ArrayList<Item> list) {
        this.items = list;
    }
}

