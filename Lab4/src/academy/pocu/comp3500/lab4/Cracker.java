package academy.pocu.comp3500.lab4;

import academy.pocu.comp3500.lab4.pocuhacker.RainbowTable;
import academy.pocu.comp3500.lab4.pocuhacker.User;


public final class Cracker {
    final User[] userTable;
    final String email;
    final String password;

    public Cracker(User[] userTable, String email, String password) {
        this.userTable = userTable;
        this.email = email;
        this.password = password;
    }

    public String[] run(final RainbowTable[] rainbowTables) {
        if (rainbowTables.length != 5)
            return null;

        String[] plaintextPWs = new String[userTable.length];
        for (int i = 0; i < userTable.length; i++) {
                String userPassWordHash = userTable[i].getPasswordHash();
            for (int j = 0; j < 5; j++) {
                if (rainbowTables[j].contains(userPassWordHash)) {
                    plaintextPWs[i] = rainbowTables[j].get(userPassWordHash);
                }
            }
        }

        return plaintextPWs;
    }
}
