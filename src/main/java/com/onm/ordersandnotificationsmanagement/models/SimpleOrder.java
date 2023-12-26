package com.onm.ordersandnotificationsmanagement.models;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
@Setter
@Getter
@NoArgsConstructor
public class SimpleOrder extends Order{
    ArrayList<Product> products;

}
