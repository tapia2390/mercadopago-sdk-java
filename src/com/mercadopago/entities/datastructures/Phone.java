package com.mercadopago.entities.datastructures;

import com.mercadopago.core.validationannotations.Size;

/**
 * Mercado Libre SDK
 * Phone class
 *
 * Created by Eduardo Paoletta on 11/9/16.
 */
public class Phone {

    @Size(max=256)
    private String area_code = null;
    @Size(max=256)
    private String number = null;

    public String getAreaCode() {
        return area_code;
    }

    public Phone setAreaCode(String areaCode) {
        this.area_code = areaCode;
        return this;
    }

    public String getNumber() {
        return number;
    }

    public Phone setNumber(String number) {
        this.number = number;
        return this;
    }
}
