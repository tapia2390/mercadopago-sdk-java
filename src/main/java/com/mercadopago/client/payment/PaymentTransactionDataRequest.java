package com.mercadopago.client.payment;

import lombok.Builder;
import lombok.Getter;

/** PaymentTransactionDataRequest class. */
@Getter
@Builder
public class PaymentTransactionDataRequest {

  /** Ticket Info. */
  private final PaymentTicketInfoRequest ticketInfo;
}
