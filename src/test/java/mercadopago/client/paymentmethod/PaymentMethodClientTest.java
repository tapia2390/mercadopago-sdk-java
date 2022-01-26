package mercadopago.client.paymentmethod;

import static com.mercadopago.net.HttpStatus.OK;
import static mercadopago.helper.MockHelper.generateHttpResponseFromFile;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;

import com.mercadopago.client.paymentmethod.PaymentMethodClient;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPResourceList;
import com.mercadopago.resources.paymentmethod.PaymentMethod;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import mercadopago.BaseClientTest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.Test;

class PaymentMethodClientTest extends BaseClientTest {

  private static final String PAYMENT_METHOD_BASE_JSON = "paymentmethod/payment_method_base.json";

  private static final String THUMBNAIL =
      "https://www.mercadopago.com/org-img/MP3/API/logos/debmaster.gif";

  private static final Long ACCREDITATION_TIME = 1440L;

  private final PaymentMethodClient client = new PaymentMethodClient();

  @Test
  void list() throws MPException, IOException {

    List<String> additionalInfoNeeded = new ArrayList<>();
    additionalInfoNeeded.add("cardholder_name");
    additionalInfoNeeded.add("cardholder_identification_type");
    additionalInfoNeeded.add("cardholder_identification_number");

    HttpResponse httpResponse = generateHttpResponseFromFile(PAYMENT_METHOD_BASE_JSON, OK);
    doReturn(httpResponse)
        .when(httpClient)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    MPResourceList<PaymentMethod> result = client.list();

    assertEquals(OK, result.get(0).getResponse().getStatusCode());
    assertNotNull(result.get(0).getResponse());
    assertEquals("debmaster", result.get(0).getId());
    assertEquals("Mastercard Débito", result.get(0).getName());
    assertEquals("debit_card", result.get(0).getPaymentTypeId());
    assertEquals("testing", result.get(0).getStatus());
    assertEquals(THUMBNAIL, result.get(0).getSecureThumbnail());
    assertEquals(THUMBNAIL, result.get(0).getThumbnail());
    assertEquals("unsupported", result.get(0).getDeferredCapture());
    assertEquals(1, result.get(0).getSettings().size());
    assertEquals("standard", result.get(0).getSettings().get(0).getCardNumber().getValidation());
    assertEquals("16", result.get(0).getSettings().get(0).getCardNumber().getLength());
    assertEquals("^(502121)", result.get(0).getSettings().get(0).getBin().getPattern());
    assertNull(result.get(0).getSettings().get(0).getBin().getInstallmentsPattern());
    assertNull(result.get(0).getSettings().get(0).getBin().getExclusionPattern());
    assertEquals(3, result.get(0).getSettings().get(0).getSecurityCode().getLength());
    assertEquals("back", result.get(0).getSettings().get(0).getSecurityCode().getCardLocation());
    assertEquals("mandatory", result.get(0).getSettings().get(0).getSecurityCode().getMode());
    assertEquals(3, result.get(0).getAdditionalInfoNeeded().size());
    assertTrue(result.get(0).getAdditionalInfoNeeded().containsAll(additionalInfoNeeded));
    assertEquals(new BigDecimal("0.5"), result.get(0).getMinAllowedAmount());
    assertEquals(new BigDecimal("60000"), result.get(0).getMaxAllowedAmount());
    assertEquals(ACCREDITATION_TIME, result.get(0).getAccreditationTime());
    assertTrue(result.get(0).getFinancialInstitutions().isEmpty());
    assertTrue(result.get(0).getProcessingModes().contains("aggregator"));
  }
}
