package com.payment.service.api;


import com.payment.service.service.PaymentService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/create")
    public RedirectView createPayment(
            @RequestParam("method") String method,
            @RequestParam("amount") String amount,
            @RequestParam("currency") String currency,
            @RequestParam("description") String description

    ){

        try {

            String cancelUrl = "https://localhost:2244/api/v1/payments/cancel";
            String successUrl = "https://localhost:2244/api/v1/payments/success";

            Payment payment = paymentService.createPayment(
                    new BigDecimal(amount),
                    currency,
                    method,
                    "sale",
                    description,
                    cancelUrl,
                    successUrl
            );

            for (Links links: payment.getLinks()){
                if (links.getRel().equals("approval_url")){
                    return new RedirectView(links.getHref());
                }
            }
        }catch (PayPalRESTException e){
            log.error("Error occurred:: ", e);
        }
        return new RedirectView("/payment/error");
    }

    @GetMapping("/success")
    public ResponseEntity<String> paymentSuccess(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("payerId") String payerId
    ){
        try {

            Payment payment = paymentService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                return ResponseEntity.ok("Payment was successful. Transaction ID: " + payment.getId());
            } else {
                return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED)
                        .body("Payment was not approved. Please try again.");
            }
        } catch (PayPalRESTException e){
            log.error("Error occurred:: ",e);
        }
        return ResponseEntity.ok("PaymentSuccess");
    }

    @GetMapping("/cancel")
    public String paymentCancel(){
        return "paymentCancel";
    }

    @GetMapping("/error")
    public String paymentError(){
        return "paymentError";
    }
}

