import opennlp.tools.langdetect.LanguageDetector;
import opennlp.tools.langdetect.LanguageDetectorME;
import opennlp.tools.langdetect.LanguageDetectorModel;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.File;
import java.util.Arrays;

public class Bot extends TelegramLongPollingBot {
    long chatId;
    String text;
    String command;
    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        String type = getMessageType(message);
        if(type == "text") text = message;
        else command = message;
        chatId = update.getMessage().getChatId();
        sendMsg(update.getMessage().getChatId(), message, type);
    }

    public synchronized void sendMsg(long chatId, String s, String type){
        String result;
        if(type == "text"){
            switch (command) {
                case "1":
                    WhitespaceTokenizer tokenizer1 = WhitespaceTokenizer.INSTANCE;
                    result = Arrays.toString(tokenizer1.tokenize(text));
                    break;
                case "2":
                    SimpleTokenizer tokenizer2 = SimpleTokenizer.INSTANCE;
                    result = Arrays.toString(tokenizer2.tokenize(text));
                    break;
                case "3":
                    try {
                        SentenceModel model = new SentenceModel(new File("opennlp-en-ud-ewt-sentence-1.0-1.9.3.bin"));
                        SentenceDetectorME detector = new SentenceDetectorME(model);
                        result = Arrays.toString(detector.sentDetect(text));
                    } catch (Exception e) {e.printStackTrace(); result = "Error.";}
                    break;
                case "4":
                    try {
                        LanguageDetectorModel model = new LanguageDetectorModel(new File("langdetect-183.bin"));
                        LanguageDetector detector = new LanguageDetectorME(model);
                        result = detector.predictLanguage(text).toString();
                    } catch (Exception e) {e.printStackTrace(); result = "Error.";}
                    break;
                case "5":
                    result = "Re-enter command and text.\n" +
                        "Commands:\n" +
                        "1 - whitespace tokenizer\n" +
                        "2 - simple tokenizer\n" +
                        "3 - sentence detection\n" +
                        "4 - language detection\n";
                default:
                    result = "Re-enter command and text.\n" +
                            "Commands:\n" +
                            "1 - whitespace tokenizer\n" +
                            "2 - simple tokenizer\n" +
                            "3 - sentence detection\n" +
                            "4 - language detection\n";
            }
        }
        else {
            result = "Enter text";
        }
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        System.out.println(result);
        sendMessage.setText(result);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String getMessageType(String string){
        String type;
        if(string.length() == 1){
            type = "command";
        }
        else type = "text";
        return type;
    }
    @Override
    public String getBotUsername() {
        return "test1412123_bot";
    }

    @Override
    public String getBotToken() {
        return "";
    }
}