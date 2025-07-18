import { useEffect, useState,useRef } from "react";
import { Payment, StatusScreen, initMercadoPago } from '@mercadopago/sdk-react';
import Api from "../services/Api";

const PaymentBrick = () => {
  const [loading, setLoading] = useState(false);
  const [payId, setPayId] = useState("");
  const [preferenceID, setPreferenceID] = useState("");
  const [showStatusScreen, setShowStatusScreen] = useState(false); 
  const paymentBrickContainer = useRef(null);

  useEffect(() => {
      if (paymentBrickContainer.current) {
          initMercadoPago('PUBLIC_KEY', { locale: 'pt' });
    }});
    
  const initialization = {
      amount: 150.0,
      preferenceID: preferenceID,
  };

  const customization = {
      paymentMethods: {
          creditCard: 'all',
          selectInstallments: "all",
          maxInstallments: 12
      },
  };

  const onSubmit = async ({ selectedPaymentMethod, formData }) => {
    try {
      console.log(formData)
        const response = await Api.postCreatePayment({
          cardPaymentDTO: {
            token: formData.token,
            issuerId: formData.issuer_id,
            paymentMethodId: formData.payment_method_id,
            transactionAmount: formData.transaction_amount,
            installments: 1,
          },
          payerDTO: {
            email: formData.payer.email,
            type: "CPF",
            number: formData.payer.identification.number,
            firstName: "",
          },
          productDTO: {
            name: "Produto Teste",
            id: "prod-001",
            description: "Descrição do produto",
            value: 150.0,
            mount: 1,
          },
          providerType: "MERCADOPAGO",
          paymentType: "CARD",
        });
        console.log(response)
        setPayId(response.data);
        setShowStatusScreen(true);   
      
    } catch (err) {      
      console.error(err);
    }

};

const onError = async (error) => {
    console.log(error);
};

const onReady = async () => {
    // Callback chamado quando o Brick estiver pronto.
};

return (
    <div ref={paymentBrickContainer}>

        {!showStatusScreen && (
            <div>
                <Payment
                    initialization={initialization}
                    customization={customization}
                    onSubmit={onSubmit}
                    onReady={onReady}
                    onError={onError}
                />
            </div>
        )}

        {showStatusScreen && (
            <div>
                <StatusScreen
                    initialization={{ paymentId: payId }}
                    onReady={onReady}
                    onError={onError}
                />
            </div>
            
        )}
    </div>
)}

export default PaymentBrick;
