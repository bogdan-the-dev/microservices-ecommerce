package ro.bogdansoftware.client_information.model;

public record UserAddress(
        String country,
        String county,
        String City,
        String street,
        String number,
        String zipcode
) {}
