package Services;

import DAO.AccountDao;
import Model.Account;

public class AccountService {
    private AccountDao accountDao;

    public AccountService() {
        this.accountDao = new AccountDao();
    }

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public Account addUser(Account account) {
        return accountDao.createAccount(account);
    }

    public Account getUser(Account account) {
        return accountDao.getUser(account);
    }
}
