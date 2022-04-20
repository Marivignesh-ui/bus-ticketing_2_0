package com.mari.domain;

public class PaymentResponse {
    private final String razorpay_payment_id;

    private final String razorpay_order_id;

    private final boolean isSignatureValid;

    public PaymentResponse(String razorpay_payment_id, String razorpay_order_id, boolean isSignatureValid) {
        this.razorpay_payment_id = razorpay_payment_id;
        this.razorpay_order_id = razorpay_order_id;
        this.isSignatureValid = isSignatureValid;
    }

    public String getRazorpay_payment_id() {
        return razorpay_payment_id;
    }

    public String getRazorpay_order_id() {
        return razorpay_order_id;
    }

    public boolean isSignatureValid() {
        return isSignatureValid;
    }
}
