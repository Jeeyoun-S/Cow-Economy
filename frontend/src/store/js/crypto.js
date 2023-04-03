// # CryptoJS 암/복호화
import CryptoJS from "crypto-js";

export const encrypt = (data, key) => {
  return CryptoJS.AES.encrypt(JSON.stringify(data), key).toString();
};

export const decrypt = (text, key) => {
  try {
    const bytes = CryptoJS.AES.decrypt(text, key);
    return JSON.parse(bytes.toString(CryptoJS.enc.Utf8));
  } catch (error) {
    console.log(error);
    return;
  }
};
