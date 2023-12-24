package com.onm.ordersandnotificationsmanagement.models;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
@Setter
@Getter
@NoArgsConstructor
public class CompoundOrder extends Order{
    ArrayList<SimpleOrder> simpleOrders;
}
