package com.payment.service.service;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import java.math.BigDecimal;

public interface PaymentService {


    Payment createPayment(
            BigDecimal total,
            String currency,
            String method,
            String intent,
            String description,
            String cancelUrl,
            String successUrl
    ) throws PayPalRESTException;


    Payment executePayment(
            String paymentId,
            String payerId
    ) throws PayPalRESTException;
}
