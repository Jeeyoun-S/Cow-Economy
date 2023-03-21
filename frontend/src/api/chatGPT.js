import { Configuration, OpenAIApi } from "openai";

const configuration = new Configuration({
  organization: process.env.VUE_APP_OPEN_API_ID,
  apiKey: process.env.VUE_APP_OPEN_API_KEY,
});

const openai = new OpenAIApi(configuration);

async function sendMessageWord(message, success, fail) {
  console.log("#chatGPT - sendMessageWord# message: ", message);

  await openai
    .createChatCompletion({
      model: "gpt-3.5-turbo",
      messages: [{ role: "user", content: message }],
    })
    .then(success)
    .catch(fail);
}

export { sendMessageWord };
