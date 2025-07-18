import axios from "axios";

const api = axios.create({
    baseURL: "http://localhost:8091",
    
});

export const postCreatePayment = async (paymentData) => {
    return await api.post("/payment", paymentData);
};

export default {
    postCreatePayment
};