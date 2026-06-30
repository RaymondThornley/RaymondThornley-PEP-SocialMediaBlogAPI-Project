package Service;

import java.util.List;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Account;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;

    public MessageService() {
        this.messageDAO = new MessageDAO();
        this.accountDAO = new AccountDAO();
    }

    public MessageService(MessageDAO messageDAO, AccountDAO accountDAO) {
        this.messageDAO = messageDAO;
        this.accountDAO = accountDAO;
    }

    public Message createMessage(Message newMessage) {
        if (newMessage.getMessage_text().length() == 0) {
            return null;
        }
        if (newMessage.getMessage_text().length() > 255) {
            return null;
        }
        Account messageAccount = this.accountDAO.getAccountById(newMessage.getPosted_by());
        if (messageAccount == null) {
            return null;
        }
        return messageDAO.insertMessage(newMessage);
    }

    public List<Message> getAllMessages() {
        return this.messageDAO.getAllMessages();
    }

    public Message getMessage(int messageId) {
        return this.messageDAO.getMessageById(messageId);
    }

    public Message deleteMessage(int messageId) {
        Message deletedMessage = messageDAO.getMessageById(messageId);
        this.messageDAO.deleteMessage(messageId);
        return deletedMessage;
    }

    public Message updateMessage(int messageId, Message newMessage) {
        String newMessageText = newMessage.getMessage_text();
        if (newMessageText.length() == 0) {
            return null;
        }
        if (newMessageText.length() > 255) {
            return null;
        }
        messageDAO.updateMessage(messageId, newMessageText);
        return messageDAO.getMessageById(messageId);
    }

    public List<Message> getMessageByAccount(int accountId) {
        return messageDAO.getMessageByAccount(accountId);
    }
}
