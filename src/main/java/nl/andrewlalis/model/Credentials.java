package nl.andrewlalis.model;

/**
 * Represents the login credentials of a user.
 */
public class Credentials {

    private String token;
    private String username;
    private String password;

    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Credentials(String token) {
        this.token = token;
    }

    /**
     * Determines if this credential is an OAuth type, where a token should be used in place of a username and password.
     *
     * @return True if an OAuth token exists, or false otherwise.
     */
    public boolean isOAuth() {
        return this.token != null;
    }

    public String getToken() {
        return this.token;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.isOAuth()) {
            return "token: " + this.token;
        } else {
            return "username: " + this.username + ", password: " + this.password;
        }
    }
}
