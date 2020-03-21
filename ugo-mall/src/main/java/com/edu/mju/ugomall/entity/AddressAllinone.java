package com.edu.mju.ugomall.entity;

public class AddressAllinone {
    private Address address;
    private AddressDetail addressDetail;

    @Override
    public String toString() {
        return "AddressAllinone{" +
                "address=" + address +
                ", addressDetail=" + addressDetail +
                '}';
    }

    public AddressAllinone() {
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public AddressDetail getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(AddressDetail addressDetail) {
        this.addressDetail = addressDetail;
    }
}
