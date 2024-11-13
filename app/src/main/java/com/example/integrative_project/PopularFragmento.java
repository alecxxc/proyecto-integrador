package com.example.integrative_project;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

public class PopularFragmento extends Fragment {

    private static final PayPalConfiguration config = new PayPalConfiguration()
            .environment(PaypalConfig.PAYPAL_ENVIRONMENT)
            .clientId(PaypalConfig.PAYPAL_CLIENT_ID)
            .merchantName("Your Merchant Name")
            .merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
            .merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));

    private String accessToken;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Infla el layout del fragmento
        View view = inflater.inflate(R.layout.fragmento_popular, container, false);

        // Iniciar el servicio de PayPal
        Intent intent = new Intent(getActivity(), PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        getActivity().startService(intent);

        // Inicializar el botón de pago
        initPayButton(view);

        // Obtener el token de acceso
        new RetrieveAccessTokenTask().execute(PaypalConfig.PAYPAL_CLIENT_ID, PaypalConfig.PAYPAL_SECRET);

        return view;
    }

    private void initPayButton(View view) {
        Button payButton = view.findViewById(R.id.payButton);
        payButton.setOnClickListener(v -> processPayment());
    }

    private void processPayment() {
        if (accessToken != null) {
            PayPalPayment payment = new PayPalPayment(
                    new BigDecimal("10.00"),
                    "USD",
                    "Descripción del pago",
                    PayPalPayment.PAYMENT_INTENT_SALE
            );

            Intent intent = new Intent(getActivity(), PaymentActivity.class);
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
            intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
            startActivityForResult(intent, 123);
        } else {
            // Manejar el caso en que el token no esté disponible
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().stopService(new Intent(getActivity(), PayPalService.class));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 123 && data != null) {
            PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirmation != null) {
                String paymentDetails = confirmation.toJSONObject().toString();
                // Manejar la confirmación del pago
            } else if (resultCode == getActivity().RESULT_CANCELED) {
                // Manejar cancelación
            } else {
                // Manejar otros errores
            }
        }
    }

    private class RetrieveAccessTokenTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                String clientId = params[0];
                String secret = params[1];
                String url = "https://api.sandbox.paypal.com/v1/oauth2/token";
                String authString = clientId + ":" + secret;
                String authStringEnc = Base64.encodeToString(authString.getBytes(), Base64.NO_WRAP);

                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Authorization", "Basic " + authStringEnc);
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setDoOutput(true);

                String paramsStr = "grant_type=client_credentials";
                connection.getOutputStream().write(paramsStr.getBytes());

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return extractAccessTokenFromResponse(response.toString());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                accessToken = result;
            } else {
                // Manejar error al obtener el token
            }
        }

        private String extractAccessTokenFromResponse(String responseBody) {
            // Implementa la lógica para extraer el token del JSON
            return ""; // Reemplaza con el token extraído del JSON
        }
    }
}
