package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        this.accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account registerUser(Account newAccount) {
        if (newAccount.getUsername().length() == 0) {
            return null;
        }
        if (newAccount.getPassword().length() < 4) {
            return null;
        }
        Account existingAccount = accountDAO.getAccountByUsername(newAccount.getUsername());
        if (existingAccount != null) {
            return null;
        }
        return accountDAO.insertAccount(newAccount);
    }

    public Account loginUser(Account loginAccount) {
        Account existingAccount = accountDAO.getAccountByUsername(loginAccount.username);
        if (existingAccount == null) {
            return null;
        }
        if (existingAccount.getPassword().equals(loginAccount.getPassword())) {
            return existingAccount;
        }
        return null;
    }
}
